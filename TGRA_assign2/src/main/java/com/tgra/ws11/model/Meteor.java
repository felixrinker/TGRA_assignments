package com.tgra.ws11.model;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

import com.tgra.ws11.core.TransformationMatrix;

import com.tgra.ws11.structures.ObjectReference;
import com.tgra.ws11.structures.Point2D;


public class Meteor {

	private float radius;
	private float positionX;
	private float positionY;
	private float angle;
	private float[] direction;
	private float speed;
	private int slices;
	private ObjectReference objRef;
	private Vector<Point2D> vertexList;
	
	public Meteor (float radius, float angle, Vector<Point2D> vertexList) {
		this.vertexList = vertexList;
		this.angle = angle;
		this.speed= 1.8f;
		this.slices = 40;
		this.direction = new float[]{1.5f, 0f, 0f, 0f};
		
		objRef = new ObjectReference(vertexList.size(),slices,GL11.GL_TRIANGLE_FAN);
		for(float f=0; f<2*Math.PI; f+=(float)2*Math.PI/(float)slices) {
			vertexList.add(new Point2D((float)Math.cos(f)*radius,(float)Math.sin(f)*radius));
		}
		
		this.direction = TransformationMatrix.multiplyVectorAndMatrix(TransformationMatrix.getRotationMatrix(angle), direction);	}
	
	public Meteor (float radius, float angle, float positionX, float positionY, Vector<Point2D> vertexList) {
		
		this.positionX = positionX;
		this.positionY = positionY;		
		this.vertexList = vertexList;
		this.angle = angle;
		
		this.speed= 1.8f;
		this.slices = 40;
		this.direction = new float[]{1.5f, 0f, 0f, 0f};
		
		objRef = new ObjectReference(vertexList.size(),slices,GL11.GL_TRIANGLE_FAN);
		for(float f=0; f<2*Math.PI; f+=(float)2*Math.PI/(float)slices) {
			vertexList.add(new Point2D((float)Math.cos(f)*radius,(float)Math.sin(f)*radius));
		}
		
		this.direction = TransformationMatrix.multiplyVectorAndMatrix(TransformationMatrix.getRotationMatrix(angle), direction);
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
		
		//Gdx.gl11.glMultMatrixf(TransformationMatrix.getRotationMatrix(angle), 0);
		
		Gdx.gl11.glDrawArrays(objRef.getOpenGLPrimitiveType(), objRef.getFirstIndex(), objRef.getVertexCount());
		Gdx.gl11.glPopMatrix();
		
	}
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
		this.direction = TransformationMatrix.multiplyVectorAndMatrix(TransformationMatrix.getRotationMatrix(angle), direction);
	}
	
	public void changeSpeed(float speed) {
		this.speed += speed;
	}
}
