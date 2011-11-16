package com.tgra.model;

import java.nio.FloatBuffer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Skybox {

    FloatBuffer vertexBuffer;
    FloatBuffer texCoordBuffer;
    
    Texture tex;
    boolean useTexture;
    
    public Skybox(String texture)
    {
        vertexBuffer = BufferUtils.newFloatBuffer(72);
        vertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
                                      0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
                                      0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
                                      0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
                                      0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
                                      -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
                                      -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
                                      -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
                                      -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
                                      0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
                                      -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
                                      0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f});
        vertexBuffer.rewind();
        
        texCoordBuffer = BufferUtils.newFloatBuffer(48);
        texCoordBuffer.put(new float[] {
            0.5f, 0.75f, 0.5f, 1.0f, 0.25f, 0.75f, 0.25f, 1.0f, // bottom
            0.25f, 0.75f, 0f, 0.75f, 0.25f, 0.5f, 0f, 0.5f, // left
            0.25f, 0.5f, 0.25f, 0.25f, 0.5f, 0.5f, 0.5f, 0.25f, // top
            0.5f, 0.5f, 0.75f, 0.5f, 0.5f, 0.75f, 0.75f, 0.75f, // right
            0.75f, 0.75f, 0.75f, 0.5f, 1f, 0.75f, 1.0f, 0.5f, // right right
            0.5f, 0.75f, 0.5f, 0.5f, 0.25f, 0.75f, 0.25f, 0.5f, // center
        });
        texCoordBuffer.rewind();
        
        tex = new Texture(Gdx.files.internal(texture));
        useTexture = true;
    }
    

    public void draw()
    {
		Gdx.gl11.glDisable(GL11.GL_LIGHTING);
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(0.5f, 0f, 0.5f);
        Gdx.gl11.glScalef(80f, 80f, 80f);

        Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
        Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);

        if (useTexture) {
            Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
            Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            
            tex.bind();  //Gdx.gl11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

            Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);
        }

        Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
        Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
        Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
        Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
        Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
        Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);

        Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        Gdx.gl11.glPopMatrix();
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
        
    }


    /**
     * Sets whether or not this instance is useTexture.
     *
     * @param useTexture The useTexture.
     */
    public void setUseTexture(boolean useTexture)
    {
        this.useTexture = useTexture;
    }
}
