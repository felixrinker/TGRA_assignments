package com.tgra.core;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL11;
import com.tgra.camera.MazeCam;
import com.tgra.model.Maze;
import com.tgra.model.Point3D;
import com.tgra.model.Vector3D;


public class Maze3D_Core implements ApplicationListener
{
	private MazeCam cam;
	private Maze maze;
	
	@Override
	public void create()
	{
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
        Gdx.gl11.glEnable(GL11.GL_NORMALIZE);
        
        Gdx.gl11.glClearColor(0.4f, 0.4f, 0.85f, 1.0f);
		
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		
		this.resize(w, h);

		maze = new Maze();
		cam = new MazeCam(new Point3D(1f, 0.5f, 0.0f), new Point3D(0.0f, 0.5f, 0.5f), new Vector3D(0.0f, 1.0f, 0.0f));
		maze.setCam(cam);
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
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			cam.pitch(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			cam.pitch(90.0f * deltaTime);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			cam.yaw(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			cam.yaw(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			cam.slide(0.0f, 0.0f, -3.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			cam.slide(0.0f, 0.0f, 3.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			cam.slide(-3.0f * deltaTime, 0.0f, 0.0f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			cam.slide(3.0f * deltaTime, 0.0f, 0.0f);
		}
		

		if(Gdx.input.isKeyPressed(Input.Keys.R))
		{
			cam.slide(0.0f, 10.0f * deltaTime, 0.0f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.F))
		{
			cam.slide(0.0f, -10.0f * deltaTime, 0.0f);
		}
		
		this.maze.update();
		
		if(maze.isFinish()) {
			if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				this.maze.setFinish(false);
				this.create();
			}
		}
	}

	
	private void display()
	{
		
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		
		cam.setModelViewMatrix();
		
		// light 0
		float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

		float[] lightPosition = {-10.0f, 5.0f, 5.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);

		
		// light 1
		float[] lightDiffuse1 = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);
		
		float[] lightPosition1 = {cam.getEye().x, cam.getEye().y, cam.getEye().z, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);

		float[] lightDirection1 = {-cam.getN().x, -cam.getN().y, -cam.getN().z, 0.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_SPOT_DIRECTION, lightDirection1, 0);
	
		
		// material
		float[] materialDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

		float[] materialSpecular = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, materialSpecular, 0);
		
		Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 90.0f);
		
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
		Gdx.gl11.glMatrixMode (GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity ();
		
		Gdx.glu.gluPerspective (Gdx.gl11, 90, (float) w / (float) h, 0.02f, 100.0f );
		Gdx.gl11.glMatrixMode (GL11.GL_MODELVIEW); //set the matrix back to model

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean needsGL20() {
	      return true;
	   }
}
