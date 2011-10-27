package com.tgra.camera;

import com.badlogic.gdx.graphics.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tgra.model.Cell;
import com.tgra.model.Maze;
import com.tgra.model.Point3D;
import com.tgra.model.Vector3D;

public class MazeCam {

	private Point3D eye;
	private Vector3D n;
	private Vector3D u;
	private Vector3D v;
	private Maze maze;
	private float wallDistanceZeroThree = 0.3f;
	private float wallDistZeroSix = 0.6f;

	public  MazeCam(Point3D pEye, Point3D pCenter, Vector3D up, Maze maze)
	{

		this.maze = maze;
		this.wallDistanceZeroThree	= 0.3f;
		this.wallDistZeroSix		= 0.7f;
		this.eye = pEye;
		this.n = Vector3D.difference(pEye, pCenter);
		this.n.normalize();
		this.u = Vector3D.cross(up, n);
		this.u.normalize();
		this.v = Vector3D.cross(n, u);
	}
	
	/**
	 * set Model View Matrix
	 */
	public void setModelViewMatrix()
	{
		Vector3D minusEye = Vector3D.difference(new Point3D(0,0,0), eye);
		
		float[] matrix = new float[16];
		matrix[0] = u.x;	matrix[4] = u.y;	matrix[8] = u.z;	matrix[12] = Vector3D.dot(minusEye, u);
		matrix[1] = v.x;	matrix[5] = v.y;	matrix[9] = v.z;	matrix[13] = Vector3D.dot(minusEye, v);
		matrix[2] = n.x;	matrix[6] = n.y;	matrix[10] = n.z;	matrix[14] = Vector3D.dot(minusEye, n);
		matrix[3] = 0;		matrix[7] = 0;		matrix[11] = 0;		matrix[15] = 1;
		
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadMatrixf(matrix, 0);
	}
	
	/**
	 * Slide on the axis.
	 * 
	 * @param delU
	 * @param delV
	 * @param delN
	 */
	public void slide(float delU, float delV, float delN)
	{
		eye.add(Vector3D.sum(Vector3D.mult(delU, u), Vector3D.sum(Vector3D.mult(delV, v), Vector3D.mult(delN, n))));
	}

