package com.tgra.ws11.desktop;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.tgra.ws11.core.TGRA_lab01_core;

public class DesktopAppStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// lab01 core
		new JoglApplication(new TGRA_lab01_core(), "TGRA - lab01", 1024, 768, false);
	}
}
