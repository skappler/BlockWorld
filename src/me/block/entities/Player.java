package me.block.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import me.block.Game;
import me.block.camera.Camera;
import me.block.cubes.Block;

/**
 * 
 * @author skappler
 * 
 *         This class represents the Player. It inherits the position and
 *         rotation from the Entity class. The position variable stores the
 *         position of the players feet. It also has a Camera Object which is
 *         the current position of the camera.
 * 
 *         At the moment the camera is at the head of the player.
 * 
 */

public class Player extends Entity {

	// CONSTANTS
	public final float BASE_HEIGHT = .6f; // The normal height of the player

	// General variables
	public Camera camera;
	private long tick = 0;

	// Special Purpose Variables
	private float bobbing = 0;
	
	private float gravitySpeed;
	
	private float jumpSpeed;
	private float maxJump = 3.3f;
	private float currentJump = 0f;
	private boolean jumping = false;
	private boolean jumpKey = false;
	private boolean readyToJump = true;
	
	private float trueSpeed;

	public Player() {
		super();
		
		this.camera = new Camera();
		this.speed = 0.08f;
		this.jumpSpeed=  (float) (3.*speed);
		this.gravitySpeed = (float) (1.5*speed);
		this.trueSpeed = speed;
		this.height = BASE_HEIGHT;
		this.position.y = 8.0f;
		this.position.x = 8.0f;
		this.position.z = 8.0f;

		setCamera();
	}

	@Override
	public void update() {

		tick++;
		// bobbing += Math.sin(tick/5)/75;

		handleInput();
		jump();
		gravity();
		setCamera();

	}

	@Override
	public void render() {

	}

	public void gravity() {

		Block under = null;

		try {
			under = currentChunk.getBlock((int) position.x,
					(int) position.y - 1, (int) position.z);
		} catch (NullPointerException e) {
			under = null;
		}

		if (under == null) {
			position.y -= gravitySpeed;
		} else {

			if (position.y > under.getCoordinates().y + 1)
				position.y -= gravitySpeed;
			if (position.y <= under.getCoordinates().y + 1){
				position.y = under.getCoordinates().y + 1;
				readyToJump = true;

			}
		}

		if (position.y < -8)
			position.y = 16.0f;
	}

	public void handleInput() {

		this.rotation.x -= Mouse.getDY() * Game.MOUSESPEED;
		if (this.rotation.x < -85)
			this.rotation.x = -85;
		if (this.rotation.x > 85)
			this.rotation.x = 85;

//		Display.setTitle("" + this.position.x + " " + this.position.y + " "
//				+ this.position.z);

		this.rotation.y += Mouse.getDX() * Game.MOUSESPEED;
		if (this.rotation.y > 360)
			this.rotation.y -= 360;
		if (this.rotation.y < 0)
			this.rotation.y += 360;

//		this.height = BASE_HEIGHT;

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.position.x += (float) Math
					.sin(Math.toRadians(this.rotation.y)) * trueSpeed;
			this.position.z += -(float) Math.cos(Math
					.toRadians(this.rotation.y)) * trueSpeed;
			this.height = BASE_HEIGHT + bobbing;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.position.x -= (float) Math
					.sin(Math.toRadians(this.rotation.y)) * trueSpeed;
			this.position.z -= -(float) Math.cos(Math
					.toRadians(this.rotation.y)) * trueSpeed;
			this.height = BASE_HEIGHT + bobbing;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.position.x += (float) Math.sin(Math
					.toRadians(this.rotation.y + 90)) * trueSpeed;
			this.position.z += -(float) Math.cos(Math
					.toRadians(this.rotation.y + 90)) * trueSpeed;
			this.height = BASE_HEIGHT + bobbing;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.position.x += (float) Math.sin(Math
					.toRadians(this.rotation.y - 90)) * trueSpeed;
			this.position.z += -(float) Math.cos(Math
					.toRadians(this.rotation.y - 90)) * trueSpeed;
			this.height = BASE_HEIGHT + bobbing;

		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			this.trueSpeed = (float) (1.8*speed);
		}else{
			this.trueSpeed = speed;
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			if(!jumpKey ){
				jumping = true;
			}
			jumpKey = true;

		}else{
			jumpKey = false;
		}
		
		checkChunk();

	}

	private void jump(){
		
		if( jumping || (readyToJump && jumpKey)){
					
			jumping = true;
			readyToJump = false;
			position.y += jumpSpeed;
			currentJump += jumpSpeed;
			
			if(currentJump >= maxJump){
				jumping = false;
				currentJump = 0f;
			}
		}	
	}
	
	
	public void setCamera() {

		camera.rotation.x = this.rotation.x;
		camera.rotation.y = this.rotation.y;
		camera.rotation.z = this.rotation.z;

		camera.position.x = this.position.x;
		camera.position.z = this.position.z;
		camera.position.y = this.position.y + height; // Set the camera position

	}

}
