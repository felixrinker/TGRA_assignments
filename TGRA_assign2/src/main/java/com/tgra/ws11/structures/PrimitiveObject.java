package com.tgra.ws11.structures;

/**
 * 
 * @author Felix Rinker
 *
 */
public class PrimitiveObject {

	private ObjectReference objectRef;	// reference to the object
	private ObjectPosition	objectPos;	// position of the object
	
	/**
	 * 
	 * @param objectRef reference to the object
	 * @param objectPos position of the object
	 */
	public PrimitiveObject(ObjectReference objectRef, ObjectPosition objectPos) {
		super();
		this.objectRef = objectRef;
		this.objectPos = objectPos;
	}

	public ObjectReference getObjectRef() {
		return objectRef;
	}

	public void setObjectRef(ObjectReference objectRef) {
		this.objectRef = objectRef;
	}

	public ObjectPosition getPos() {
		return objectPos;
	}

	public void setObjectPos(ObjectPosition objectPos) {
		this.objectPos = objectPos;
	}
}
