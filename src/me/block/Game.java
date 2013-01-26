package me.block;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DONT_CARE;
import static org.lwjgl.opengl.GL11.GL_EXP;
import static org.lwjgl.opengl.GL11.GL_FOG;
import static org.lwjgl.opengl.GL11.GL_FOG_COLOR;
import static org.lwjgl.opengl.GL11.GL_FOG_DENSITY;
import static org.lwjgl.opengl.GL11.GL_FOG_END;
import static org.lwjgl.opengl.GL11.GL_FOG_HINT;
import static org.lwjgl.opengl.GL11.GL_FOG_MODE;
import static org.lwjgl.opengl.GL11.GL_FOG_START;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFog;
import static org.lwjgl.opengl.GL11.glFogf;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import me.block.screen.Gui;
import me.block.screen.Screen;
import me.block.texture.MyTextureLoader;
import me.block.util.MyUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.util.Log;

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

	// DEBUG FLAGS
	public static boolean DEBUG_COLLISION_TIME = false;
	public static boolean DEBUG_PLAYER_POSITION = false;
	public static boolean DEBUG_PLAYER_CHUNK = false;
	public static boolean DEBUG_WORLD_COLOR = false;

	// General Setting
	public static final float MOUSESPEED = 0.3f;
	public static boolean CROSSHAIR = false;

	// General variables
	private boolean finished = false;

	// Game Mechanics
	private Screen screen;
	private Gui gui;

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
		gui = new Gui();

		// Bind the textures and set the Texture paremeter
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

		init3d();
		screen.render();

		init2d();
		gui.render();
	}

	private void update() {

		if (Display.isCloseRequested()
				|| Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			finished = true;

		screen.update();
		gui.update(screen.getPlayer().position.x, screen.getPlayer().position.z);

	}

	private void init3d(){
		glViewport(0, 0, WIDTH, HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, ((float) WIDTH / (float) HEIGHT), 0.1f, 1000.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		 glDepthFunc(GL_LEQUAL);
		 glEnable(GL_DEPTH_TEST);
		 
		// Bind the textures and set the Texture paremeter
			MyTextureLoader.SPRITESHEET.bind();

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}
	
	private void init2d(){
		glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    gluOrtho2D(0.0f, WIDTH, HEIGHT, 0.0f);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();

	    glDisable(GL_DEPTH_TEST);
	    
	    
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

		// glEnable(GL_CULL_FACE);
	}

	public void enableFog() {

		glFog(GL_FOG_COLOR,
				MyUtil.allocateFloat(new float[] { 0.4f, 0.4f, 0.4f, 1f }));
		glFogf(GL_FOG_MODE, GL_EXP);
		glFogf(GL_FOG_DENSITY, .015f);
		glHint(GL_FOG_HINT, GL_DONT_CARE);
		glFogf(GL_FOG_START, 10.0f);
		glFogf(GL_FOG_END, 40.0f);
		glEnable(GL_FOG);

	}

	// ########### START THE GAME #######

	public static void main(String[] args) {

		// System.loadLibrary("jawt");

		Game game = new Game();

		try {
			game.start();
		} catch (LWJGLException e) {
			System.err.println("Could not create the display");
			System.exit(1);
		}

		// int i = -31;
		// int x = i % 16;
		//
		// System.out.println(x);

		// ArrayList<Integer> ints = new ArrayList<Integer>(5000);
		// ints.add(0, 4711);
		// ints.add(2, 1337);
		//
		// for(Integer i : ints)
		// System.out.println(i);
	}

}
