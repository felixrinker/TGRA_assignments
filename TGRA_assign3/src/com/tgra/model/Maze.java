package com.tgra.model;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.tgra.camera.MazeCam;
import com.tgra.core.MazeGenerator;

/**
 * 
 * @author Felix Rinker
 *
 */
public class Maze {

	private Cell[][] cells;
	private MazeCam cam;
	private Mesh model;
	private Cell endCell;
	private SpriteBatch batch;
	private BitmapFont font;
	private boolean finish;
	private boolean start;
	private Skybox sky;
	private Sphere sphere;
	private SkyDome skyDome;
	private Sky ss;
	private static int MAZE_LENGTH = 5;
	private static int MAZE_HEIGHT = 5;	
	
	public Maze() {
		
		MazeGenerator mazeGen = new MazeGenerator(MAZE_LENGTH,MAZE_HEIGHT);
		mazeGen.generatePath();
		mazeGen.display();
		this.cells = mazeGen.getCellArray();
		
		try {
			InputStream in = Gdx.files.internal("assets/data/venusm.obj").read();
			model =  ObjLoader.loadObj(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.finish = false;
		this.start = true;
		this.batch = new SpriteBatch();
	    this.font = new BitmapFont();
		
		this.endCell = this.cells[MAZE_LENGTH-1][MAZE_HEIGHT-2];
		
		sky = new Skybox("assets/textures/skybox.png");
		sphere = new Sphere(500, 480);
		skyDome = new SkyDome(10, 60, 240);
		ss = new Sky(10f, 60f, 240f, 10f, 10f);
		
	}

	/**
	 * Draws all components in the maze
	 */
	public void draw() {
		if(!finish) {
			
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef( 2.5f, 0f, 2.5f );
			//Gdx.gl11.glScalef(4f, 4f, 4f);
			//this.sphere.draw();
			this.skyDome.draw();
			
			//this.ss.draw();
			
		Gdx.gl11.glPopMatrix();
			
			
			//this.sky.draw();
			this.drawMaze();
			this.drawEndModel();
		} 
		if(finish) this.drawEndScreen();
	}
	
	/**
	 * Perform the updates in the maze
	 */
	public void update() {
			
		float coordX = (cam.getEye().x + 0.5f);
        float coordZ = (cam.getEye().z + 0.5f);
       
        this.detectCollision(coordX, coordZ);
		
		Cell cuCell = this.getCellForCorrd(coordX, coordZ);
		if(cuCell.equals(endCell)) this.finish = true;
	}
	
//////////////////////////////////////////WALL Privates //////////////////////////////////////////////
	/**
	 * 
	 * @param coordX
	 * @param coordZ
	 */
	private void detectCollision( float coordX, float coordZ) {
		
		 int posX = (int)Math.floor(coordX);
	        int posZ = (int)Math.floor(coordZ);

	        if (coordX - posX > 0.8f) {
	            if (hasWestWall(posX, posZ)) {
	            	cam.getEye().x = posX + 0.3f;
	             	System.out.println("West");
	            }             
	        }
	        if (coordX - posX < 0.2f) {
	            if (hasEastWall(posX, posZ)) {
	            	 cam.getEye().x = posX - 0.3f;
	            	 System.out.println("EAST");
	            }          
	        }
	        if (coordZ - posZ < 0.2f) {
	            if (hasSouthWall(posX, posZ)){
	            	cam.getEye().z = posZ - 0.3f;
	            	System.out.println("South");
	            }     
	        }
	        if (coordZ - posZ > 0.8f) {
	            if (hasNorthWall(posX, posZ)) {
	            	 cam.getEye().z = posZ + 0.3f;
	                 System.out.println("North");
	            }     
	        }
	}
	
	/**
	 * 
	 * @param coordX
	 * @param coordZ
	 * @return
	 */
	private Cell getCellForCorrd(float coordX,float coordZ) {
	
		return this.getCellForPos((int)Math.floor(coordX), (int)Math.floor(coordZ));
	}
	
	/**
	 * 
	 * @param posX
	 * @param poxZ
	 * @return
	 */
	private Cell getCellForPos( int posX, int poxZ ) {
		
		if(posX < 0) posX = 0;
		if(posX > this.cells.length)  posX = this.cells.length;
		
		if(poxZ < 0) poxZ= 0;
		if(poxZ > this.cells.length) poxZ = this.cells.length;
		
		return this.cells[posX][poxZ];
	}

	/**
	 * Draws the maze
	 * 
	 */
	private void drawMaze() {
		for (int row=0; row < MAZE_HEIGHT; row++)
		{
		    for (int col=0; col < MAZE_LENGTH; col++)
		    {
		    	Gdx.gl11.glPushMatrix();
					Gdx.gl11.glTranslatef( (float) col, 0.0f, (float) row );
					cells[col][row].draw();
				Gdx.gl11.glPopMatrix();
		    }
		}
	}
	
	/**
	 * Draws the end model
	 */
	private void  drawEndModel() {
		float scale = 0.0003f;
		Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef( endCell.getPosX(), 0.0f, (float) endCell.getPosY() );
			Gdx.gl11.glScalef(scale, scale, scale);
			Gdx.gl11.glRotatef(180f,0, 1, 0);
			model.render(GL11.GL_TRIANGLES);
		Gdx.gl11.glPopMatrix();

	}
	
	/**
	 * Draw end screen
	 */
	private void drawEndScreen() {
		Gdx.gl11.glDisable(GL11.GL_LIGHTING);
		
		Gdx.gl11.glClearColor(0.0f,0.0f,0.2f,1.0f);
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
            batch.begin();
            font.setColor(0.9f, 0.0f, 1.0f, 1.0f);
            font.setScale(4.0f);
            font.draw(batch, "FOUND GOAL", 110, 350);
            font.setScale(2.0f);
            font.setColor(0.9f, 1.0f, 1.0f, 1.0f);
            font.draw(batch, "Press Enter for a new Game", 110, 220);
            batch.end();
	}
//////////////////////////////////////WALL GETTER ////////////////////////////////////////////////

	public boolean hasWestWall( int posX, int posZ ) {
		return this.cells[posX][posZ].isWestWall();
	}

	public boolean hasEastWall( int posX, int posZ ) {
		return this.cells[posX-1][posZ].isWestWall();
	}
	
	public boolean hasSouthWall( int posX, int posZ ) {
		return this.cells[posX][posZ].isSouthWall();
	}
	
	public boolean hasNorthWall( int posX, int posZ ) {
			return this.cells[posX][posZ+1].isSouthWall();
	}

	public void setCam(MazeCam cam) {
		this.cam = cam;
	}

	public Cell getEndCell() {
		return endCell;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}
}
