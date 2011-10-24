package com.tgra.core;
import java.nio.FloatBuffer;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tgra.camera.Camera;
import com.tgra.model.Cube;
import com.tgra.model.Maze;
import com.tgra.model.Point3D;
import com.tgra.model.Vector3D;


public class First3D_Core implements ApplicationListener
{
	Camera cam;
	Cube cube;
	private Maze maze;
	private int zoom;
	private float tx;
	private float ty;
	private float rotx;
	private float roty;
	private float light_angle;
	
	@Override
	public void create()
	{
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		
		this.resize(w, h);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		cam = new Camera(new Point3D(0.5f, 0.5f, 0.5f), new Point3D(0.5f, 0.5f, -0.5f), new Vector3D(0.0f, 1.0f, 0.0f));
		
		
		cube = new Cube();
		
		maze = new Maze(cam);
		
		light_angle = 0.0f;
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
		
		this.maze.update();
	}

	
	private void display()
	{
		
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		
		cam.setModelViewMatrix();
		
		float[] globalAmbience = {0.2f, 0.2f, 0.2f, 1.0f};
		Gdx.gl11.glLightModelfv(GL11.GL_LIGHT_MODEL_AMBIENT, globalAmbience, 0);
		
		
		float[] lightDiffuse =  {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

		float[] lightSpecular =  {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_SPECULAR, lightSpecular, 0);

		float[] lightPosition = {5.0f, 10.0f, 15.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);

		
		
		float[] lightDiffuse1 = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);

		float[] lightSpecular1 = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_SPECULAR, lightSpecular1, 0);
		
		float[] lightPosition1 = {-5.0f, -10.0f, -15.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);

		
		
		float[] materialDiffuse = {0.2f, 0.2f, 0.1f, 0.5f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

		float[] materialSpecular = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, materialSpecular, 0);

		float[] materialAmbient = {0.2f, 0.6f, 0.1f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_AMBIENT, materialAmbient, 0);
		
		Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 90.0f);

		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(-1.0f, 5.0f, -1.0f);
		cube.draw();
		Gdx.gl11.glPopMatrix();
		
		this.maze.draw();
		
	}

	@Override
	public void render()
	{
		update();
		display();
	}

	@Override
	public void resize(int arg0, int arg1) {

		int w = arg0;
		int h = arg1;
		
		Gdx.gl11.glViewport(0, 0, w, h); 
		Gdx.gl11.glMatrixMode (Gdx.gl11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity ();
		
		Gdx.glu.gluPerspective (Gdx.gl11, 45, (float) w / (float) h, 0.2f, 1000.0f );
		Gdx.gl11.glMatrixMode (Gdx.gl11.GL_MODELVIEW); //set the matrix back to model
		
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
