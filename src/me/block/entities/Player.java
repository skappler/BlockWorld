package me.block.entities;

import java.util.HashMap;
import java.util.HashSet;

import me.block.Game;
import me.block.camera.Camera;
import me.block.cubes.Block;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.geom.Rectangle;

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
	
	private HashMap<Integer, Boolean> keyPressed;

	private Vector3f oldPos;
	private Vector3f delta;	
	private Vector3f view;
	
	public Player() {
		super();

		this.camera = new Camera();
		this.oldPos = new Vector3f();
		this.delta = new Vector3f();
		this.view = new Vector3f();
		this.speed = 0.1f;
		this.jumpSpeed = (float) (2.5 * speed);
		this.gravitySpeed = (float) (1.5 * speed);
		this.trueSpeed = speed;
		this.height = BASE_HEIGHT;
		this.position.y = 6.0f;
		this.position.x = 5.0f;
		this.position.z = 5.0f;

		//init all keys the player should be able to use
		this.keyPressed = new HashMap<Integer, Boolean>();
		this.keyPressed.put(Keyboard.KEY_F, false);
		this.keyPressed.put(Keyboard.KEY_M, false);
		
		setCamera();
	}

	@Override
	public void update() {

		tick++;
		// bobbing += Math.sin(tick/5)/75;
		
		handleInput();
		jump();
		if(!Game.NOCLIP)
			gravity();
		setCamera();

	}

	@Override
	public void render() {
		
	}

	public boolean checkCollision() {

		if (currentChunk == null || Game.NOCLIP)
			return false;

		// Get the player coordinates
		int x = (int) Math.floor(this.position.x );
		int y = (int) Math.floor(this.position.y );
		int z = (int) Math.floor(this.position.z );
		y--;

		
		Label: {
			if (check_Block(x - 1, y + 1, z - 1))
				break Label;
			if (check_Block(x - 1, y + 1, z))
				break Label;
			if (check_Block(x - 1, y + 1, z + 1))
				break Label;
			if (check_Block(x, y + 1, z - 1))
				break Label;
			if (check_Block(x, y + 1, z))
				break Label;
			if (check_Block(x, y + 1, z + 1))
				break Label;
			if (check_Block(x + 1, y + 1, z - 1))
				break Label;
			if (check_Block(x + 1, y + 1, z))
				break Label;
			if (check_Block(x + 1, y + 1, z + 1))
				break Label;

			if (check_Block(x - 1, y + 2, z - 1))
				break Label;
			if (check_Block(x - 1, y + 2, z))
				break Label;
			if (check_Block(x - 1, y + 2, z + 1))
				break Label;
			if (check_Block(x, y + 2, z - 1))
				break Label;
			if (check_Block(x, y + 2, z))
				break Label;
			if (check_Block(x, y + 2, z + 1))
				break Label;
			if (check_Block(x + 1, y + 2, z - 1))
				break Label;
			if (check_Block(x + 1, y + 2, z))
				break Label;
			if (check_Block(x + 1, y + 2, z + 1))
				break Label;
			
			return false;
		}
		
		
		
		return true;

	}

	public boolean check_Block(int x, int y, int z) {
		
		Block b = currentChunk.getBlock(x, y, z);
		
		if (b != null && this.getBounds().intersects(b.getBounds())) {
			this.position.x = oldPos.x;
			this.position.z = oldPos.z;
			
			return true;
		}
		return false;
	}

	

	public void handleInput() {
		
		view.x = (float) Math.sin(Math.toRadians(this.rotation.y));// + camera.position.x;
		view.z = -(float) Math.cos(Math.toRadians(this.rotation.y));//+ camera.position.z;
		view.y = -(float) Math.sin(Math.toRadians(this.rotation.x));//+ camera.position.y;
		view.normalise();
			
		oldPos.x = position.x;
		oldPos.y = position.y;
		oldPos.z = position.z;
		
		int n = 0; 
		delta.x = 0;
		delta.y = 0;
		delta.z = 0;

		this.rotation.x -= Mouse.getDY() * Game.MOUSESPEED;
		if (this.rotation.x < -85)
			this.rotation.x = -85;
		if (this.rotation.x > 85)
			this.rotation.x = 85;

		this.rotation.y += Mouse.getDX() * Game.MOUSESPEED;
		if (this.rotation.y > 360)
			this.rotation.y -= 360;
		if (this.rotation.y < 0)
			this.rotation.y += 360;
		
		if (Game.DEBUG_PLAYER_POSITION) {
			Display.setTitle("" + this.position.x + " " + this.position.y + " "
					+ this.position.z);
		}
		
		// this.height = BASE_HEIGHT;

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.delta.x += (float) Math
					.sin(Math.toRadians(this.rotation.y)) * trueSpeed;
			this.delta.z += -(float) Math.cos(Math
					.toRadians(this.rotation.y)) * trueSpeed;
			if(Game.NOCLIP)
				this.delta.y += -(float) Math.sin(Math.toRadians(this.rotation.x)) * trueSpeed;

			
			this.height = BASE_HEIGHT + bobbing;
			n++;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.delta.x -= (float) Math
					.sin(Math.toRadians(this.rotation.y)) * trueSpeed;
			this.delta.z -= -(float) Math.cos(Math
					.toRadians(this.rotation.y)) * trueSpeed;
			if(Game.NOCLIP)
				this.delta.y -= -(float) Math.sin(Math.toRadians(this.rotation.x)) * trueSpeed;
			
			this.height = BASE_HEIGHT + bobbing;
			n++;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.delta.x += (float) Math.sin(Math
					.toRadians(this.rotation.y + 90)) * trueSpeed;
			this.delta.z += -(float) Math.cos(Math
					.toRadians(this.rotation.y + 90)) * trueSpeed;
			this.height = BASE_HEIGHT + bobbing;
			n++;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.delta.x += (float) Math.sin(Math
					.toRadians(this.rotation.y - 90)) * trueSpeed;
			this.delta.z += -(float) Math.cos(Math
					.toRadians(this.rotation.y - 90)) * trueSpeed;
			this.height = BASE_HEIGHT + bobbing;
			n++;
		}

		if(n != 0){
			delta.x /= n;
			delta.y /= n;
			delta.z /= n;

		}
		
		position.x += delta.x;
		position.y += delta.y;
		position.z += delta.z;
		
		long t1 = 0;
		if (Game.DEBUG_COLLISION_TIME)
			t1 = System.nanoTime();

		
		if(checkCollision()){
			
			position.x += delta.x;
			if(checkCollision()){
				position.z += delta.z;
				checkCollision();
			}
			
		}
		
		if (Game.DEBUG_COLLISION_TIME) {
			long t2 = System.nanoTime();
			System.out.println("Debug Collision time: " + (t2 - t1));
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			this.trueSpeed = (float) (1.8 * speed);
		} else {
			this.trueSpeed = speed;
		}
		
		checkChunk();
		
		// +--- END MOVEMENT CODE ---+
		// Jumping Code

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (!jumpKey && readyToJump) {
				jumping = true;
			}
			jumpKey = true;

		} else {
			jumpKey = false;
		}
		
		
		// Toggling of Gui Elements
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			
			if(!keyPressed.get(Keyboard.KEY_F)){
				Game.FOG = ! Game.FOG;
				if(Game.FOG)
					Game.enableFog();
				else
					GL11.glDisable(GL11.GL_FOG);
				
			}
			
			keyPressed.put(Keyboard.KEY_F, true);
			
		}else{
			keyPressed.put(Keyboard.KEY_F, false);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_M)){
			if(!keyPressed.get(Keyboard.KEY_M))
				Game.MINIMAP = !Game.MINIMAP;
			keyPressed.put(Keyboard.KEY_M, true);
		}else{
			keyPressed.put(Keyboard.KEY_M, false);
		}


	}

	private void jump() {

		if (jumping || (readyToJump && jumpKey)) {

			jumping = true;
			readyToJump = false;
			
			Block above = null;
						
			if(currentChunk != null){
				above = currentChunk.getBlock((int) Math.floor(position.x),
						(int) Math.floor(position.y + this.height + 1),
						(int) Math.floor(position.z));
			}
					
			if(above == null ){
					
				position.y += jumpSpeed;
				currentJump += jumpSpeed;
			}else{
					
				position.y = above.getCoordinates().y -1 -height;
				
				jumping = false;
				currentJump = 0f;
			}
			

			if (currentJump >= maxJump ) {
								
				jumping = false;
				currentJump = 0f;
			}
		}
	}

	public void gravity() {

		
		//++++++++++++++
		// PROBLEM: auch am rand würde collision stattfinden. Lösung: checke die ecken
		//++++++++++++++
		
//		boolean mayFall = true;
//		float bottom = 1000f;
//		Rectangle bounds = getBounds();
//		Block b = null;
//				
//		for(int i = 0; i < bounds.getPointCount(); i++){
//			
//			if(currentChunk != null && (b=currentChunk.getBlock(bounds.getPoint(i)[0], position.y -1,bounds.getPoint(i)[1])) != null){
//				mayFall = false;
//				bottom = b.getCoordinates().y  +1;
////				System.out.println(currentChunk.getBlock(bounds.getPoint(i)[0],position.y,bounds.getPoint(i)[1]).toString());
//				break;
//			}
//			
//
//		}
//		
//		if(mayFall)
//			position.y -= gravitySpeed;
//		else{
////			for(int i = 0; i < bounds.getPointCount();i++){
////				if(currentChunk.getBlock(bounds.getPoint(i)[0], position.y +1,bounds.getPoint(i)[1]) != null){
////					position.y -= gravitySpeed;
////					System.out.println("true");
////					break;
////				}
////			}
//			if( position.y -1 > bottom)
//				position.y -= gravitySpeed;
//			else
//			if(position.y -1 <= bottom)
//				if(!jumping)
//					position.y = bottom;
//				readyToJump = true;
//		}
			
			
			
//		System.out.println(mayFall+" "+bottom+ " "+position.y);
	
		
		Block under = null;

		if(currentChunk != null){
			under = currentChunk.getBlock((int) Math.floor(position.x),
					(int) Math.floor(position.y -1),
					(int) Math.floor(position.z));
		}
		
		
//		if(under != null){
//		Display.setTitle(under.getCoordinates().toString());
//		System.out.println(under.getCoordinates().toString() + " "+ ((int) Math.floor(position.y - 1)));
//		}
//		else
//			Display.setTitle("null");
		
		
		
		
		if (under == null) {
			
				position.y -= gravitySpeed;
		} else {

			if (under != null && position.y > under.getCoordinates().y + 1){

					position.y -= gravitySpeed;
				
			}
			if (under != null && position.y <= under.getCoordinates().y + 1) {
				position.y = under.getCoordinates().y + 1;
				readyToJump = true;

			}
		}
		
	
		
		if (position.y < -8)
			position.y = 16.0f;
	}
	
	public Rectangle getBounds() {
//		return new Rectangle(position.x, position.z, 0f, 0f);
		float size = .3f;
		 return new Rectangle((float)(position.x - size / 2),(float)
		 (position.z - size / 2),(float) size,(float) size);
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
