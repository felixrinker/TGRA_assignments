package com.tgra.ws11.core;

import java.nio.FloatBuffer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.BufferUtils;

/**
 * 
 * 2. Make a windowed desktop application that displays a box.
 * Make the box move around the screen when you hold down the arrow keys.
 * Dont let it exit the screen.
 * When it hits the edge make it stop.
 * 
 * @author Felix Rinker
 * 
 */

public class TGRA_part2_core implements ApplicationListener {

	private float position_x;
	private float position_y;
	private float box_size;
	private float box_avg;
	
	public void create() {

		this.position_x = 100.0f;
		this.position_y = 100.0f;
		this.box_size	= 50.0f;
		this.box_avg	= box_size;
		
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
