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
	private static int MAZE_LENGTH = 10;
	private static int MAZE_HEIGHT = 20;	
	
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
		
	}
	
	private void  drawEndModel() {
		float scale = 0.0003f;
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef( endCell.getPosX()+0.5f, 0.0f, (float) endCell.getPosY()+0.5f );
		Gdx.gl11.glScalef(scale, scale, scale);
		
		Gdx.gl11.glRotatef(180f,0, 1, 0);
		model.render(GL11.GL_TRIANGLES);
		
		Gdx.gl11.glPopMatrix();

	}

	public void draw() {
		
		
	if(!finish) {	
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
		
		this.drawEndModel();
	} 
	if(finish){
		
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
	}
	
	public void update() {
		
		Cell cuCell = cam.getCurrentCell();
		
		if(cuCell.equals(endCell)) this.finish = true;
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
	
	public Cell getCell( int x, int y ) {
		
		if(x < 0) x = 0;
		if(x > this.cells.length)  x = this.cells.length;
		
		if(y < 0) y= 0;
		if(y > this.cells.length) y = this.cells.length;
		
		return this.cells[x][y];
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
