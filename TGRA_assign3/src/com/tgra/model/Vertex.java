package com.tgra.model;

public class Vertex extends Vector3D{

	float u;
	float v;

	public Vertex(float xx, float yy, float zz,float uu,float vv) {
		super(xx, yy, zz);
		u = uu;
		v = vv;
	}

}
