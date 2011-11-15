package com.tgra.starter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.tgra.core.Maze3D_Core;

public class DesktopStarter
{
	public static void main(String[] args)
	{
	new LwjglApplication(new Maze3D_Core(), "First 3D Program", 1024, 768, false);
		//new LwjglApplication(new Maze3D_Core(), "First 3D Program", 640, 480, false);
	}
}
