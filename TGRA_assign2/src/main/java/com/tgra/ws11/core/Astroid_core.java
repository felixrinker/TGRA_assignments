package com.tgra.ws11.core;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import com.tgra.ws11.model.Bullet;
import com.tgra.ws11.model.Meteor;
import com.tgra.ws11.model.SpaceShip;
import com.tgra.ws11.structures.Point2D;

/**
 * 
 * @author Felix Rinker
 * @author Sara Van de Moosdijk
 *
 */

public class Astroid_core implements ApplicationListener {

	private Vector<Point2D> vertexList;
	private SpaceShip spaceShip;
	private ArrayList<Meteor> meteorList;
	private ArrayList<Bullet> bulletList;
	private long lastBulletShot;
	
	public void create() {

		this.vertexList		= new Vector<Point2D>();
		this.bulletList		= new ArrayList<Bullet>();
		
		// create and load init objects
		loadInitObjects();
		
		// load vertexList to vertexBuffer
		loadVertexList();
		
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		// set clear color
		Gdx.gl11.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
	}
	
	private void loadVertexList() {
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
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {	
			spaceShip.changeAngle(180.0f*deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			spaceShip.changeAngle(-180.0f*deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			spaceShip.changeSpeed(-spaceShip.getSpeedChange() * deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			spaceShip.changeSpeed(spaceShip.getSpeedChange() * deltaTime);	
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			
			if(this.checkNextShot()) {
				this.bulletList.add(spaceShip.fireBullet());
				loadVertexList();
				this.setLastShotTime(System.currentTimeMillis());
			}
		}
		
		
		
		this.spaceShip.update();
		
		/** 
		 * iterates over the meteors and call the update
		 * 
		 * @TODO check if they marked for delete in case
		 * they were hitted by a bullet
		 * 
		 * @TODO add score point to meteor
		 */
		for( Meteor m : meteorList) {
			m.update();
		}
		
		/**
		 * iterates over the bullets and call the update
		 * if the lifetime of the bullet is over delete it.
		 *
		 * @TODO collision detection i would put this
		 * for loop into the meteor loop and check for 
		 * every meteor if a bullet is hitting him 
		 * (beware of the size, during calc). If yes
		 * delete meteor / bullet. Maybe new function in
		 * bullet and meteor boolean toDelete.
		 * 
		 */
		ArrayList<Bullet> delete = new ArrayList<Bullet>();
		for(Bullet b : bulletList) {
			
			if(b.checkLife()) {
				b.update();
			}else {
				delete.add(b);
			}
		}
		
		bulletList.removeAll(delete);
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
		
		this.spaceShip.draw();
		
		for( Meteor m : meteorList) {
			m.draw();
		}
		
		for(Bullet b : bulletList) {
			b.draw();
		}
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
		
		loadLevelOneInitObjects();	
	}
	
	/**
	 * Creates and load objects for the (first) level
	 */
	private void loadLevelOneInitObjects() {
		
		meteorList = new ArrayList<Meteor>();
		
		//this.meteorList.add(new Meteor(20.0f, -170.0f, 200,100, this.vertexList));
		//this.meteorList.add(new Meteor(15.0f, -170.0f, 800,200, this.vertexList));
		this.meteorList.add(new Meteor(10.0f, -170.0f, 400,700, this.vertexList));
		this.meteorList.add(new Meteor(10.0f, -170.0f, 600,500, this.vertexList));
		this.meteorList.add(new Meteor(05.0f, -170.0f, 150,200, this.vertexList));
		
	}
	
	/**
	 * 
	 * @param lastShot the time of the last bullet shot
	 */
	private void setLastShotTime(long lastShot) {
		this.lastBulletShot = lastShot;
	}
	
	/**
	 * check if the waiting time is over
	 * 
	 * @return true if over, otherwise false
	 */
	private boolean checkNextShot() {
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		long diff =300+(long)deltaTime;
		
		if(System.currentTimeMillis() > this.lastBulletShot+diff) {
			return true;
		}
		return false;
	}
	
/************************************* OBJECTS ***********************************************/
	
}
