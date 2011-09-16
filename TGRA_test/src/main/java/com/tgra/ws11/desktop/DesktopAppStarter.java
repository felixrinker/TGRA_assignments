package com.tgra.ws11.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.tgra.ws11.core.TGRA_test01_core;

public class DesktopAppStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new LwjglApplication(new TGRA_test01_core(), "TGRA - part1", 1024, 768, false);
	}
}
