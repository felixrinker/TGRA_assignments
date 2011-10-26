package com.tgra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tgra.camera.Camera;

/**
 * 
 * @author Felix Rinker
 *
 */
public class Maze {

	private Cell[][] cells;
	private Camera cam;
	private Cell currentCell;
	
	private static int MAZE_LENGTH = 3;
	private static int MAZE_WIDTH = 3;	
	
	public Maze(Camera cam) {
		
		this.cam = cam;
		
		
		
		/*Cell swCell	= new Cell(true, true);
		Cell wCell	= new Cell(true, false);
		Cell sCell	= new Cell(false, true);
		Cell eCell	= new Cell();
		
		cells[0][0] = swCell;
		cells[0][1] = wCell;
		cells[0][2] = swCell;
		
		cells[1][0] = swCell;
		cells[1][1] = eCell;
		cells[1][2] = swCell;
		
		cells[2][0] = swCell;
		cells[2][1] = eCell;
		cells[2][2] = swCell;*/
	}
	
	public void update() {
		
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			cam.slide(0.0f, 0.0f, -1.0f * deltaTime);
			
			if(collide()) 
			{
				System.out.println("COLLIDE");
				cam.slide(0.0f, 0.0f, +1.0f * deltaTime);
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			cam.slide(0.0f, 0.0f, 1.0f * deltaTime);
			if(collide()) 
			{
				System.out.println("COLLIDE");
				cam.slide(0.0f, 0.0f, -1.0f * deltaTime);
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			cam.yaw(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			cam.yaw(90.0f * deltaTime);
		}
		
		
		
	//	currentCell = this.cells[(int) cam.getEye().x][(int) cam.getEye().z];
		
		//System.out.println("X: "+(int) cam.getEye().x+"Z: "+(int) cam.getEye().z);
	}
	
	
	private boolean collide() {
		
		float m = 0.35f;
		float m2 = 0.35f;
		float mm = 0.8f;
		float m3 = 1.3f;
		float eX = cam.getEye().x;
		float eZ = cam.getEye().z;
		
		float zT = eZ+m2;
		float zB = eZ-m3;
		float xR = eX+m;
		float xL = eX-mm;
		
		int x = (int) eX;
		int z = (int) eZ;
		
		int intXL =(int) Math.round(xL);
		
		//System.out.println("X1: "+ intXL+"X2: "+xR+"X3: "+eX+"X: "+x+"");
		System.out.println("X1: "+ zT+"X2: "+zB+"X3: "+eZ+"X: "+z+"");
		
		

		
		if(intXL < x && this.hasWestWall(x, z)) return true;
		if(((int) xR) > x && this.hasEastWall(x, z)) return true;
		
		if(((int) zB) < z && this.hasSouthWall(x, z)) return true;
		if(((int) zT) > z && this.hasNorthWall(x, z)) return true;
		
		
			
		return false;
	}

	public void draw() {
		
		
		for (int row=0; row < cells.length; row++)
		{
		    for (int col=0; col < cells[row].length; col++)
		    {
		    	Gdx.gl11.glPushMatrix();
				Gdx.gl11.glTranslatef( (float) row, 0.0f, (float) col );
				//Gdx.gl11.glScalef(0.95f, 0.95f, 0.95f);
				cells[row][col].draw();
				Gdx.gl11.glPopMatrix();
		    }
		}
	}
	
	//////////////////////// WALL GETTER ///////////////////////////
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasWestWall( int x, int y ) {
		
		return this.cells[x][y].isWestWall();
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasEastWall( int x, int y ) {
		
		return this.cells[x+1][y].isWestWall();
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasSouthWall( int x, int y ) {
		
		return this.cells[x][y].isSouthWall();
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasNorthWall( int x, int y ) {
		
		return this.cells[x][y+1].isSouthWall();
	}
	
	/*public Cell getCurrentCell( int x, int y ) {
		
		return this.cells[x][y]
	}*/
}
