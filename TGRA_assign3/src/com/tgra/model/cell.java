package com.tgra.model;

import java.io.InputStream;
import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

/**
 * 
 * @author Felix Rinker
 *
 */
public class Cell {

	private boolean westWall;
	private boolean southWall;
	private Cube cube;
	private Texture floorTexture;
	private FloatBuffer texCoordBuffer;
	private Texture wallTexture;
	
	public Cell( boolean westWall, boolean southWall) {
		super();
		
		this.westWall	= westWall;
		this.southWall	= southWall;
		
		cube = new Cube();
		
		texCoordBuffer = BufferUtils.newFloatBuffer(48);
		texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
		texCoordBuffer.rewind();
		
		//floorTexture = new Texture(Gdx.files.internal("assets/textures/floor.jpg"));
		//wallTexture = new Texture(Gdx.files.internal("assets/textures/wall.jpg"));
	}

	public Cell() {
		
		this(true, true);
	};
	
	
	private void drawWallTexture() {
		
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		
		Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		wallTexture.bind();

		Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);

		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);

		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}

	private void drawFloorTexture() {
		
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		
		Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		floorTexture.bind();

		Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);

		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);

		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
	
	public void draw() {
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef( 0.5f, 0.0f, 0.5f );
		Gdx.gl11.glScalef(1.0f, 0.15f, 1.0f);
		
		this.drawFloorTexture();
		
		cube.draw();
		
		Gdx.gl11.glPopMatrix();
		
		
		if(this.isSouthWall()) {
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef( 0.5f, 0.5f, 0.0f );
			Gdx.gl11.glScalef(1.0f, 1.0f, 0.15f);
			
			this.drawWallTexture();
			
			cube.draw();
			
			Gdx.gl11.glPopMatrix();
		}
		
		if(this.isWestWall()) {
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef( 0.0f, 0.5f, 0.5f );
			Gdx.gl11.glScalef(0.15f, 1.0f, 1.0f);
			
			this.drawWallTexture();
			cube.draw();
			
			Gdx.gl11.glPopMatrix();
		}
		
	}
	
	
	///////////////////////// GETTER / SETTER ////////////////////////////
	
	/**
	 * 
	 * @param westWall
	 */
	public void setWestWall(boolean westWall) {
		this.westWall = westWall;
	}
	
	/**
	 * 
	 * @param southWall
	 */
	public void setSouthWall(boolean southWall) {
		this.southWall = southWall;
	}	
	
	/**
	 * 
	 * @return
	 */
	public boolean isSouthWall() {
		return southWall;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isWestWall() {
		return westWall;
	}


	@Override
	public String toString() {
		return "Cell [westWall=" + westWall + ", southWall=" + southWall + "]";
	}
	
	
}
