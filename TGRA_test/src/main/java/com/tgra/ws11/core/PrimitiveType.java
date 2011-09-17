package com.tgra.ws11.core;

import java.util.Vector;

import org.lwjgl.opengl.GL11;

import com.tgra.ws11.structures.ObjectReference;
import com.tgra.ws11.structures.Point2D;


/**
 * 
 * @author Felix Rinker
 *
 */
public class PrimitiveType {

	/**
	 * 
	 * @param width
	 * @param height
	 * @param vertexList
	 * @return
	 */
	public static ObjectReference createBox(float width, float height, Vector<Point2D> vertexList) {
		
		ObjectReference objRef = new ObjectReference(vertexList.size(), 4, GL11.GL_TRIANGLE_STRIP);
		
		vertexList.add( new Point2D(-width/2.0f,-height/2.0f) );
		vertexList.add( new Point2D(-width/2.0f, height/2.0f) );
		vertexList.add( new Point2D(width/2.0f, -height/2.0f) );
		vertexList.add( new Point2D(width/2.0f, height/2.0f) ) ;
		
		return objRef;
	}	
}
