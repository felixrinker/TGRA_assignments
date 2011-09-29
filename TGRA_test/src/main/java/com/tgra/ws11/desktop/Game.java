package com.tgra.ws11.desktop;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.BufferUtils;
import com.tgra.ws11.core.TransformationMatrix;



public class Game implements ApplicationListener{
	ObjectReference spacecraft;
	float x_position = 100;
	float y_position = 100;
	float[] matrix = TransformationMatrix.getIdentityMatrix();
	float[] directionSC = {1,1,0};
	float[] travelSC = {1,1,0};
	float angle=0;
	float speed=0;
	float bullet_width = 2;
	float bullet_height = 6;
	boolean fire;
	HashMap<ObjectReference,Float> bullets = new HashMap<ObjectReference,Float>(100);


	public void create() {
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		fire = false;
	}


	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	public void render() {
		update();
		display();
	}
	
	private void update() {
		float[] MT = TransformationMatrix.getIdentityMatrix();
		MT[0] = 1;				MT[3] = 0;		MT[6]=speed;
		MT[1] = 0;				MT[4] = 1;		MT[7]=speed;
		MT[2] = 0;				MT[5] = 0;		MT[8]=1;
		
		float[] MS = TransformationMatrix.getIdentityMatrix();
		MT[0] = speed;			MT[3] = 0;		MT[6]=0;
		MT[1] = 0;				MT[4] = speed;	MT[7]=0;
		MT[2] = 0;				MT[5] = 0;		MT[8]=1;
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			//for(Float number: travelSC) {
				//System.out.println(number);
			//}
			speed += 2.0f*deltaTime;
			travelSC = TransformationMatrix.multiplyVector(MS, travelSC);
			//for(Float number: travelSC) {
				//System.out.println(number);
			//}
		}
		else {
			speed -= 0.2f*deltaTime;
			travelSC = TransformationMatrix.multiplyVector(MS, travelSC);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			//turn space craft
			angle -= 180.0f*deltaTime;
			//directionSC = TransformationMatrix.multiplyVector(MR, directionSC);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			//turn space craft
			angle += 180.0f*deltaTime;
			//directionSC = TransformationMatrix.multiplyVector(MR, directionSC);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			//fire
			fire = true;
		}

	}
	
	private void display() {
		Gdx.gl11.glClearColor(0.0f,0.0f,0.2f,1.0f);
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		Vector<Point2D> vertexList = new Vector<Point2D>();
		
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		
		Gdx.glu.gluOrtho2D(Gdx.gl10,0,640,0,480);
		
		spacecraft = createSpacecraft(20,40,vertexList);
		/*
		if(fire) {
			bullets.put(createBullet(bullet_width,bullet_height,vertexList),1.0f);
		}
		*/
		
		int floatBufferSize = vertexList.size()*2;
		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(floatBufferSize);
		float[] array = new float[floatBufferSize];
		for(int i=0; i<vertexList.size(); i++) {
			array[i*2] = vertexList.get(i).x;
			array[i*2+1] = vertexList.get(i).y;
		}
		vertexBuffer.put(array);
		vertexBuffer.rewind();
		Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT,0,vertexBuffer);
		
		drawScene();
	}

	private void drawScene() {
		Gdx.gl11.glColor4f(0.9f,0.9f,0.9f,0.0f);
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(x_position+travelSC[0], y_position+travelSC[1], 0);
		drawObject(spacecraft);
		Gdx.gl11.glPopMatrix();
		
	}
	

	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	private ObjectReference createSpacecraft(float width, float height, Vector<Point2D> vertexList)
	{
		ObjectReference or = new ObjectReference(vertexList.size(), 6, GL11.GL_TRIANGLE_FAN);
		
		vertexList.add(new Point2D(-width/4.0f, height/2.0f));
		vertexList.add(new Point2D(width/4.0f, height/2.0f));
		vertexList.add(new Point2D(width/2.0f, height/4.0f));
		vertexList.add(new Point2D(width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, height/4.0f));
		 
		return or;
	}
	
	private ObjectReference createBullet(float width, float height, Vector<Point2D> vertexList) {
		ObjectReference or = new ObjectReference(vertexList.size(),4,GL11.GL_TRIANGLE_STRIP);
		vertexList.add(new Point2D(-width/2.0f, height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(width/2.0f, height/2.0f));
		vertexList.add(new Point2D(width/2.0f, -height/2.0f));
		return or;
	}
	
	private ObjectReference createMeteor(float radius, int slices, Vector<Point2D> vertexList) {
		ObjectReference or = new ObjectReference(vertexList.size(),slices,GL11.GL_TRIANGLE_FAN);
		for(float f=0; f<2*Math.PI; f+=(float)2*Math.PI/(float)slices) {
			vertexList.add(new Point2D((float)Math.cos(f)*radius,(float)Math.sin(f)*radius));
		}
		return or;
	}
	
	private void drawObject(ObjectReference or)
	{
		Gdx.gl11.glDrawArrays(or.openGLPrimitiveType, or.firstIndex, or.vertexCount);
	}
	
	public class ObjectReference {
		public int firstIndex;
		public int vertexCount;
		public int openGLPrimitiveType;
		
		public ObjectReference(int fi,int vc,int pt) {
			firstIndex = fi;
			vertexCount = vc;
			openGLPrimitiveType = pt;
		}
	}
	
	public class Point2D {
		public float x;
		public float y;
		public Point2D(float xx, float yy) {
			x=xx;
			y=yy;
		}
	}
}
