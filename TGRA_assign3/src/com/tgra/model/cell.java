package com.tgra.model;
/**
 * 
 * @author Felix Rinker
 *
 */
public class Cell {

	boolean westWall;
	boolean southWall;
	
	
	
	
	///////////////////////// GETTER / SETTER ////////////////////////////
	
	/**
	 * 
	 * @param westWall
	 */
	public void setWestWall(boolean westWall) {
		this.westWall = westWall;
	}
	
	/**
	 * 
	 * @param southWall
	 */
	public void setSouthWall(boolean southWall) {
		this.southWall = southWall;
	}	
	
	/**
	 * 
	 * @return
	 */
	public boolean isSouthWall() {
		return southWall;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isWestWall() {
		return westWall;
	}
}
