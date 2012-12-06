package me.block.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import me.block.Game;
import me.block.camera.Camera;

public class Player extends Entity {

	public Camera camera;
	public float walking = 0;
	private int tick = 0;
	private float baseHeight = 1.6f;
	public Player(){
		super();
		
		this.camera = new Camera();
		this.speed = 0.1f;
		this.height = 1.6f;
	}
	
	@Override
	public void update() {
		
		
//		tick++;
//		walking += Math.sin(tick/5)/75;
		
		handleInput();
	}

	@Override
	public void render() {

	}
	
	public void handleInput(){
		
		this.rotation.x -= Mouse.getDY() * Game.MOUSESPEED;
		if(this.rotation.x < -85)
			this.rotation.x = -85;
		if(this.rotation.x > 85)
			this.rotation.x = 85;
		
		Display.setTitle(""+this.position.x+" "+this.position.z);
		
		this.rotation.y += Mouse.getDX() * Game.MOUSESPEED;
		if(this.rotation.y > 360)
			this.rotation.y -= 360;
		if(this.rotation.y < 0)
			this.rotation.y += 360;
		
		this.height = baseHeight;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.position.x += (float) Math.sin(Math.toRadians(this.rotation.y))*speed;
			this.position.z += -(float) Math.cos(Math.toRadians(this.rotation.y))*speed;
			this.height = baseHeight+walking;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.position.x -= (float) Math.sin(Math.toRadians(this.rotation.y))*speed;
			this.position.z -= -(float) Math.cos(Math.toRadians(this.rotation.y))*speed;
			this.height = baseHeight+walking;

		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.position.x += (float) Math.sin(Math.toRadians(this.rotation.y+90))*speed;
			this.position.z += -(float) Math.cos(Math.toRadians(this.rotation.y+90))*speed;
			this.height = baseHeight+walking;

		}
			
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.position.x += (float) Math.sin(Math.toRadians(this.rotation.y-90))*speed;
			this.position.z += -(float) Math.cos(Math.toRadians(this.rotation.y-90))*speed;
			this.height = baseHeight+walking;

		}
		
		camera.rotation = this.rotation;
		camera.position = this.position;
	}

}
