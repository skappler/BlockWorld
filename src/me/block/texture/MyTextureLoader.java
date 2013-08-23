package me.block.texture;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * 
 * @author skappler
 * 
 *         This class provides static methods to load textures using the slick
 *         TextureLoader
 */

public class MyTextureLoader {

	// Static values that map a Texture to it position
	public static final byte GRASS_SIDE = 0;
	public static final byte GRASS_TOP = 1;
	public static final byte GRASS_BOTTOM = 2;

	public static final byte STONEFLOOR = 5;
	public static final byte STONEWALL = 3;

	// Static Textures to prevent using too much memory
	// The single Sprites can be accesed by their position
	// starting at 0
	public static Texture SPRITESHEET = loadTexture("spritesheet.png");
	public static Texture ExampleSprite = loadTexture("example1.png");
	public static Texture RobotExample = loadTexture("robot1.png");
	public static Texture CROSSHAIR = loadTexture("crosshair.png");

	// Static values to keep track of the size of the Spritesheet
	public static int TILESIZE = 16;
	public static int SPRITESHEETWIDTH = SPRITESHEET.getImageWidth() / TILESIZE;
	public static int SPRITESHEETHEIGHT = SPRITESHEET.getImageHeight()
			/ TILESIZE;

	public static Texture loadTexture(String path) {

		try {
			return TextureLoader.getTexture("PNG", MyTextureLoader.class
					.getResourceAsStream("/me/block/texture/" + path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
