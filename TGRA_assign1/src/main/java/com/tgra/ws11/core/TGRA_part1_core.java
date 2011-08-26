package com.tgra.ws11.core;

import java.nio.FloatBuffer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.BufferUtils;

/**
 * 
 * 1. Make a windowed desktop application that displays a box. Make the box move
 * at an even speed diagonally. If the box hits the edges of the window, make it
 * change direction as if it's bouncing. If it hits the top of the screen, only
 * change the up-down direction and so on.
 * 
 * @author Felix Rinker
 * 
 */

public class TGRA_part1_core implements ApplicationListener {

	private static final float speed = 3.0f;
	private float position_x;
	private float position_y;
	private float angle;
	private Action act;
	private float box_size;
	private static enum Action { UP, DOWN, LEFT, RIGHT, UP_DIAG_RIGHT, DOWN_DIAG_LEFT, UP_DIAG_LEFT, DOWN_DIAG_RIGHT }

	public void create() {

		this.position_x = 100.0f;
		this.position_y = 100.0f;
		this.angle		= 45.0f;
		this.box_size	= 50.0f;
		this.act		= Action.UP_DIAG_RIGHT;
		
		Gdx.gl11.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// set clear color
		Gdx.gl11.glClearColor(0.4f, 0.6f, 1.0f, 1.0f);

		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(8);
		vertexBuffer.put(new float[] { -box_size, -box_size, -box_size, box_size, box_size, -box_size, box_size, box_size });
		vertexBuffer.rewind();
		Gdx.gl11.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
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

		angle += 1.0f;

		// if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		if (Gdx.input.justTouched()) {
			position_x = Gdx.input.getX();
			position_y = Gdx.graphics.getHeight() - Gdx.input.getY();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			position_x -= 5.0f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			position_x += 5.0f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			position_y -= 5.0f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			position_y += 5.0f;
		}
	}

	/**
	 * GRAPHICS CODE
	 */
	private void display() {

		Gdx.gl11.glClear(GL10.GL_COLOR_BUFFER_BIT);

		Gdx.gl11.glMatrixMode(GL10.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();

		Gdx.glu.gluOrtho2D(Gdx.gl10, 0, Gdx.graphics.getWidth(), 0,
				Gdx.graphics.getHeight());

		Gdx.gl11.glColor4f(0.6f, 0.0f, 0.0f, 1.0f);


		float box_avg = box_size/2;
		
		if(position_x >= Gdx.graphics.getWidth()) {
			
			//case (1,x)
			act = Action.LEFT;
			
			if( position_y >= Gdx.graphics.getHeight() ) {
				//case (1,1)
				act = Action.DOWN_DIAG_LEFT;
			}
			if( position_y <= 0.0f ) {
				//case (1,0)
				act = Action.UP_DIAG_LEFT;
			}
		}
		
		if(position_x <= 0.0f) {
			
			//case (0,x)
			act = Action.RIGHT;
			
			if( position_y >= Gdx.graphics.getHeight() ) {
				//case (0,1)
				act = Action.DOWN_DIAG_RIGHT;
			}
			if( position_y <= 0.0f ) {
				//case (0,0)
				act = Action.UP_DIAG_LEFT;
			}
		}
		
		switch(act) {
		
			case UP:
				this.position_y += speed;
				break;
			
			case DOWN:
				this.position_y -= speed;
				break;
				
			case LEFT:
				this.position_x -= speed;
				break;
			
			case RIGHT:
				this.position_x += speed;
				break;
				
			case UP_DIAG_RIGHT:
				this.position_x += speed;
				this.position_y += speed;
				break;
				
			case DOWN_DIAG_LEFT:
				this.position_x -= speed;
				this.position_y -= speed;
				break;
				
			case UP_DIAG_LEFT:
				this.position_x -= speed;
				this.position_y += speed;
				break;
				
			case DOWN_DIAG_RIGHT:
				this.position_x += speed;
				this.position_y -= speed;
				break;
		}

		Gdx.gl11.glColor4f(0.6f, 1.0f, 0.0f, 1.0f);
		Gdx.gl11.glTranslatef(position_x, position_y, 0);
		Gdx.gl11.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}
