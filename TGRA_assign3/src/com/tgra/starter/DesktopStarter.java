package com.tgra.starter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.tgra.core.First3D_Core;

public class DesktopStarter
{
	public static void main(String[] args)
	{
		new LwjglApplication(new First3D_Core(), "First 3D Program", 640, 480, false);
	}
}
