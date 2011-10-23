package com.tgra.model;

import com.badlogic.gdx.Gdx;

/**
 * 
 * @author Felix Rinker
 *
 */
public class Maze {

	private Cell[][] cells;
	
	
	
	
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
	public boolean hastWestWall( int x, int y ) {
		
		return this.cells[x][y].isWestWall();
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hastEastWall( int x, int y ) {
		
		return this.cells[x+1][y].isWestWall();
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hastSouthWall( int x, int y ) {
		
		return this.cells[x][y].isSouthWall();
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hastNorthWall( int x, int y ) {
		
		return this.cells[x+1][y+1].isSouthWall();
	}
}
