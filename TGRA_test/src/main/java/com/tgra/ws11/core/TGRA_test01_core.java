package com.tgra.ws11.core;

import java.nio.FloatBuffer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

/**
 * 
 * @author Felix Rinker
 * 
 */

public class TGRA_test01_core implements ApplicationListener {

	private static final float speed = 0f;
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
		
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		// set clear color
		Gdx.gl11.glClearColor(0.4f, 0.6f, 1.0f, 1.0f);

		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(8);
		vertexBuffer.put(new float[] { -box_size, -box_size, -box_size, box_size, box_size, -box_size, box_size, box_size });
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
			position_x += -100.0f * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			position_x += 100.0f * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			position_y += -100.0f * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			position_y += 100.0f * deltaTime;
		}
	}

	/**
	 * GRAPHICS CODE
	 */
	private void display() {
		Gdx.gl11.glClearColor(0.4f, 0.6f, 1.0f, 1.0f);
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		Gdx.gl11.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluOrtho2D(Gdx.gl10, 0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());
		
		Gdx.gl11.glColor4f(0.6f, 1.0f, 0.0f, 1.0f);
		Gdx.gl11.glTranslatef(position_x, position_y, 0);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}
}