	/**
	 * Look to the sides.
	 * Turn around v.
	 * 
	 * @param angle the angle
	 */
	public void yaw(float angle)
	{
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = u;
		u = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, n));
		n = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, n));
	}

	/**
	 * Rotation around n-axis.
	 * 
	 * @param angle the angle
	 */
	public void roll(float angle)
	{
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = u;
		u = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, v));
		v = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, v));
	}

	/**
	 * Look up and down. Turn around u.
	 * @param angle
	 */
	public void pitch(float angle)
	{
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = v;
		v = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, n));
		n = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, n));
	}
	
	/**
	 * React on user input, move the camera.
	 */
	public void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			this.move(-1.0f, deltaTime, maze);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			this.move(1.0f, deltaTime, maze);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			this.yaw(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			this.yaw(90.0f * deltaTime);
		}
	}

	
	public Cell getCurrentCell() {
		
		int fieldPosX = (int)this.eye.x;
		int fieldPosZ = (int)this.eye.z;
		
		return maze.getCell(fieldPosX, fieldPosZ);
	}
	
	/**
	 * Move the camera around
	 * 
	 * @param forwards -1.0f up button, 1.0f back button
	 * @param deltaTime
	 * @param maze
	 */
	private void move(float forwards, float deltaTime, Maze maze)
	{		

		boolean wWall = false;
		boolean sWall = false;
		
		
		int fieldPosX = (int)this.eye.x;
		int fieldPosZ = (int)this.eye.z;
		
		float directionX = this.n.x * forwards;
		float directionZ = this.n.z * forwards;
		
		float camPosX = this.eye.x;
		float camPosZ = this.eye.z;
		
		Cell currentCell = getCurrentCell();
		
		System.out.println("currentCell: "+currentCell);
		System.out.println("nextCell: "+maze.getCell(fieldPosX, fieldPosZ +1));
		System.out.println("fieldX: "+fieldPosX+" fieldZ: "+fieldPosZ);
		System.out.println("camX: "+camPosX+" camZ: "+camPosZ);	
		System.out.println("directionX: "+directionX+" directionZ: "+directionZ);
		
		if(directionX <= 0)
		{
			System.out.println("direction x <= 0");
			if(directionZ > 0)
			{
				System.out.println("directionZ > 0");
				if(maze.getCell(fieldPosX-1, fieldPosZ).isWestWall())
				{
					if(camPosX <= (fieldPosX + wallDistanceZeroThree))
					{
						wWall = true;
					}
				}
				if(maze.getCell(fieldPosX, fieldPosZ +1).isSouthWall())
				{
					if(camPosZ >= (fieldPosZ + wallDistanceZeroThree))
					{
						sWall = true;
					}
				}
				if(wWall == true && sWall == false){
					this.eye.z += deltaTime * 1;
					System.out.println("Wall 1 hit");
					return;
				}
				else if(wWall == false && sWall == true){
					this.eye.x -= deltaTime * 1;
					System.out.println("Wall 2 hit");
					return;
				}
				else if(wWall == true && sWall == true){
					System.out.println("Trapped!");
					return;
				}
			}
			else if(directionZ <= 0)
			{
				
				System.out.println("directionZ <= 0");
				if(maze.getCell(fieldPosX-1, fieldPosZ).isWestWall())
				{
					if(camPosX >= (fieldPosX + wallDistanceZeroThree))
					{
						wWall = true;
					}
				}
				if(currentCell.isSouthWall())
				{
					if(camPosZ >= (fieldPosZ + wallDistanceZeroThree))
					{
						sWall = true;
					}
				}
				if(wWall == true && sWall == false){
					this.eye.z -= deltaTime * 1;
					System.out.println("Wall 1 hit");
					return;
				}
				else if(wWall == false && sWall == true){
					this.eye.x -= deltaTime * 1;
					System.out.println("Wall 2 hit");
					return;
				}
				else if(wWall == true && sWall == true){
					System.out.println("Trapped!");
					return;
				}
			}
		}
		///////////////////////////////////////
		
		
		
		
		
		if(directionX > 0)
		{
			System.out.println("direction x > 0");
			if(directionZ >= 0)
			{
				
				System.out.println("directionZ >= 0");
				if(maze.getCell(fieldPosX, fieldPosZ+1).isSouthWall())
				{
					if(camPosZ >= (fieldPosZ+ wallDistZeroSix))
					{
						wWall = true;
					}
				}
				if(currentCell.isWestWall())
				{
					if(camPosX >= (fieldPosX +  wallDistZeroSix))
					{
						sWall = true;
					}
				} 
				if(wWall == true && sWall == false){
					this.eye.x += deltaTime * 1;
					System.out.println("Wall 1 hit");
					return;
				}
				else if(wWall == false && sWall == true){
					this.eye.z += deltaTime * 1;
					System.out.println("Wall 2 hit");
					return;
				}
				else if(wWall == true && sWall == true){
					System.out.println("Trapped!");
					return;
				}
			}
		
			else if(directionZ < 0)
			{
				System.out.println("directionZ < 0");
				if(currentCell.isWestWall())
				{
					if(camPosX >= (fieldPosX + wallDistZeroSix))
					{
						wWall = true;
					}
				}
				if(currentCell.isSouthWall())
				{
					if(camPosZ <= (fieldPosZ + wallDistZeroSix))
					{				
						sWall = true;
					}
				}
				if(wWall == true && sWall == false){
					this.eye.z -= deltaTime * 1;
					System.out.println("Wall 1 hit");
					return;
				}
				else if(wWall == false && sWall == true){
					this.eye.x += deltaTime * 1;
					System.out.println("Wall 2 hit");
					return;
				}
				else if(wWall == true && sWall == true){
					System.out.println("Trapped!");
					return;
				}
			}
		}
		
		this.slide(0.0f, 0.0f, forwards * deltaTime);	
	}
}

