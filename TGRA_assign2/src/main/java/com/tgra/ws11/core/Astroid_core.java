package com.tgra.ws11.core;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;
import com.tgra.ws11.model.Bullet;
import com.tgra.ws11.model.Meteor;
import com.tgra.ws11.model.SpaceShip;
import com.tgra.ws11.structures.Point2D;
import com.tgra.ws11.structures.TransformationMatrix;

/**
 * 
 * @author Felix Rinker
 * @author Sara van de Moosdijk
 *
 */

public class Astroid_core implements ApplicationListener {

	private int level =1;
	private boolean gameOver;
	private Vector<Point2D> vertexList;
	private SpaceShip spaceShip;
	private ArrayList<Meteor> meteorList;
	private ArrayList<Bullet> bulletList;
	private long lastBulletShot;
	private SpriteBatch batch;
	private BitmapFont font;
	
	public void create() {

		this.vertexList		= new Vector<Point2D>();
		this.bulletList		= new ArrayList<Bullet>();
		
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        
       // this.level = 1;
        this.gameOver = false;
      
        
		// create and load init objects
		loadInitObjects();
		
		// load vertexList to vertexBuffer
		loadVertexList();
		
		Gdx.gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		Gdx.gl10.glClear(0);
		
		// set clear color
		Gdx.gl10.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
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
		
		Gdx.gl10.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
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
		if(gameOver) {
			
			// if enter is pressed
			if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				this.level = 1;
				this.gameOver = false;
				System.exit(0);
			}
			
		}else {
			
		
			if(meteorList.isEmpty()) {
				level=2;
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {	
				spaceShip.changeAngle(2.0f);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				spaceShip.changeAngle(-2.0f);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				spaceShip.changeSpeed(-spaceShip.getSpeedChange());
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				spaceShip.changeSpeed(spaceShip.getSpeedChange());	
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				
				if(this.checkNextShot()) {
					this.bulletList.add(spaceShip.fireBullet());
					loadVertexList();
					this.setLastShotTime(System.currentTimeMillis());
				}
			}
	
			// update ship object
			this.spaceShip.update();
			
			/** 
			 * iterates over the meteors and call the update
			 * Checks if the distance between a meteor and a bullet is larger than
			 * the combined radius and size.
			 * If not, delete both.
			 * 
			 * @TODO add score point to meteor
			 */
			ArrayList<Bullet> deleteBullets = new ArrayList<Bullet>();
			ArrayList<Meteor> deleteMeteors = new ArrayList<Meteor>();
			
			//These two lines of code calculate the width and height of the spaceship based on angle.
			float[] rotatedWH = {spaceShip.getWidth(),spaceShip.getHeight(),0,0};
			rotatedWH = TransformationMatrix.multiplyVectorAndMatrix(TransformationMatrix.rotationMatrix(spaceShip.getAngle()),rotatedWH);
			for( Meteor m : meteorList) {
				
				//FIRST DETECT BULLET COLLISIONS
				for(Bullet b: bulletList) {
					
					if(b.checkLife()) {
					
						//Calculate distances between meteor and bullet
						float dx = Math.abs(m.getPositionX()-b.getPositionX());
						float dy = Math.abs(m.getPositionY()-b.getPositionY());
						
						//Calculate how small the distance can be for non-collision
						float minX = m.getRadius()+(b.getWidth()/2);
						float minY = m.getRadius()+(b.getHeight()/2);
						
						//Check if the distances are smaller than the minimum
						if(dx<minX && dy<minY) {
							//If so, mark meteor and bullet for deletion
							deleteMeteors.add(m);
							deleteBullets.add(b);
							
						}else {
							b.update();
						}
					}else {
						deleteBullets.add(b);
					}
				}
				
				//THEN DETECT SPACESHIP COLLISION
				//calculate the distance between the xcoord and ycoord of the meteors and spaceship
				float dxSS = Math.abs(m.getPositionX()-spaceShip.getPositionX());
				float dySS = Math.abs(m.getPositionY()-spaceShip.getPositionY());
				
				//calculate the minimum distance before collision occurs
				float minXSS = m.getRadius()+(spaceShip.getWidth()/2);
				float minYSS = m.getRadius()+(spaceShip.getHeight()/2);
				
				
				//float minXSS = m.getRadius()+(rotatedWH[0]/2);
				//float minYSS = m.getRadius()+(rotatedWH[1]/2);
				
				//if distance is below minimum, the game is over
				if(dxSS<minXSS && dySS<minYSS) {
					gameOver=true;
				}
				
				//THEN DETECT OTHER METEOR COLLISION
				for(Meteor m2: meteorList) {
					float dxM = Math.abs(m.getPositionX()-m2.getPositionX());
					float dyM = Math.abs(m.getPositionY()-m2.getPositionY());
					float minM = m.getRadius()+m2.getRadius();
					if(dxM<minM && dyM<minM) {
						m.changeAngle(-m.getAngle());
						m2.changeAngle(-m2.getAngle());
					}
				}
				
				m.update();
			}
			
			bulletList.removeAll(deleteBullets);
			meteorList.removeAll(deleteMeteors);
		}
	}

