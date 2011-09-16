package com.tgra;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.BufferUtils;

public class Transformations_2D_Core implements ApplicationListener {

	float angle;
	@Override
	public void create() {

		angle = 0.0f;
		
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(28);
		vertexBuffer.put(new float[] {0.0f, 0.0f, 1.0f, 0.0f,//Vector - 0, 2
									 0.7f, 0.2f, 1.0f, 0.0f,//Vector_arrow - 2, 2
									 0.7f, -0.2f, 1.0f, 0.0f,//Vector_arrow - 4, 2
									 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 1.0f,//Box - 6, 4
									 -3.0f, 0.0f, 10.0f, 0.0f,//Axis - 10, 2
									 0.0f, 0.1f, 0.0f, -0.1f});//Axis_mark - 12, 2
		vertexBuffer.rewind();
		Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, vertexBuffer);

		Gdx.gl11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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
		angle += 180.0f * Gdx.graphics.getDeltaTime();
	}
	
	private void drawAxis()
	{
		Gdx.gl11.glDrawArrays(GL11.GL_LINES, 10, 2);
		for(float f = -3.0f; f < 10.0f; f += 1.0f)
		{
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(f, 0, 0);
			Gdx.gl11.glDrawArrays(GL11.GL_LINES, 12, 2);
			Gdx.gl11.glPopMatrix();
		}
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(9.0f, 0, 0);
		Gdx.gl11.glDrawArrays(GL11.GL_LINES, 2, 4);
		Gdx.gl11.glPopMatrix();
	}
	
	private void drawVector()
	{
		Gdx.gl11.glDrawArrays(GL11.GL_LINES, 0, 6);
	}
	
	private void drawBox()
	{
		Gdx.gl11.glDrawArrays(GL11.GL_LINE_LOOP, 6, 4);
	}
	
	private void drawCoordinateSystem()
	{
		Gdx.gl11.glPushMatrix();
		drawAxis();
		Gdx.gl11.glRotatef(90.0f, 0, 0, 1);
		drawAxis();
		Gdx.gl11.glPopMatrix();
	}
	
	private void drawCoordinateFrame()
	{
		Gdx.gl11.glPushMatrix();
		drawVector();
		Gdx.gl11.glRotatef(90.0f, 0, 0, 1);
		drawVector();
		Gdx.gl11.glPopMatrix();
	}
	
	private void display()
	{
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluOrtho2D(Gdx.gl10, -5.0f, 15.0f, -5.0f, 15.0f);

		Gdx.gl11.glColor4f(0.6f, 0.6f, 0.6f, 1.0f);

		drawCoordinateSystem();
		drawBox();
		
		
		

		float[] MR = TransformationMatrix.getIdentityMatrix();

		MR[0] = (float) Math.cos(angle * 3.1415f/180.0f);	MR[4] = (float) -Math.sin(angle * 3.1415f/180.0f);	MR[12]	= 0;
		MR[1] = (float) Math.sin(angle * 3.1415f/180.0f);	MR[5] = (float) Math.cos(angle * 3.1415f/180.0f);	MR[13]	= 0;
		MR[3] = 0;				MR[7] = 0;				MR[15]	= 1;

		float[] MT = TransformationMatrix.getIdentityMatrix();

		MT[0] = 1;				MT[4] = 0;				MT[12]	= 2;
		MT[1] = 0;				MT[5] = 1;				MT[13]	= 3;
		MT[3] = 0;				MT[7] = 0;				MT[15]	= 1;

		float[] MS = TransformationMatrix.getIdentityMatrix();

		MS[0] = 2.0f;			MS[4] = 0;				MS[12]	= 0;
		MS[1] = 0;				MS[5] = 2.0f;			MS[13]	= 0;
		MS[3] = 0;				MS[7] = 0;				MS[15]	= 1;


		float[] M = TransformationMatrix.getIdentityMatrix();

		M[0] = 0.7f;				M[4] = 2.3f;				M[12]	= 1.7f;
		M[1] = 1.4f;				M[5] = 0.3f;				M[13]	= 1.2f;
		M[3] = 0;				M[7] = 0;				M[15]	= 1;

		float[] Mshear = TransformationMatrix.getIdentityMatrix();

		Mshear[0] = 1;				Mshear[4] = 0.5f;			Mshear[12]	= 0;
		Mshear[1] = 0;				Mshear[5] = 1;				Mshear[13]	= 0;
		Mshear[3] = 0;				Mshear[7] = 0;				Mshear[15]	= 1;


		
		
		
		

		Gdx.gl11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glPushMatrix();

		//Gdx.gl11.glTranslatef(4.0f, 6.0f, 0.0f);
		//Gdx.gl11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		Gdx.gl11.glMultMatrixf(TransformationMatrix.multiply(MT, TransformationMatrix.multiply(MR, Mshear)), 0);

		drawCoordinateFrame();
		drawBox();
		
		Gdx.gl11.glPopMatrix();
		
	}

	@Override
	public void render() {
		update();
		display();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
