package com.tgra.ws11.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.tgra.ws11.core.Astroid_core;

/**
 * 
 * @author Felix Rinker
 * @author Sara van de Moosdijk
 *
 */
public class AstroidAppStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new LwjglApplication(new Astroid_core(), "TGRA - assign2", 1024, 768, false);
	}
}
