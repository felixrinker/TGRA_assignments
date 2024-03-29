package com.tgra.ws11.desktop;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.tgra.ws11.core.TGRA_part3_core;

public class DesktopAppPart3Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// lab01 core
		//new JoglApplication(new TGRA_lab01_core(), "TGRA - lab01", 1024, 768, false);
		
		// part1 core
		//new JoglApplication(new TGRA_part1_core(), "TGRA - part1", 1024, 768, false);
		
		// part2 core
		//new JoglApplication(new TGRA_part2_core(), "TGRA - part2", 1024, 768, false);
		
		// part3 core
		new JoglApplication(new TGRA_part3_core(), "TGRA - part3", 1024, 768, false);
	}
}
