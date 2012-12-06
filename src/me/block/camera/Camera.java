package me.block.camera;

import org.lwjgl.util.vector.Vector3f;

/**
 * 
 * @author skappler
 *
 * This class represents a camera. It has values for the position 
 * in the world and it's rotation
 * 
 */

public class Camera {

	public Vector3f position;
	public Vector3f rotation;
	
	public Camera(){
		
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		
	}
	
}
