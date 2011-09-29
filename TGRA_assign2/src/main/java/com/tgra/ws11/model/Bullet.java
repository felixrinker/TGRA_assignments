package com.tgra.ws11.model;

import java.util.Arrays;
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
public class Bullet {

	private float radius;
	private float positionX;
	private float positionY;
	private float angle;
	private float[] direction;
	private float speed;
	private ObjectReference objRef;
	
	/**
	 * 
	 * @param radius
	 * @param angle
	 * @param vertexList
	 */
	public Bullet (float width, float height, Vector<Point2D> vertexList) {
		this.speed= 0.5f;
		this.angle = 90f;
		this.direction = new float[]{speed, 0f, 0f, 0f};
		
		objRef = new ObjectReference(vertexList.size(),4,GL11.GL_TRIANGLE_STRIP);
		vertexList.add(new Point2D(-width/2.0f, height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(width/2.0f, height/2.0f));
		vertexList.add(new Point2D(width/2.0f, -height/2.0f));
	}
	
	/**
	 * 
	 * @param width
	 * @param angle
	 * @param positionX
	 * @param positionY
	 * @param vertexList
	 */
	public Bullet (float width, float height, float positionX, float positionY, Vector<Point2D> vertexList) {
		
		this(width, height, vertexList);
		
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public Bullet (float width, float height, float positionX, float positionY, float angle, float[] direction, Vector<Point2D> vertexList) {
		
		
		this(width, height, positionX, positionY, vertexList);
		
		this.angle += angle;
		
		this.direction = TransformationMatrix.multiplyVectorAndMatrix(TransformationMatrix.getIdentityMatrix(), direction);
		//this.direction = TransformationMatrix.multiplyMatrix(TransformationMatrix.getIdentityMatrix(), TransformationMatrix.rotationMatrix(angle));
	}
	
	/**
	 * 
	 */
	public void draw() {
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glColor4f(1.0f, 0.5f, 0.5f, 1.0f);
		Gdx.gl11.glTranslatef(this.positionX, this.positionY, 0);
		Gdx.gl11.glMultMatrixf(TransformationMatrix.rotationMatrix(angle), 0);
		Gdx.gl11.glDrawArrays(objRef.getOpenGLPrimitiveType(), objRef.getFirstIndex(), objRef.getVertexCount());
		Gdx.gl11.glPopMatrix();	
	}
	
	/**
	 * 
	 */
	public void update () {
		
		this.positionX += this.direction[0]*speed;
		this.positionY += this.direction[1]*speed;
		
		if(this.positionX >= Gdx.graphics.getWidth()) {
			this.positionX = 0;
		}else if(this.positionX <= 0) this.positionX = Gdx.graphics.getWidth();
		if(this.positionY >= Gdx.graphics.getHeight()) {
			this.positionY = 0;
		}else if(this.positionY <= 0) this.positionY = Gdx.graphics.getHeight();
	}
	
	/***************************** GETTER SETTER ***************************************/		
	public float getWidth() {
		return radius;
	}



	public void setWidth(float width) {
		this.radius = width;
	}



	public float getHeight() {
		return angle;
	}



	public void setHeight(float height) {
		this.angle = height;
	}


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


	public void changeAngle(float angle) {
		this.angle += angle;
		this.direction = TransformationMatrix.multiplyVectorAndMatrix(TransformationMatrix.rotationMatrix(angle), direction);
		
	}
	
	public void changeSpeed(float speed) {
		this.speed += speed;
	}

	@Override
	public String toString() {
		return "Bullet [radius=" + radius + ", positionX=" + positionX
				+ ", positionY=" + positionY + ", angle=" + angle
				+ ", direction=" + Arrays.toString(direction) + ", speed="
				+ speed + ", objRef=" + objRef + "]";
	}
}
