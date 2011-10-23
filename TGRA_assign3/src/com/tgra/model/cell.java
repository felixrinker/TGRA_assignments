package com.tgra.model;

import com.badlogic.gdx.Gdx;

/**
 * 
 * @author Felix Rinker
 *
 */
public class Cell {

	private boolean westWall;
	private boolean southWall;
	private Cube cube;
	
	
	
	public Cell() {
		super();
		cube = new Cube();
	}


	public void draw() {
		
		if(this.isSouthWall()) {
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef( 0.5f, 0.5f, 0.0f );
			Gdx.gl11.glScalef(1.0f, 1.0f, 0.15f);
			
			cube.draw();
			
			Gdx.gl11.glPopMatrix();
		}
		
		if(this.isWestWall()) {
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef( 0.0f, 0.5f, 0.5f );
			Gdx.gl11.glScalef(0.15f, 1.0f, 1.0f);
			
			cube.draw();
			
			Gdx.gl11.glPopMatrix();
		}
		
	}
	
	
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
