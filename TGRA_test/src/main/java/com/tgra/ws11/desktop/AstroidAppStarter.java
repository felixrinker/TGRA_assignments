package com.tgra.ws11.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class AstroidAppStarter {

	
	public static void main(String args[]) {
		new LwjglApplication(new Game(), "Asteroids",640,480,false);
	}
}
