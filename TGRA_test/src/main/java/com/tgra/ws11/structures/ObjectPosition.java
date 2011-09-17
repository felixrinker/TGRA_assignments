package com.tgra.ws11.structures;

/**
 * 
 * @author Felix Rinker
 *
 */
public class ObjectPosition {

	private float x;					// x position
	private float y;					// y position
	
	/**
	 * 
	 * @param x position of the obj
	 * @param y position of the obj
	 */
	public ObjectPosition(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void addToX(float add) {
		this.x += add;
	}
	
	public void addToY(float add) {
		this.y += add;
	}
	
	/****************** GETTER / SETTER *******************************/
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