	/**
	 * GRAPHICS CODE
	 */
	private void display() {
		
		Gdx.gl10.glClearColor(0.0f,0.0f,0.2f,1.0f);
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);

		Gdx.gl10.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl10.glMatrixMode(GL10.GL_MODELVIEW);
		Gdx.gl10.glLoadIdentity();
		Gdx.glu.gluOrtho2D(Gdx.gl10, 0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());
		
		if(!gameOver) {
			this.spaceShip.draw();
			
			for( Meteor m : meteorList) {
				m.draw();
			}
			
			for(Bullet b : bulletList) {
				b.draw();
			}
		}else {
			Gdx.gl10.glClearColor(0.0f,0.0f,0.2f,1.0f);
			Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	            batch.begin();
	            font.setColor(0.9f, 0.0f, 1.0f, 1.0f);
	            font.setScale(4.0f);
	            font.draw(batch, "Game Over", 330, 500);
	            font.setScale(2.0f);
	            font.setColor(0.9f, 1.0f, 1.0f, 1.0f);
	            font.draw(batch, "Press Enter to quit", 350, 300);
	            batch.end();
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
		
		if(level==1){
		loadLevelOneInitObjects();	}
		else if(level==2) {
			loadLevelTwoInitObjects();
		}
	}
	
	/**
	 * Creates and load objects for the (first) level
	 */
	private void loadLevelOneInitObjects() {
		
		meteorList = new ArrayList<Meteor>();
		
		this.meteorList.add(new Meteor(20.0f, -1.0f, 200,100, this.vertexList));
		this.meteorList.add(new Meteor(15.0f, -1.0f, 800,200, this.vertexList));
		this.meteorList.add(new Meteor(10.0f, -1.0f, 400,700, this.vertexList));
		this.meteorList.add(new Meteor(10.0f, -140.0f, 600,500, this.vertexList));
		this.meteorList.add(new Meteor(05.0f, -180.0f, 150,200, this.vertexList));
		
	}
	
	private void loadLevelTwoInitObjects() {
		meteorList = new ArrayList<Meteor>();
		this.meteorList.add(new Meteor(10.0f, 170.0f, 400,700, this.vertexList));
		this.meteorList.add(new Meteor(10.0f, 50.0f, 600,500, this.vertexList));
		this.meteorList.add(new Meteor(05.0f, 30.0f, 150,200, this.vertexList));
		this.meteorList.add(new Meteor(9.0f, -5.0f, 50,100, this.vertexList));
		this.meteorList.add(new Meteor(7.0f, -90.0f, 400,100, this.vertexList));
		this.meteorList.add(new Meteor(12.0f, -150.0f, 350,200, this.vertexList));
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
