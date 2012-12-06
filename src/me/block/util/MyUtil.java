package me.block.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 
 * @author skappler
 *
 * A utility class that contains usefull methods
 * 
 */

public class MyUtil {

	
	/**
	 * Allocates memory that contains the passed floats
	 * 
	 * @param f Array that contains the floats to allocate
	 * @return A FloatBuffer with the floats of the array
	 */
	public static FloatBuffer allocateFloat(float[] f){
		
		FloatBuffer temp = ByteBuffer.allocateDirect(f.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		temp.put(f).flip();
		return temp;
	}
	
	
}
