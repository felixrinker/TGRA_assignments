package com.tgra.ws11.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class TGRA_lab01_core implements ApplicationListener{

		float position_x;
		float position_y;
		float angle;
		public void create() {
			
			position_x = 100.0f;
			position_y = 100.0f;
			angle = 45.0f;
			
			Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

			Gdx.gl11.glClearColor(0.4f, 0.6f, 1.0f, 1.0f);
			

			FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(8);
			vertexBuffer.put(new float[] {-50,-50, -50,50, 50,-50, 50,50});
			vertexBuffer.rewind();
			Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, vertexBuffer);
		}
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
		public void pause() {
			
		}
		
		private void update() {
			/*******UPDATE CODE*******/
			angle += 1.0f;
			
			//if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			if(Gdx.input.justTouched())
			{
				position_x = Gdx.input.getX();
				position_y = 480 - Gdx.input.getY();
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
		
		private void display()
		{
	/*******GRAPHICS CODE*******/
			Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
			Gdx.gl11.glLoadIdentity();

			Gdx.glu.gluOrtho2D(Gdx.gl10, 0, 640, 0, 480);

			Gdx.gl11.glColor4f(0.6f, 0.0f, 0.0f, 1.0f);


			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(200, 200, 0);
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glScalef(1.5f, 1.5f, 1.0f);
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			Gdx.gl11.glPopMatrix();
			
			
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glRotatef(angle, 0, 0, 1);
			Gdx.gl11.glTranslatef(150, 0, 0);
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			Gdx.gl11.glPopMatrix();

			
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glRotatef(angle / 2.0f, 0, 0, 1);
			Gdx.gl11.glTranslatef(0, 300, 0);
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);

			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glRotatef(angle, 0, 0, 1);
			Gdx.gl11.glTranslatef(150, 0, 0);
			Gdx.gl11.glScalef(0.5f, 0.5f, 1.0f);
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			Gdx.gl11.glPopMatrix();
			

			Gdx.gl11.glPopMatrix();

			

			Gdx.gl11.glColor4f(0.6f, 1.0f, 0.0f, 1.0f);
			Gdx.gl11.glTranslatef(position_x, position_y, 0);
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
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
}
