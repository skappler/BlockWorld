package me.block.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * 
 * @author skappler
 * 
 * This class represents every kind of entity
 * 
 */

public abstract class Entity {

	public Vector3f rotation;
	public Vector3f position;
	
	public float speed;
	public float height;
	
	public Entity(){
		
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		
	}
	
	public abstract void update();
	public abstract void render();
	
}
