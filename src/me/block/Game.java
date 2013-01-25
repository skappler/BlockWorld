package me.block;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import me.block.cubes.Block;
import me.block.screen.Screen;
import me.block.texture.MyTextureLoader;
import me.block.util.MyUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * 
 * @author skappler
 * 
 *         The main game class responsible for starting the game and controlling
 *         the gameloop
 * 
 */

public class Game {

	// ########## Variables ############
	
	// CONSTANTS
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Block World";
	
	// General Setting
	public static final float MOUSESPEED = 0.3f;

	// General variables
	private boolean finished = false;

	// Game Mechanics
	private Screen screen;

	// ########## Methods ##############

	public Game() {
		
	}

	private void start() throws LWJGLException {

		// Initiate the Display and Input
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.setVSyncEnabled(true);
		Display.setTitle(TITLE);
		Display.create();

		Keyboard.create();
		Mouse.create();
		Mouse.setGrabbed(true);

		Log.setVerbose(false);
		
		// Initiate Variables
		screen = new Screen();

		//Bind the textures and set the Texture paremeter
		MyTextureLoader.SPRITESHEET.bind();

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		// Initiate OpenGL
		initOGL();

		// Start the GameLoop
		gameLoop();

	}

	private void gameLoop() {
		
		while (!finished) {

			update();
			render();

			Display.update();
			Display.sync(60);

		}

		Display.destroy();
	}

	private void render() {

		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
		glLoadIdentity();

		screen.render();

	}

	private void update() {

		if (Display.isCloseRequested()
				|| Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			finished = true;

		screen.update();

	}

	private void initOGL() {

		glViewport(0, 0, WIDTH, HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, ((float) WIDTH / (float) HEIGHT), 0.1f, 1000.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glShadeModel(GL_SMOOTH);
		glClearColor(0.35f, 0.6f, 0.8f, 1.0f);

		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		glEnable(GL_TEXTURE_2D);
		
		enableFog();
		
//		glEnable(GL_CULL_FACE);
	}

	public void enableFog(){
		
		glFog(GL_FOG_COLOR, MyUtil.allocateFloat(new float[]{0.4f,0.4f,0.4f,1f}));
		glFogf(GL_FOG_MODE, GL_EXP);
		glFogf(GL_FOG_DENSITY, .015f);
		glHint(GL_FOG_HINT, GL_DONT_CARE);
		glFogf (GL_FOG_START, 10.0f);
	    glFogf (GL_FOG_END, 40.0f);
	    glEnable(GL_FOG);
		
	}
	
	// ########### START THE GAME #######
	
	public static void main(String[] args) {

//		System.loadLibrary("jawt");		
		
		Game game = new Game();

		try {
			game.start();
		} catch (LWJGLException e) {
			System.err.println("Could not create the display");
			System.exit(1);
		}
		
//		int i = -31;
//		int x = i % 16;
//		
//		System.out.println(x);
		
		
//		ArrayList<Integer> ints = new ArrayList<Integer>(5000);
//		ints.add(0, 4711);
//		ints.add(2, 1337);
//		
//		for(Integer i : ints)
//			System.out.println(i);
	}

}
