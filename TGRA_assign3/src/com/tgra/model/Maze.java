package com.tgra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tgra.camera.Camera;
import com.tgra.core.MazeGenerator;

/**
 * 
 * @author Felix Rinker
 *
 */
public class Maze {

	private Cell[][] cells;
	private Camera cam;
	private Cell currentCell;
	private float angle;
	private static int MAZE_LENGTH = 3;
	private static int MAZE_WIDTH = 3;	
	
	public Maze() {
		
		MazeGenerator mazeGen = new MazeGenerator(9,9);
		mazeGen.generatePath();
		mazeGen.display();
		this.cells = mazeGen.getCellArray();
	}

	public void draw() {
		
		
		for (int row=0; row < cells.length; row++)
		{
		    for (int col=0; col < cells[row].length; col++)
		    {
		    	
		    	//System.out.println("h:"+cells.length+"l: "+cells[row].length);
		    	
		    	Gdx.gl11.glPushMatrix();
				Gdx.gl11.glTranslatef( (float) col, 0.0f, (float) row );
				//Gdx.gl11.glScalef(0.95f, 0.95f, 0.95f);
				cells[col][row].draw();
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
	
	public Cell getCell( int x, int y ) {
		
		return this.cells[x][y];
	}
}
