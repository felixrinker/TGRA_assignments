package com.tgra.ws11.labs;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.BufferUtils;
import java.lang.Math;

import java.nio.FloatBuffer;

public class Andriod3DLibGTXTestCore implements ApplicationListener{

	float position_x;
	float position_y;
	float angle;
	float zoom_position_x;
	float zoom_position_y;
	float zoom_width;
	float zoom_height;
	
	@Override
	public void create() {

		position_x = 100.0f;
		position_y = 100.0f;
		angle = 45.0f;
		zoom_position_x = Gdx.graphics.getWidth() / 4;
		zoom_position_y = Gdx.graphics.getHeight() / 4;
		
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);


		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(8);
		vertexBuffer.put(new float[] {-0.5f,-0.5f, -0.5f,0.5f, 0.5f,-0.5f, 0.5f,0.5f});
		vertexBuffer.rewind();
		Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, vertexBuffer);

		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	private void update()
	{
/*******UPDATE CODE*******/
		angle += 1.0f;

		zoom_width = (Gdx.graphics.getWidth() / 8) + (float) (Math.sin(angle * 3.1415f / 180.0f) * (Gdx.graphics.getWidth() / 16));
		zoom_height = (Gdx.graphics.getHeight() / 4) + (float) (Math.sin(angle * 3.1415f / 180.0f) * (Gdx.graphics.getHeight() / 8));
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		//if(Gdx.input.justTouched())
		{
			zoom_position_x = Gdx.input.getX();
			zoom_position_y = Gdx.graphics.getHeight() - Gdx.input.getY();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			position_x -= 5.0f;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			position_x += 5.0f;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			position_y -= 5.0f;
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
			position_y += 5.0f;
	
	}
	
	private void drawScene()
	{
		Gdx.gl11.glColor4f(0.3f, 0.5f, 0.9f, 1.0f);

		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(zoom_position_x, zoom_position_y, 0);
		Gdx.gl11.glScalef(zoom_width, zoom_height, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glPopMatrix();
		
		
		
		Gdx.gl11.glColor4f(0.6f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(200, 200, 0);
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glScalef(150, 150, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glPopMatrix();
		
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glRotatef(angle, 0, 0, 1);
		Gdx.gl11.glTranslatef(150, 0, 0);
		Gdx.gl11.glScalef(100, 100, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glPopMatrix();

		
		Gdx.gl11.glRotatef(angle / 2.0f, 0, 0, 1);
		Gdx.gl11.glTranslatef(0, 300, 0);
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glScalef(100, 100, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glPopMatrix();

		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glRotatef(angle, 0, 0, 1);
		Gdx.gl11.glTranslatef(120, 0, 0);
		Gdx.gl11.glScalef(40, 70, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glPopMatrix();
		

		Gdx.gl11.glPopMatrix();

		

		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glColor4f(0.6f, 1.0f, 0.0f, 1.0f);
		Gdx.gl11.glTranslatef(position_x, position_y, 0);
		Gdx.gl11.glRotatef(angle, 0, 0, 1);
		Gdx.gl11.glScalef(300, 30, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glPopMatrix();
	}
	
	private void display()
	{
/*******GRAPHICS CODE*******/
		Gdx.gl11.glClearColor(0.4f, 0.6f, 1.0f, 1.0f);
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);


		Gdx.gl11.glViewport(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluOrtho2D(Gdx.gl10, 0, Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getHeight());
		
		drawScene();
		

		Gdx.gl11.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluOrtho2D(Gdx.gl10, zoom_position_x - zoom_width / 2,
									 zoom_position_x + zoom_width / 2,
									 zoom_position_y - zoom_height / 2,
									 zoom_position_y + zoom_height / 2);
		
		drawScene();
	}

	@Override
	public void render()
	{
		update();
		
		display();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		display();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


}