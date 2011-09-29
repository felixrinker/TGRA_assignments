package com.tgra.ws11.structures;

/**
 * 
 * @author Felix Rinker
 *
 */
public class ObjectReference {

	private int firstIndex;
	private int vertexCount;
	private int openGLPrimitiveType;
	
	
	/**
	 * Constructs an reference
	 * an object 
	 * 
	 * @param firstIndex
	 * @param vertexCount
	 * @param openGLPrimitiveType
	 */
	public ObjectReference( int firstIndex, int vertexCount, int openGLPrimitiveType ) {
		
		this.firstIndex = firstIndex;
		this.vertexCount = vertexCount;
		this.openGLPrimitiveType = openGLPrimitiveType;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public int getOpenGLPrimitiveType() {
		return openGLPrimitiveType;
	}

	public void setOpenGLPrimitiveType(int openGLPrimitiveType) {
		this.openGLPrimitiveType = openGLPrimitiveType;
	}
}
