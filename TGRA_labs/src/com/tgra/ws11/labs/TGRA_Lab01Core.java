package com.tgra.ws11.labs;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.BufferUtils;


import java.nio.FloatBuffer;
import java.util.Vector;


public class TGRA_Lab01Core implements ApplicationListener{

	
	float position_x;
	float position_y;
	float angle;
	
	
	ObjectReference littleBox;
	ObjectReference bigBox;
	ObjectReference littleCircle;
	ObjectReference arbObject;
	 
	private class Point2D
	{
		public float x;
		public float y;
		public Point2D(float xx, float yy)
		{
			x = xx;
			y = yy;
		}
	}
	public class ObjectReference
	{
		public int firstIndex;
		public int vertexCount;
		public int openGLPrimitiveType;
		public ObjectReference(int fi, int vc, int pt)
		{
			firstIndex = fi;
			vertexCount = vc;
			openGLPrimitiveType = pt;
		}
	}
	 
	@Override
	public void create() {
		
		
		position_x = 100.0f;
		position_y = 100.0f;
		angle = 45.0f;
		 
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		 
		Vector<Point2D> vertexList = new Vector<Point2D>();
		
		
		littleBox = createBox(50, 50, vertexList);
		bigBox = createBox(100, 100, vertexList);
		littleCircle = createCircle(100, 39, vertexList);
		arbObject = createArbitraryObject(vertexList);
		
		int floatBufferSize = vertexList.size() * 2;
		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(floatBufferSize);
		float[] array = new float[floatBufferSize];
		for(int i = 0; i < vertexList.size(); i++)
		{
			array[i*2] = vertexList.get(i).x;
			array[i*2+1] = vertexList.get(i).y;
		}
		vertexBuffer.put(array);  //new float[] {-0.5f,-0.5f, -0.5f,0.5f, 0.5f,-0.5f, 0.5f,0.5f}
		vertexBuffer.rewind();
		Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, vertexBuffer);

	}
	 
	private ObjectReference createBox(float width, float height, Vector<Point2D> vertexList)
	{
		ObjectReference or = new ObjectReference(vertexList.size(), 4, GL11.GL_TRIANGLE_STRIP);
		
		
		vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(-width/2.0f, height/2.0f));
		vertexList.add(new Point2D(width/2.0f, -height/2.0f));
		vertexList.add(new Point2D(width/2.0f, height/2.0f));
		 
		return or;
	}
	
	private ObjectReference createCircle(float radius, int slices, Vector<Point2D> vertexList)
	{
		ObjectReference or = new ObjectReference(vertexList.size(), slices, GL11.GL_TRIANGLE_FAN);
		
		
		for(float f = 0; f < 2*Math.PI; f += (float)2*Math.PI / (float)slices)
		{
			vertexList.add(new Point2D((float)Math.cos(f) * radius, (float)Math.sin(f) * radius));
		}
		
		return or;
	}
	
	private ObjectReference createArbitraryObject(Vector<Point2D> vertexList)
	{
		ObjectReference or = new ObjectReference(vertexList.size(), 8, GL11.GL_TRIANGLE_STRIP);
		
		
		vertexList.add(new Point2D(-20, -30));
		vertexList.add(new Point2D(30, -30));
		vertexList.add(new Point2D(-24, -10));
		vertexList.add(new Point2D(27, -10));
		vertexList.add(new Point2D(-25, 10));
		vertexList.add(new Point2D(25, 10));
		vertexList.add(new Point2D(-30, 30));
		vertexList.add(new Point2D(30, 30));
		 
		return or;
	}
	 
	private void drawObject(ObjectReference or)
	{
		Gdx.gl11.glDrawArrays(or.openGLPrimitiveType, or.firstIndex, or.vertexCount);
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
		/*******UPDATE CODE*******/
		angle += 180.0f * deltaTime;
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			position_x -= 500.0f * deltaTime;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			position_x += 500.0f * deltaTime;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			position_y -= 500.0f * deltaTime;
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
			position_y += 500.0f * deltaTime;
		
	}
	 
	private void drawScene()
	{
		Gdx.gl11.glColor4f(0.6f, 0.0f, 0.0f, 1.0f);
		
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(200, 200, 0);
		drawObject(littleBox);
		Gdx.gl11.glPopMatrix();
		 
		
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(300, 100, 0);
		Gdx.gl11.glRotatef(angle, 0, 0, 1);
		drawObject(bigBox);
		Gdx.gl11.glPopMatrix();
		
/*
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(200, 500, 0);
		drawObject(arbObject);
		Gdx.gl11.glPopMatrix();
*/

		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(200, 500, 0);
		drawObject(littleCircle);
		Gdx.gl11.glPopMatrix();

		
		 
		
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glColor4f(0.6f, 1.0f, 0.0f, 1.0f);
		Gdx.gl11.glTranslatef(position_x, position_y, 0);
		drawObject(bigBox);
		Gdx.gl11.glPopMatrix();
	}
	 
	private void display()
	{
	/*******GRAPHICS CODE*******/
		Gdx.gl11.glClearColor(0.4f, 0.6f, 1.0f, 1.0f);
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		
		
		
		Gdx.gl11.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluOrtho2D(Gdx.gl10, 0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());
		drawScene();
		 
		
		
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