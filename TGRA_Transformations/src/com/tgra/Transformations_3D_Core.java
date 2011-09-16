package com.tgra;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.BufferUtils;

public class Transformations_3D_Core implements ApplicationListener {

	float angle;

	float camRotate;
	float camHeight;
	float camDistance;
	
	@Override
	public void create() {

		angle = 0.0f;

		camRotate = 1.5708f;
		camHeight = 10.0f;
		camDistance = 10.0f;
		
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(78);
		vertexBuffer.put(new float[] {0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,//Vector - 0, 2
									 0.7f, 0.2f, 0.0f, 1.0f, 0.0f, 0.0f,//Vector_arrow - 2, 2
									 0.7f, -0.2f, 0.0f, 1.0f, 0.0f, 0.0f,//Vector_arrow - 4, 2
									 -3.0f, 0.0f, 0.0f, 10.0f, 0.0f, 0.0f,//Axis - 6, 2
									 0.0f, 0.1f, 0.0f, 0.0f, -0.1f, 0.0f,//Axis_mark - 8, 2
									 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 1.0f, 2.0f, 2.0f, 1.0f, 2.0f, 1.0f, 1.0f,//Box - 10, 4
									 1.0f, 1.0f, 2.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 1.0f, 2.0f,//Box - 14, 4
									 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f,
									 1.0f, 2.0f, 1.0f, 1.0f, 2.0f, 2.0f,
									 2.0f, 2.0f, 1.0f, 2.0f, 2.0f, 2.0f,
									 2.0f, 1.0f, 1.0f, 2.0f, 1.0f, 2.0f});//Box - 18, 8 (LINES)
		vertexBuffer.rewind();
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);

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
		float delta = Gdx.graphics.getDeltaTime();
		angle += 180.0f * delta;
		

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			camRotate -= 3.1415 * delta;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			camRotate += 3.1415 * delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			camHeight += 30 * delta;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			camHeight -= 30 * delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			camDistance += 30 * delta;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			camDistance -= 30 * delta;
		}

	}
	
	private void drawAxis()
	{
		Gdx.gl11.glDrawArrays(GL11.GL_LINES, 6, 2);
		for(float f = -3.0f; f < 10.0f; f += 1.0f)
		{
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(f, 0, 0);
			Gdx.gl11.glDrawArrays(GL11.GL_LINES, 8, 2);
			Gdx.gl11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			Gdx.gl11.glDrawArrays(GL11.GL_LINES, 8, 2);
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
		Gdx.gl11.glDrawArrays(GL11.GL_LINE_LOOP, 10, 4);
		Gdx.gl11.glDrawArrays(GL11.GL_LINE_LOOP, 14, 4);
		Gdx.gl11.glDrawArrays(GL11.GL_LINES, 18, 8);
	}
	
	private void drawCoordinateSystem()
	{
		Gdx.gl11.glPushMatrix();
		drawAxis();
		Gdx.gl11.glRotatef(90.0f, 0, 0, 1);
		drawAxis();
		Gdx.gl11.glRotatef(-90.0f, 0, 1, 0);
		drawAxis();
		Gdx.gl11.glPopMatrix();
	}
	
	private void drawCoordinateFrame()
	{
		Gdx.gl11.glPushMatrix();
		drawVector();
		Gdx.gl11.glRotatef(90.0f, 0, 0, 1);
		drawVector();
		Gdx.gl11.glRotatef(-90.0f, 0, 1, 0);
		drawVector();
		Gdx.gl11.glPopMatrix();
	}
	
	private void display()
	{
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl10, 60.0f, 1.0f, 1.0f, 100.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluLookAt(Gdx.gl10, camDistance * (float)Math.cos(camRotate), camHeight, camDistance * (float)Math.sin(camRotate), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

		Gdx.gl11.glColor4f(0.6f, 0.6f, 0.6f, 1.0f);

		drawCoordinateSystem();
		drawBox();
		
		
		
		float[] MRX = TransformationMatrix.getIdentityMatrix();

		MRX[0] = 1;		MRX[4] = 0;											MRX[8]	= 0;											MRX[12]	= 0;
		MRX[1] = 0;		MRX[5] = (float)Math.cos(angle * 3.1415f/180.0f);	MRX[9]	= (float) -Math.sin(angle * 3.1415f/180.0f);	MRX[13]	= 0;
		MRX[2] = 0;		MRX[6] = (float)Math.sin(angle * 3.1415f/180.0f);	MRX[10]	= (float) Math.cos(angle * 3.1415f/180.0f);		MRX[14]	= 0;
		MRX[3] = 0;		MRX[7] = 0;											MRX[11]	= 0;											MRX[15]	= 1;

		float[] MRY = TransformationMatrix.getIdentityMatrix();

		MRY[0] = (float) Math.cos(angle * 3.1415f/180.0f);	MRY[4] = 0;		MRY[8]	= (float) Math.sin(angle * 3.1415f/180.0f);	MRY[12]	= 0;
		MRY[1] = 0;											MRY[5] = 1;		MRY[9]	= 0;										MRY[13]	= 0;
		MRY[2] = (float) -Math.sin(angle * 3.1415f/180.0f);	MRY[6] = 0;		MRY[10]	= (float) Math.cos(angle * 3.1415f/180.0f);	MRY[14]	= 0;
		MRY[3] = 0;											MRY[7] = 0;		MRY[11]	= 0;										MRY[15]	= 1;

		float[] MRZ = TransformationMatrix.getIdentityMatrix();

		MRZ[0] = (float) Math.cos(angle * 3.1415f/180.0f);	MRZ[4] = (float) -Math.sin(angle * 3.1415f/180.0f);	MRZ[8]	= 0;	MRZ[12]	= 0;
		MRZ[1] = (float) Math.sin(angle * 3.1415f/180.0f);	MRZ[5] = (float) Math.cos(angle * 3.1415f/180.0f);	MRZ[9]	= 0;	MRZ[13]	= 0;
		MRZ[2] = 0;											MRZ[6] = 0;											MRZ[10]	= 1;	MRZ[14]	= 0;
		MRZ[3] = 0;											MRZ[7] = 0;											MRZ[11]	= 0;	MRZ[15]	= 1;

		float[] MT = TransformationMatrix.getIdentityMatrix();

		MT[0] = 1;	MT[4] = 0;	MT[8]	= 0;	MT[12]	= 3;
		MT[1] = 0;	MT[5] = 1;	MT[9]	= 0;	MT[13]	= 3;
		MT[2] = 0;	MT[6] = 0;	MT[10]	= 1;	MT[14]	= 3;
		MT[3] = 0;	MT[7] = 0;	MT[11]	= 0;	MT[15]	= 1;

		float[] MScale = TransformationMatrix.getIdentityMatrix();

		MScale[0] = 0.7f;		MScale[4] = 0;		MScale[8]	= 0;	MScale[12]	= 0;
		MScale[1] = 0;			MScale[5] = 0.5f;	MScale[9]	= 0;	MScale[13]	= 0;
		MScale[2] = 0;			MScale[6] = 0;		MScale[10]	= 1;	MScale[14]	= 0;
		MScale[3] = 0;			MScale[7] = 0;		MScale[11]	= 0;	MScale[15]	= 1;

		float[] MShear = TransformationMatrix.getIdentityMatrix();

		MShear[0] = 1;			MShear[4] = 0;		MShear[8]	= 0.8f;	MShear[12]	= 0;
		MShear[1] = 0.3f;		MShear[5] = 1;		MShear[9]	= 0;	MShear[13]	= 0;
		MShear[2] = 0;			MShear[6] = 0.5f;	MShear[10]	= 1;	MShear[14]	= 0;
		MShear[3] = 0;			MShear[7] = 0;		MShear[11]	= 0;	MShear[15]	= 1;


		float[] M = TransformationMatrix.getIdentityMatrix();

		M[0] = 1.4f;			M[4] = 0.3f;			M[8]	= -0.24f;	M[12]	= 1.0f;
		M[1] = 0.7f;			M[5] = 2.3f;			M[9]	= -0.4f;	M[13]	= 2.4f;
		M[2] = 0.3f;			M[6] = 0.8f;			M[10]	= 1.2f;		M[14]	= 0.7f;
		M[3] = 0;				M[7] = 0;				M[11]	= 0;		M[15]	= 1;


		
		
		
		

		Gdx.gl11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glPushMatrix();

		//Gdx.gl11.glTranslatef(4.0f, 6.0f, 0.0f);
		//Gdx.gl11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		Gdx.gl11.glMultMatrixf(TransformationMatrix.multiply(MT, TransformationMatrix.multiply(MRX, MShear)), 0);

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
