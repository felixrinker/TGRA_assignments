package com.tgra.model;
/**
 * 
 * @author Felix Rinker
 *
 */
public class Maze {

	Cell[][] cells;
	
	
	
	
	
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
