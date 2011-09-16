package com.tgra;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter {
	public static void main(String[] args)
	{
		new LwjglApplication((ApplicationListener)new Transformations_3D_Core(), "Transformations_2D", 500, 500, false);
	}
}
