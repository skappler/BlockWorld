package me.block;

import java.util.ArrayList;

import me.block.screen.Screen;
import me.block.util.MyTextureLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
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
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		glEnable(GL_TEXTURE_2D);
		
//		glEnable(GL_CULL_FACE);
	}

	// ########### START THE GAME #######

	public static void main(String[] args) {

		Game game = new Game();

		try {
			game.start();
		} catch (LWJGLException e) {
			System.err.println("Could not create the display");
			System.exit(1);
		}
		

//		ArrayList<Integer> ints = new ArrayList<Integer>(5000);
//		ints.add(0, 4711);
//		ints.add(2, 1337);
//		
//		for(Integer i : ints)
//			System.out.println(i);
	}

}
