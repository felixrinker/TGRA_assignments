package com.tgra.ws11.model;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

import com.tgra.ws11.core.TransformationMatrix;
import com.tgra.ws11.structures.ObjectReference;
import com.tgra.ws11.structures.Point2D;
/**
 * 
 * @author Felix Rinker
 * @author Sara Van de Moosdijk
 *
 */
public class SpaceShip {

	private float positionX;
	private float positionY;
	private float angle;
	float[] direction;
	private float speed;
	private float speedChange;
	private ObjectReference objRef;
	private Vector<Point2D> vertexList;
	
	public SpaceShip (float width, float height, Vector<Point2D> vertexList) {
		objRef = new ObjectReference(vertexList.size(), 6, GL11.GL_TRIANGLE_FAN);
		
		this.vertexList = vertexList;
		this.speed= 1.5f;
		this.angle = -90f;
		this.speedChange = 1.0f;
		
		this.direction = new float[]{speed, 0f, 0f, 0f};
		vertexList.add(new Point2D(-width/4.0f, height/2.0f));
		vertexList.add(new Point2D(width/4.0f, height/2.0f));
		vertexList.add(new Point2D(width/2.0f, height/4.0f));
		vertexList.add(new Point2D(width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, height/4.0f));
	}

	/**
	 * 
	 */
	public void draw() {
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glColor4f(0f, 0f, 0.9f, 1.0f);
		Gdx.gl11.glTranslatef(this.positionX, this.positionY, 0);
		Gdx.gl11.glMultMatrixf(TransformationMatrix.rotationMatrix(angle), 0);
		Gdx.gl11.glDrawArrays(objRef.getOpenGLPrimitiveType(), objRef.getFirstIndex(), objRef.getVertexCount());
		Gdx.gl11.glPopMatrix();
		
	}
	
	/**
	 * 
	 */
	public void update() {
		this.positionX += this.direction[0]*speed;
		this.positionY += this.direction[1]*speed;
		
		if(this.positionX >= Gdx.graphics.getWidth()) {
			this.positionX = 0;
		}else if(this.positionX <= 0) this.positionX = Gdx.graphics.getWidth();
		if(this.positionY >= Gdx.graphics.getHeight()) {
			this.positionY = 0;
		}else if(this.positionY <= 0) this.positionY = Gdx.graphics.getHeight();
	}
	
	/**
	 * 
	 * @return new bullet
	 */
	public Bullet fireBullet() {
		Bullet bullet = new Bullet(7,3, this.positionX, this.positionY, this.angle, this.direction, this.vertexList);
		
		return bullet;
	}
	
/***************************** GETTER SETTER ***************************************/	


	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}


	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}


	public float getPositionX() {
		return positionX;
	}

	public float getPositionY() {
		return positionY;
	}


	public float getAngle() {
		return angle;
	}


	public float getSpeedChange() {
		return speedChange;
	}


	public void setSpeedChange(float speedChange) {
		this.speedChange = speedChange;
	}


	public void changeAngle(float angle) {
		this.angle += angle;
		this.direction = TransformationMatrix.multiplyVectorAndMatrix(TransformationMatrix.rotationMatrix(angle), direction);
	}
	
	public void changeSpeed(float speed) {
		this.speed += speed;
	}
}
