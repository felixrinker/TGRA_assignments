package com.tgra.ws11.labs.desktop;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.tgra.ws11.labs.Andriod3DLibGTXTestCore;

public class DesktopAndroidStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// lab01 core
		new JoglApplication(new Andriod3DLibGTXTestCore(), "TGRA - lab01", 1024, 768, false);
		
		// part1 core
		//new JoglApplication(new TGRA_part1_core(), "TGRA - part1", 1024, 768, false);
		
		// part2 core
		//new JoglApplication(new TGRA_part2_core(), "TGRA - part2", 1024, 768, false);
		
		// part3 core
		//new JoglApplication(new TGRA_part3_core(), "TGRA - part3", 1024, 768, false);
	}
}
