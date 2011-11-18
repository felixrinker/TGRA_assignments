package com.tgra.model;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * 
 * @author Felix Rinker
 *
 */
public class Cell {

	private boolean westWall;
	private boolean southWall;
	private Cube cube;
	private Texture floorTexture;
	private FloatBuffer texCoordBuffer;
	private Texture wallTexture;
	private int posX;
	private int posY;
	private Cube wallCube;
	private Cube floorCube;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param westWall
	 * @param southWall
	 * @param wallCube
	 * @param floorCube
	 */
	public Cell( int x, int y, boolean westWall, boolean southWall, Cube wallCube, Cube floorCube) {
		super();
		
		this.posX = x;
		this.posY = y;
		
		this.westWall	= westWall;
		this.southWall	= southWall;
		
		this.wallCube	= wallCube;
		this.floorCube	= floorCube;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Cell(int x, int y) {
		this(x, y, true, true, new Cube(), new Cube());
	};
	
	public Cell(int x, int y, Cube wallCube, Cube floorCube) {
		this(x, y, true, true, wallCube, floorCube);
	};
	
	/**
	 * 
	 */
	public void draw() {
		Gdx.gl11.glPushMatrix();
			Gdx.gl11.glScalef(1.0f, 0.15f, 1.0f);
			this.floorCube.draw();
		Gdx.gl11.glPopMatrix();
		
		if(this.isSouthWall()) {
			Gdx.gl11.glPushMatrix();
				Gdx.gl11.glTranslatef( 0.0f, 0.5f, -0.5f );
				Gdx.gl11.glScalef(1.0f, 1.0f, 0.05f);
				this.wallCube.draw();
			Gdx.gl11.glPopMatrix();
		}
		
		if(this.isWestWall()) {
			Gdx.gl11.glPushMatrix();
				Gdx.gl11.glTranslatef( 0.5f, 0.5f, 0.0f );
				Gdx.gl11.glScalef(0.05f, 1.0f, 1.0f);
				this.wallCube.draw();
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

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	@Override
	public String toString() {
		return "Cell [westWall=" + westWall + ", southWall=" + southWall + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cube == null) ? 0 : cube.hashCode());
		result = prime * result
				+ ((floorTexture == null) ? 0 : floorTexture.hashCode());
		result = prime * result + posX;
		result = prime * result + posY;
		result = prime * result + (southWall ? 1231 : 1237);
		result = prime * result
				+ ((texCoordBuffer == null) ? 0 : texCoordBuffer.hashCode());
		result = prime * result
				+ ((wallTexture == null) ? 0 : wallTexture.hashCode());
		result = prime * result + (westWall ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (cube == null) {
			if (other.cube != null)
				return false;
		} else if (!cube.equals(other.cube))
			return false;
		if (floorTexture == null) {
			if (other.floorTexture != null)
				return false;
		} else if (!floorTexture.equals(other.floorTexture))
			return false;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		if (southWall != other.southWall)
			return false;
		if (texCoordBuffer == null) {
			if (other.texCoordBuffer != null)
				return false;
		} else if (!texCoordBuffer.equals(other.texCoordBuffer))
			return false;
		if (wallTexture == null) {
			if (other.wallTexture != null)
				return false;
		} else if (!wallTexture.equals(other.wallTexture))
			return false;
		if (westWall != other.westWall)
			return false;
		return true;
	}
}
