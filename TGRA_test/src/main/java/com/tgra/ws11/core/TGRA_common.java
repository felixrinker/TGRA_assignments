package com.tgra.ws11.core;

import com.badlogic.gdx.Gdx;

public class TGRA_common {

	/**
	 * Updates the y position with 
	 * boundaries check.
	 * 
	 * @param pos_x the x position
	 * @param changeRate the value to add oder subtract
	 * @param box_avg the box size
	 * @param flag true if plus otherwise false
	 * 
	 * @return the updates x position
	 */
	public static float update_x(float pos_x, float changeRate, float box_avg, boolean flag) {
		
		float sign = (flag) ? 1.0f : -1.0f;
		
		if(pos_x+box_avg+changeRate > Gdx.graphics.getWidth() && flag) {
				return Gdx.graphics.getWidth()-box_avg;
			}else if(pos_x-box_avg-changeRate < 0.0f && !flag) {
				return box_avg;
			}else {
				return pos_x += sign*changeRate;
			}
	}
	
	/**
	 * Updates the y position with 
	 * boundaries check.
	 * 
	 * @param pos_y the y position
	 * @param changeRate the value to add oder subtract
	 * @param box_avg the box size
	 * @param flag true if plus otherwise false
	 * 
	 * @return the updates y position
	 */
	public static float update_y(float pos_y, float changeRate, float box_avg, boolean flag) {
		
		float sign = (flag) ? 1.0f : -1.0f;
		
		if(pos_y+box_avg+changeRate > Gdx.graphics.getHeight() && flag) {
				return Gdx.graphics.getHeight()-box_avg;
		}else if(pos_y-box_avg-changeRate < 0.0f && !flag) {
				return box_avg;
		}else {
				return pos_y += sign*changeRate;
		}
	}
}
