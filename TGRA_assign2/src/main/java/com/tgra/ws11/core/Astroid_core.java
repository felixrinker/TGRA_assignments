package com.tgra.ws11.core;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import com.tgra.ws11.model.SpaceShip;
import com.tgra.ws11.structures.ObjectPosition;
import com.tgra.ws11.structures.ObjectReference;
import com.tgra.ws11.structures.Point2D;
import com.tgra.ws11.structures.PrimitiveObject;

/**
 * 
 * @author Felix Rinker
 * 
 */

public class Astroid_core implements ApplicationListener {

	private Vector<Point2D> vertexList;
	private Map<String, PrimitiveObject> createdObjects;
	private SpaceShip spaceShip;
	
	public void create() {

		this.vertexList		= new Vector<Point2D>();
		
		// create and load init objects
		loadInitObjects();
		
		// load vertexList to vertexBuffer
		int floatBufferSize			= vertexList.size() * 2;
		FloatBuffer vertexBuffer	= BufferUtils.newFloatBuffer(floatBufferSize);
		float[] vertexArray			= new float[floatBufferSize];
		
		for(int i = 0; i < vertexList.size(); i++) {
		      vertexArray[i*2]	 = vertexList.get(i).getX();
		      vertexArray[i*2+1] = vertexList.get(i).getY();
		}
		
		vertexBuffer.put(vertexArray);
		vertexBuffer.rewind();
		Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, vertexBuffer);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		// set clear color
		Gdx.gl11.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
	}
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void pause() {

	}

	public void render() {

		update();
		display();
	}

	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}

	/************************************************* PRIVATE METHODS **************************************************/

	/**
	 * UPDATE CODE
	 */
	private void update() {
		
		float deltaTime = Gdx.graphics.getDeltaTime();

		// get object to update
		//PrimitiveObject littleBox = this.createdObjects.get("spaceShip");
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {	
			spaceShip.changeAngle(-180.0f*deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			spaceShip.changeAngle(+180.0f*deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			spaceShip.addToY(-0.3f * deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			spaceShip.addToY(0.3f * deltaTime);	
		}
	}

	/**
	 * GRAPHICS CODE
	 */
	private void display() {
		
		Gdx.gl11.glClearColor(0.0f,0.0f,0.2f,1.0f);
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		Gdx.gl11.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluOrtho2D(Gdx.gl10, 0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());
		
		// draw objects
		/*for(PrimitiveObject object : createdObjects.values()) {
			
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(object.getPos().getX(), object.getPos().getY(), 0);
			this.drawObject(object.getObjectRef());
			Gdx.gl11.glPopMatrix();
		}*/
		
		this.spaceShip.draw();
	}
	
	
	/**
	 * 
	 * @param objRef
	 */
	private void drawObject(ObjectReference objRef) {
	       Gdx.gl11.glDrawArrays(objRef.getOpenGLPrimitiveType(), objRef.getFirstIndex(), objRef.getVertexCount());
	}
	
	
	/**
	 * creates the init objects
	 * 
	 * @return a HashMap with then names (ID) and objects
	 */
	private void loadInitObjects() {
		
		spaceShip = new SpaceShip(20, 40, this.vertexList);
		spaceShip.setPositionX(300);
		spaceShip.setPositionY(400);
	}
	
	/**
	 * 
	 * @param objectRef to the object
	 * @param x position
	 * @param y position
	 * 
	 * @return PrimitiveObject
	 */
	private PrimitiveObject createPrimitveObjectAtPos(ObjectReference objectRef, float x, float y) {
		
		return new PrimitiveObject(objectRef, new ObjectPosition(x,y));
	}
	
	
/************************************* OBJECTS ***********************************************/
	
}
