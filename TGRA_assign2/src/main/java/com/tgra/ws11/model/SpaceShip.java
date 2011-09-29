package com.tgra.ws11.model;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

import com.tgra.ws11.core.TransformationMatrix;
import com.tgra.ws11.structures.ObjectReference;
import com.tgra.ws11.structures.Point2D;

public class SpaceShip {

	private float width;
	private float height;
	private float positionX;
	private float positionY;
	private float angle;
	float[] direction;
	private float speed;
	private ObjectReference objRef;
	
	public SpaceShip (float width, float height, Vector<Point2D> vertexList) {
		objRef = new ObjectReference(vertexList.size(), 6, GL11.GL_TRIANGLE_FAN);
		this.speed= 1.0f;
		this.angle = -90f;
		this.direction = new float[]{1.5f, 0f, 0f, 0f};
		vertexList.add(new Point2D(-width/4.0f, height/2.0f));
		vertexList.add(new Point2D(width/4.0f, height/2.0f));
		vertexList.add(new Point2D(width/2.0f, height/4.0f));
		vertexList.add(new Point2D(width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, height/4.0f));
	}
	

	public void draw() {
		
		
		this.positionX += this.direction[0]*speed;
		this.positionY += this.direction[1]*speed;
		
		if(this.positionX >= Gdx.graphics.getWidth()) {
			this.positionX = 0;
		}else if(this.positionX <= 0) this.positionX = Gdx.graphics.getWidth();
		if(this.positionY >= Gdx.graphics.getHeight()) {
			this.positionY = 0;
		}else if(this.positionY <= 0) this.positionY = Gdx.graphics.getHeight();
		
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(this.positionX, this.positionY, 0);
		
		Gdx.gl11.glMultMatrixf(TransformationMatrix.getRotationMatrix(angle), 0);
		
		Gdx.gl11.glDrawArrays(objRef.getOpenGLPrimitiveType(), objRef.getFirstIndex(), objRef.getVertexCount());
		Gdx.gl11.glPopMatrix();
		
	}
	public float getWidth() {
		return width;
	}



	public void setWidth(float width) {
		this.width = width;
	}



	public float getHeight() {
		return height;
	}



	public void setHeight(float height) {
		this.height = height;
	}



	public float getPositionX() {
		return positionX;
	}



	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}


	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}


	public void addToX(float positionX) {
		this.speed += positionX;
	}

	public void addToY(float positionY) {
		this.speed -= positionY;
	}


	public float getPositionY() {
		return positionY;
	}


	public float getAngle() {
		return angle;
	}


	public void changeAngle(float angle) {
		this.angle += angle;
		this.direction = TransformationMatrix.multiplyVectorAndMatrix(TransformationMatrix.getRotationMatrix(angle), direction);
	}
}