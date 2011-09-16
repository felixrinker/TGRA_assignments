package com.tgra.ws11.core;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.BufferUtils;

/**
 * 
 * 3. Make a windowed desktop application that displays a blank screen.
 * When you click with the mouse on the screen display a box centered at the point you clicked on.
 * Each time you click the screen add a new box.
 * Every frame you have to display all the boxes, so you have to keep track of them the whole time.
 * Use if(Gdx.input.justTouched()) so you only create one box for each click.
 * 
 * @author Felix Rinker
 * 
 */

public class TGRA_part3_core implements ApplicationListener {

	private float position_x;
	private float position_y;
	private float box_size;
	private float box_avg;
	private List<Position> boxes;
	
	public void create() {

		this.position_x = 100.0f;
		this.position_y = 100.0f;
		this.box_size	= 50.0f;
		this.box_avg	= box_size;
		this.boxes		= new ArrayList<Position>();	
		
		Gdx.gl11.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// set clear color
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // black

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
			position_x = TGRA_common.update_x(Gdx.input.getX(), 0.0f, box_avg, true);
			position_y = TGRA_common.update_y(Gdx.graphics.getHeight() - Gdx.input.getY(), 0.0f, box_avg, true);
		}
		
		// add position to boxes list.
		this.boxes.add(new Position(position_x, position_y));
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

		// iterate over the list of known box positions
		for(Position pos : boxes) {
			
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glColor4f(0.6f, 1.0f, 0.0f, 1.0f);
			Gdx.gl11.glTranslatef(pos.getX(), pos.getY(), 0);
			Gdx.gl11.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			Gdx.gl11.glPopMatrix();
		}
	}
}
