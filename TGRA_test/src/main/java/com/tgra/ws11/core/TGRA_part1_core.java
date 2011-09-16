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
	private float box_size;
	private float box_avg;
	private boolean direction_x;
	private boolean direction_y;

	public void create() {

		this.position_x 	= 100.0f;
		this.position_y 	= 100.0f;
		this.box_size		= 50.0f;
		this.box_avg		= box_size;
		this.direction_x	= true;
		this.direction_y	= true;
		
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
		if (Gdx.input.justTouched()) {
			
			position_x = TGRA_common.update_x(Gdx.input.getX()+box_avg, 0.0f, box_avg, true);
			position_y = TGRA_common.update_y(Gdx.graphics.getHeight() - Gdx.input.getY()+box_avg, 0.0f, box_avg, true);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			position_x = TGRA_common.update_x(position_x, 5.0f, box_avg, false);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			position_x = TGRA_common.update_x(position_x, 5.0f, box_avg, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			position_y = TGRA_common.update_y(position_y, 5.0f, box_avg, false);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			position_y = TGRA_common.update_y(position_y, 5.0f, box_avg, true);
		}
		
		
		float box_left	= position_x-box_avg;
		float box_right = position_x+box_avg;
		float box_top	= position_y+box_avg;
		float box_down	= position_y-box_avg;
		
		if(box_right == Gdx.graphics.getWidth()) {
			
			if(  box_top >= Gdx.graphics.getHeight() ) {
				//case (1,1)
				this.direction_x = !direction_x;
				this.direction_y = !direction_y;
			}
			if( box_down <= 0.0f ) {
				//case (1,0)
				this.direction_x = !direction_x;
				this.direction_y = !direction_y;
			}else {
				//case (1,x)
				//inverse the x direction
				this.direction_x = !direction_x;
			}
		}
		
		if(box_left == 0.0f) {
			if( box_top >= Gdx.graphics.getHeight() ) {
				//case (0,1)
				this.direction_x = !direction_x;
				this.direction_y = !direction_y;
			}
			if( box_down <= 0.0f ) {
				//case (0,0)
				this.direction_x = !direction_x;
				this.direction_y = !direction_y;
			}else {
				//case (0,x)
				//inverse the x direction
				this.direction_x = !direction_x;
			}
		}

		if(box_top == Gdx.graphics.getHeight()) {
			//case (x,1)
			//inverse the y direction
			this.direction_y = !direction_y;
		}
		
		if(box_down == 0.0f) {
			//case (x,0)
			//inverse the y direction
			this.direction_y = !direction_y;
		}
		
		
		// Update coordinates
		this.position_y = TGRA_common.update_y(this.position_y, speed, box_avg, direction_y);
		this.position_x = TGRA_common.update_x(this.position_x, speed, box_avg, direction_x);
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
		
		Gdx.gl11.glColor4f(0.6f, 1.0f, 0.0f, 1.0f);
		Gdx.gl11.glTranslatef(position_x, position_y, 0);
		Gdx.gl11.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}
