package me.block.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

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

	// Static Textures to prevent using too much memory
	// The single Sprites can be accesed by their position 
	// starting at 0
	public static Texture SPRITESHEET = loadTexture("res/texture/spritesheet.png");

	// Static values to keep track of the size of the Spritesheet
	public static int TILESIZE = 16;
	public static int SPRITESHEETWIDTH = SPRITESHEET.getImageWidth() / TILESIZE;
	public static int SPRITESHEETHEIGHT = SPRITESHEET.getImageHeight()
			/ TILESIZE;

	public static Texture loadTexture(String path) {

		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return null;
	}

}
