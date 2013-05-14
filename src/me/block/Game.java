package me.block;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import me.block.screen.Gui;
import me.block.screen.Screen;
import me.block.texture.MyTextureLoader;
import me.block.util.MyUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.Log;
import static org.lwjgl.opengl.GL20.*;
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
	public static boolean DEBUG_FPS_CONSOLE = false;
	public static boolean DEBUG_FPS_TITLE = true;
	public static boolean DEBUG_VIEW = false;

	// General Setting
	public static final float MOUSESPEED = 0.3f;
	public static boolean CROSSHAIR = false;
	public static int CROSSHAIR_SIZE = 4;
	public static boolean MINIMAP = false;
	public static boolean CULL_FACE = false;
	public static boolean FOG = true;
	public static boolean NOCLIP = false;
	public static boolean RENDER_NICE = true;
	public static boolean LIGHTING = false;
	public static boolean SHADERS = false;

	// General variables
	private boolean finished = false;

	// Game Mechanics
	private Screen screen;
	private int fps = 0;
	private double lastFpsTime = 0;
	
	//Shader
	private int shaderProgram;
	private int vertexShader;
	private int fragmentShader;

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
//		gui = new Gui();

		// Bind the textures and set the Texture paremeter
		MyTextureLoader.SPRITESHEET.bind();

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		// Initiate OpenGL
		initOGL();
		
		if(SHADERS)
			initShaders();
		// Start the GameLoop
		gameLoop();

	}

	private void gameLoop() {

		long lastLoopTime = System.nanoTime();

		while (!finished) {

			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;

			lastFpsTime += updateLength;
			fps++;

			if (lastFpsTime >= 1000000000) {
				
				if(Game.DEBUG_FPS_CONSOLE)
					System.out.println("(FPS: " + fps + ")");
				
				if(Game.DEBUG_FPS_TITLE)
					Display.setTitle("FPS: " + fps);
				lastFpsTime = 0;
				fps = 0;
			}

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
		screen.renderGame();

		init2d();
		screen.renderGui();
	}

	private void update() {

		if (Display.isCloseRequested()
				|| Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			finished = true;

		screen.updateGame();
	}

	private void init3d() {
		glViewport(0, 0, WIDTH, HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60.0f, ((float) WIDTH / (float) HEIGHT), 0.1f, 1000.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);

		// Bind the textures and set the Texture paremeter
		MyTextureLoader.SPRITESHEET.bind();

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
//		enableFog();
	}

	private void init2d() {
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

		glEnable(GL_BLEND); 
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_TEXTURE_2D);

		if(FOG)
		enableFog();
		
		if(CULL_FACE)
		 glEnable(GL_CULL_FACE);

		if(LIGHTING)
			setUpLighting();
		
	}

	public void initShaders(){
		shaderProgram = glCreateProgram();
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		
		StringBuilder vertexShaderSource = new StringBuilder();
		StringBuilder fragmentShaderSource = new StringBuilder();

		try{
			BufferedReader reader = new BufferedReader(new FileReader("src/me/block/shaders/shader.vert"));
			String line;
			
			while((line = reader.readLine()) != null){
				vertexShaderSource.append(line+"\n");
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			BufferedReader reader = new BufferedReader(new FileReader("src/me/block/shaders/shader.frag"));
			String line;
			
			while((line = reader.readLine()) != null){
				fragmentShaderSource.append(line+"\n");
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		
		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);
		
		if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Vertex shader wasn't able to be compiled correctly. Error log:");
            System.err.println(glGetShaderInfoLog(vertexShader, 1024));
        }
		
		 if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
	            System.err.println("Fragment shader wasn't able to be compiled correctly. Error log:");
	            System.err.println(glGetShaderInfoLog(fragmentShader, 1024));
	        }
		
		glAttachShader(shaderProgram, fragmentShader);
		glAttachShader(shaderProgram, vertexShader);
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
		
		glUseProgram(shaderProgram);
		
	}
	
	private static void setUpLighting() {
        glShadeModel(GL_SMOOTH);
//        glEnable(GL_DEPTH_TEST);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glLightModel(GL_LIGHT_MODEL_AMBIENT, MyUtil.allocateFloat(new float[]{0.05f, 0.05f, 0.05f, 1f}));
        glLight(GL_LIGHT0, GL_POSITION, MyUtil.allocateFloat(new float[]{0, 0, 0, 1}));
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_DIFFUSE);
    }
	
	public static void enableFog() {

		glFog(GL_FOG_COLOR,
				MyUtil.allocateFloat(new float[] { 0.6f, 0.6f, 0.6f, 1f }));
		glFogf(GL_FOG_MODE, GL_EXP);
		glFogf(GL_FOG_DENSITY, .05f);
		glHint(GL_FOG_HINT, GL_DONT_CARE);
		glFogf(GL_FOG_START, 8.0f);
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
	}

}
