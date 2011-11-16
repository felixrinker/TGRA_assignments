package com.tgra.camera;

import com.badlogic.gdx.graphics.GL11;

import com.badlogic.gdx.Gdx;
import com.tgra.model.Point3D;
import com.tgra.model.Vector3D;

public class MazeCam {

	private Point3D eye;
	private Vector3D n;
	private Vector3D u;
	private Vector3D v;

	public  MazeCam(Point3D pEye, Point3D pCenter, Vector3D up)
	{
		this.eye = pEye;
		this.n = Vector3D.difference(pEye, pCenter);
		this.n.normalize();
		this.u = Vector3D.cross(up, n);
		this.u.normalize();
		this.v = Vector3D.cross(n, u);
	}
	
	/**
	 * set Model View Matrix
	 */
	public void setModelViewMatrix()
	{
		Vector3D minusEye = Vector3D.difference(new Point3D(0,0,0), eye);
		
		float[] matrix = new float[16];
		matrix[0] = u.x;	matrix[4] = u.y;	matrix[8] = u.z;	matrix[12] = Vector3D.dot(minusEye, u);
		matrix[1] = v.x;	matrix[5] = v.y;	matrix[9] = v.z;	matrix[13] = Vector3D.dot(minusEye, v);
		matrix[2] = n.x;	matrix[6] = n.y;	matrix[10] = n.z;	matrix[14] = Vector3D.dot(minusEye, n);
		matrix[3] = 0;		matrix[7] = 0;		matrix[11] = 0;		matrix[15] = 1;
		
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadMatrixf(matrix, 0);
	}
	
	/**
	 * Slide on the axis.
	 * 
	 * @param delU
	 * @param delV
	 * @param delN
	 */
	public void slide(float delU, float delV, float delN)
	{
		eye.add(Vector3D.sum(Vector3D.mult(delU, u), Vector3D.sum(Vector3D.mult(delV, v), Vector3D.mult(delN, n))));
	}

	/**
	 * Look to the sides.
	 * Turn around v.
	 * 
	 * @param angle the angle
	 */
	public void yaw(float angle)
	{
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = u;
		u = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, n));
		n = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, n));
	}

	/**
	 * Rotation around n-axis.
	 * 
	 * @param angle the angle
	 */
	public void roll(float angle)
	{
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = u;
		u = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, v));
		v = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, v));
	}

	/**
	 * Look up and down. Turn around u.
	 * @param angle
	 */
	public void pitch(float angle)
	{
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = v;
		v = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, n));
		n = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, n));
	}
	
	public Point3D getEye() {
		return eye;
	}

	public Vector3D getN() {
		return n;
	}
}

