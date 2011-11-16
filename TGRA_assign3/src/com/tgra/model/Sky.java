package com.tgra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

public class Sky {

	private static float DTOR = (float) (Math.PI /180f);
	int NumVertices;
	private Vertex[] Vertices;   
	   
	public Sky(float radius, float dtheta, float dphi, float hTile, float vTile)   
	{   
	    int theta, phi;   
	   
	    // Initialize our Vertex array   
	    NumVertices = (int)((360/dtheta)*(90/dphi)*4);   
	    Vertices = new Vertex[NumVertices];   
	   
	    // Used to calculate the UV coordinates   
	    float vx, vy, vz, mag;   
	   
	    // Generate the dome   
	    int n = 0;   
	    for (phi=0; phi <= 90 - dphi; phi += (int)dphi)   
	    {   
	        for (theta=0; theta <= 360 - dtheta; theta += (int)dtheta)   
	        {   
	            // Calculate the vertex at phi, theta   
	            Vertices[n].x = (float) (radius * Math.sin(phi*DTOR) * Math.cos(DTOR*theta));   
	            Vertices[n].y = (float) (radius * Math.sin(phi*DTOR) * Math.sin(DTOR*theta));   
	            Vertices[n].z = (float) (radius * Math.cos(phi*DTOR));   
	   
	            // Create a vector from the origin to this vertex   
	            vx = Vertices[n].x;   
	            vy = Vertices[n].y;   
	            vz = Vertices[n].z;   
	   
	            // Normalize the vector   
	            mag = (float)Math.sqrt(Math.pow(vx,2)+Math.pow(vy,2)+Math.pow(vz,2));   
	            vx /= mag;   
	            vy /= mag;   
	            vz /= mag;   
	   
	            // Calculate the spherical texture coordinates   
	            Vertices[n].u = hTile * (float)(Math.atan2(vx, vz)/(Math.PI*2)) + 0.5f;   
	            Vertices[n].v = vTile * (float)(Math.asin(vy) / Math.PI) + 0.5f;        
	            n++;   
	   
	            // Calculate the vertex at phi+dphi, theta   
	            Vertices[n].x = (float) (radius * Math.sin((phi+dphi)*DTOR) * Math.cos(theta*DTOR));   
	            Vertices[n].y = (float) (radius * Math.sin((phi+dphi)*DTOR) * Math.sin(theta*DTOR));   
	            Vertices[n].z = (float) (radius * Math.cos((phi+dphi)*DTOR));   
	               
	            // Calculate the texture coordinates   
	            vx = Vertices[n].x;   
	            vy = Vertices[n].y;   
	            vz = Vertices[n].z;   
	   
	            mag = (float)Math.sqrt(Math.pow(vx,2)+Math.pow(vy,2)+Math.pow(vz,2));   
	            vx /= mag;   
	            vy /= mag;   
	            vz /= mag;   
	   
	            Vertices[n].u = hTile * (float)(Math.atan2(vx, vz)/(Math.PI*2)) + 0.5f;   
	            Vertices[n].v = vTile * (float)(Math.asin(vy) / Math.PI) + 0.5f;        
	            n++;   
	   
	            // Calculate the vertex at phi, theta+dtheta   
	            Vertices[n].x = (float) (radius * Math.sin(DTOR*phi) * Math.cos(DTOR*(theta+dtheta)));   
	            Vertices[n].y = (float) (radius * Math.sin(DTOR*phi) * Math.sin(DTOR*(theta+dtheta)));   
	            Vertices[n].z = (float) (radius * Math.cos(DTOR*phi));   
	               
	            // Calculate the texture coordinates   
	            vx = Vertices[n].x;   
	            vy = Vertices[n].y;   
	            vz = Vertices[n].z;   
	   
	            mag = (float)Math.sqrt(Math.pow(vx,2)+Math.pow(vy,2)+Math.pow(vz,2));   
	            vx /= mag;   
	            vy /= mag;   
	            vz /= mag;   
	   
	            Vertices[n].u = hTile * (float)(Math.atan2(vx, vz)/(Math.PI*2)) + 0.5f;   
	            Vertices[n].v = vTile * (float)(Math.asin(vy) / Math.PI) + 0.5f;        
	            n++;   
	   
	            if (phi > -90 && phi < 90)   
	            {   
	                // Calculate the vertex at phi+dphi, theta+dtheta   
	                Vertices[n].x = (float) (radius * Math.sin((phi+dphi)*DTOR) * Math.cos(DTOR*(theta+dtheta)));   
	                Vertices[n].y = (float) (radius * Math.sin((phi+dphi)*DTOR) * Math.sin(DTOR*(theta+dtheta)));   
	                Vertices[n].z = (float) (radius * Math.cos((phi+dphi)*DTOR));   
	                   
	                // Calculate the texture coordinates   
	                vx = Vertices[n].x;   
	                vy = Vertices[n].y;   
	                vz = Vertices[n].z;   
	   
	                mag = (float)Math.sqrt(Math.pow(vx,2)+Math.pow(vy,2)+Math.pow(vz,2));   
	                vx /= mag;   
	                vy /= mag;   
	                vz /= mag;   
	   
	                Vertices[n].u = hTile * (float)(Math.atan2(vx, vz)/(Math.PI*2)) + 0.5f;   
	                Vertices[n].v = vTile * (float)(Math.asin(vy) / Math.PI) + 0.5f;        
	                n++;   
	            }   
	        }   
	    }   
	   
	    // Fix the problem at the seam   
	   /* for (int i=0; i < NumVertices-3; i++)   
	    {   
	        if (Vertices[i].u - Vertices[i+1].u > 0.9f)   
	            Vertices[i+1].u += 1.0f;   
	   
	        if (Vertices[i+1].u - Vertices[i].u > 0.9f)   
	            Vertices[i].u += 1.0f;   
	   
	        if (Vertices[i].u - Vertices[i+2].u > 0.9f)   
	            Vertices[i+2].u += 1.0f;   
	   
	        if (Vertices[i+2].u - Vertices[i].u > 0.9f)   
	            Vertices[i].u += 1.0f;   
	   
	        if (Vertices[i+1].u - Vertices[i+2].u > 0.9f)   
	            Vertices[i+2].u += 1.0f;   
	   
	        if (Vertices[i+2].u - Vertices[i+1].u > 0.9f)   
	            Vertices[i+1].u += 1.0f;   
	   
	        if (Vertices[i].v - Vertices[i+1].v > 0.8f)   
	            Vertices[i+1].v += 1.0f;   
	   
	        if (Vertices[i+1].v - Vertices[i].v > 0.8f)   
	            Vertices[i].v += 1.0f;   
	   
	        if (Vertices[i].v - Vertices[i+2].v > 0.8f)   
	            Vertices[i+2].v += 1.0f;   
	   
	        if (Vertices[i+2].v - Vertices[i].v > 0.8f)   
	            Vertices[i].v += 1.0f;   
	   
	        if (Vertices[i+1].v - Vertices[i+2].v > 0.8f)   
	            Vertices[i+2].v += 1.0f;   
	   
	        if (Vertices[i+2].v - Vertices[i+1].v > 0.8f)   
	            Vertices[i+1].v += 1.0f;   
	    }   */
	}   
	   
	public void draw()   
	{   
	    Gdx.gl11.glPushMatrix();   
	    Gdx.gl11.glTranslatef(0.0f, -100.0f, 0.0f);   
	    //Gdx.gl11. glRotatef(timeGetTime()/2000.0f,0.0f, 1.0f, 0.0f);   
	    Gdx.gl11.glRotatef(270, 1.0f, 0.0f, 0.0f);   
	   
	    
	   
	    for (int i=0; i < NumVertices; i++)   
	    {   
	    	Gdx.gl11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);   
	   
	        //glTexCoord2f(Vertices[i].u, Vertices[i].v);   
	        Gdx.gl11.glNormal3f(Vertices[i].x, Vertices[i].y, Vertices[i].z);
	        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	    }   
	    Gdx.gl11.glPopMatrix();   
	}   
}
