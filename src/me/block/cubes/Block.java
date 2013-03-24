package me.block.cubes;

import me.block.texture.MyTextureLoader;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.geom.Rectangle;

import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * @author skappler
 * 
 *         This is the superclass for all kinds of blocks. It holds all
 *         attributes and methodes provided for a block
 * 
 *         Every block has the normed size 1x1x1. The given coordinate is the
 *         top back left corner of the cube
 */

public class Block {

	// The block ID identifies a block distinct
	public static final byte GRASS_ID = 0x01;
	public static final byte STONEWALL_ID = 0x02;
	public static final byte STONEFLOOR_ID = 0x03;

	// The texture coordinates of the block starting at 0 up to (width *
	// height)-1
	protected float top = 0;
	protected float bottom = 0;
	protected float side = 0;

	private Vector3f coordinate;
	private boolean isSolid = false;
	
	private float x;
	private float y;
	private float z;

	
	private float delta;

	public Block(Vector3f coord, int t, int b, int s) {

		this.coordinate = coord;
		this.top = t;
		this.bottom = b;
		this.side = s;
		
		x = coordinate.x;
		y = coordinate.y;
		z = coordinate.z;
		
		delta = (float) (1.0 / MyTextureLoader.SPRITESHEETWIDTH);


		
	}

	public Block(float x, float y, float z,int t, int b, int s) {

		this(new Vector3f(x, y, z),t,b,s);

	}

	public void render() {
		float x = coordinate.x;
		float y = coordinate.y;
		float z = coordinate.z;

		float topTextureX = top / MyTextureLoader.SPRITESHEETWIDTH;
		float topTextureY = top % MyTextureLoader.SPRITESHEETWIDTH;

		float bottomTextureX = bottom / MyTextureLoader.SPRITESHEETWIDTH;
		float bottomTextureY = bottom % MyTextureLoader.SPRITESHEETWIDTH;

		float sideTextureX = side / MyTextureLoader.SPRITESHEETWIDTH;
		float sideTextureY = side % MyTextureLoader.SPRITESHEETWIDTH;

		float delta = (float) (1.0 / MyTextureLoader.SPRITESHEETWIDTH);

		glBegin(GL_QUADS);

		// TOP
		glNormal3f(0f, 1f, 0f);
		glTexCoord2f(topTextureX, topTextureY);
		glVertex3f(x, y, z);
		glTexCoord2f(topTextureX, topTextureY + delta);
		glVertex3f(x, y, z + 1);
		glTexCoord2f(topTextureX + delta, topTextureY + delta);
		glVertex3f(x + 1, y, z + 1);
		glTexCoord2f(topTextureX + delta, topTextureY);
		glVertex3f(x + 1, y, z);

		// BOTTOM
		glNormal3f(0f, -1f, 0f);
		glTexCoord2f(bottomTextureX, bottomTextureY);
		glVertex3f(x, y - 1, z);
		glTexCoord2f(bottomTextureX, bottomTextureY + delta);
		glVertex3f(x, y - 1, z + 1);
		glTexCoord2f(bottomTextureX + delta, bottomTextureY + delta);
		glVertex3f(x + 1, y - 1, z + 1);
		glTexCoord2f(bottomTextureX + delta, bottomTextureY);
		glVertex3f(x + 1, y - 1, z);

		// BACK
		glNormal3f(0, 0, -1);
		glTexCoord2f(sideTextureX, sideTextureY);
		glVertex3f(x, y, z);
		glTexCoord2f(sideTextureX + delta, sideTextureY);
		glVertex3f(x + 1, y, z);
		glTexCoord2f(sideTextureX + delta, sideTextureY + delta);
		glVertex3f(x + 1, y - 1, z);
		glTexCoord2f(sideTextureX, sideTextureY + delta);
		glVertex3f(x, y - 1, z);

		// FRONT
		glNormal3f(0, 0, 1);
		glTexCoord2f(sideTextureX, sideTextureY + delta);
		glVertex3f(x, y - 1, z + 1);
		glTexCoord2f(sideTextureX + delta, sideTextureY + delta);
		glVertex3f(x + 1, y - 1, z + 1);
		glTexCoord2f(sideTextureX + delta, sideTextureY);
		glVertex3f(x + 1, y, z + 1);
		glTexCoord2f(sideTextureX, sideTextureY);
		glVertex3f(x, y, z + 1);

		// LEFT
		glNormal3f(-1, 0, 0);
		glTexCoord2f(sideTextureX, sideTextureY);
		glVertex3f(x, y, z);
		glTexCoord2f(sideTextureX, sideTextureY + delta);
		glVertex3f(x, y - 1, z);
		glTexCoord2f(sideTextureX + delta, sideTextureY + delta);
		glVertex3f(x, y - 1, z + 1);
		glTexCoord2f(sideTextureX + delta, sideTextureY);
		glVertex3f(x, y, z + 1);

		// RIGHT
		glNormal3f(1, 0, 0);
		glTexCoord2f(sideTextureX + delta, sideTextureY);
		glVertex3f(x + 1, y, z + 1);
		glTexCoord2f(sideTextureX + delta, sideTextureY + delta);
		glVertex3f(x + 1, y - 1, z + 1);
		glTexCoord2f(sideTextureX, sideTextureY + delta);
		glVertex3f(x + 1, y - 1, z);
		glTexCoord2f(sideTextureX, sideTextureY);
		glVertex3f(x + 1, y, z);

		glEnd();

	}

	public void create() {
		
		float topTextureX =(top % MyTextureLoader.SPRITESHEETWIDTH)*delta;
		float topTextureY =(float) Math.floor(top / MyTextureLoader.SPRITESHEETHEIGHT)*delta;
		
		
		float bottomTextureX = (bottom % MyTextureLoader.SPRITESHEETWIDTH)*delta;
		float bottomTextureY = (float)Math.floor(bottom / MyTextureLoader.SPRITESHEETHEIGHT)*delta;

		float sideTextureX = (side % MyTextureLoader.SPRITESHEETWIDTH)*delta;
		float sideTextureY =(float) Math.floor(side / MyTextureLoader.SPRITESHEETHEIGHT)*delta;


		// if(x == 15f && z == 15f){
		// topTextureX = bottom / MyTextureLoader.SPRITESHEETWIDTH;
		// topTextureY = bottom % MyTextureLoader.SPRITESHEETWIDTH;
		// }

		// TOP
		glNormal3f(0f, 1f, 0f);
		glTexCoord2f(topTextureX, topTextureY);
		glVertex3f(x, y, z);
		glTexCoord2f(topTextureX, topTextureY + delta);
		glVertex3f(x, y, z + 1);
		glTexCoord2f(topTextureX + delta, topTextureY + delta);
		glVertex3f(x + 1, y, z + 1);
		glTexCoord2f(topTextureX + delta, topTextureY);
		glVertex3f(x + 1, y, z);

		// BOTTOM
		glNormal3f(0f, -1f, 0f);
		glTexCoord2f(bottomTextureX, bottomTextureY);
		glVertex3f(x, y - 1, z);
		glTexCoord2f(bottomTextureX, bottomTextureY + delta);
		glVertex3f(x, y - 1, z + 1);
		glTexCoord2f(bottomTextureX + delta, bottomTextureY + delta);
		glVertex3f(x + 1, y - 1, z + 1);
		glTexCoord2f(bottomTextureX + delta, bottomTextureY);
		glVertex3f(x + 1, y - 1, z);

		// BACK
		glNormal3f(0, 0, -1);
		glTexCoord2f(sideTextureX, sideTextureY);
		glVertex3f(x, y, z);
		glTexCoord2f(sideTextureX + delta, sideTextureY);
		glVertex3f(x + 1, y, z);
		glTexCoord2f(sideTextureX + delta, sideTextureY + delta);
		glVertex3f(x + 1, y - 1, z);
		glTexCoord2f(sideTextureX, sideTextureY + delta);
		glVertex3f(x, y - 1, z);

		// FRONT
		glNormal3f(0, 0, 1);
		glTexCoord2f(sideTextureX, sideTextureY + delta);
		glVertex3f(x, y - 1, z + 1);
		glTexCoord2f(sideTextureX + delta, sideTextureY + delta);
		glVertex3f(x + 1, y - 1, z + 1);
		glTexCoord2f(sideTextureX + delta, sideTextureY);
		glVertex3f(x + 1, y, z + 1);
		glTexCoord2f(sideTextureX, sideTextureY);
		glVertex3f(x, y, z + 1);

		// LEFT
		glNormal3f(-1, 0, 0);
		glTexCoord2f(sideTextureX, sideTextureY);
		glVertex3f(x, y, z);
		glTexCoord2f(sideTextureX, sideTextureY + delta);
		glVertex3f(x, y - 1, z);
		glTexCoord2f(sideTextureX + delta, sideTextureY + delta);
		glVertex3f(x, y - 1, z + 1);
		glTexCoord2f(sideTextureX + delta, sideTextureY);
		glVertex3f(x, y, z + 1);

		// RIGHT
		glNormal3f(1, 0, 0);
		glTexCoord2f(sideTextureX + delta, sideTextureY);
		glVertex3f(x + 1, y, z + 1);
		glTexCoord2f(sideTextureX + delta, sideTextureY + delta);
		glVertex3f(x + 1, y - 1, z + 1);
		glTexCoord2f(sideTextureX, sideTextureY + delta);
		glVertex3f(x + 1, y - 1, z);
		glTexCoord2f(sideTextureX, sideTextureY);
		glVertex3f(x + 1, y, z);

	}

	public void setSolid(boolean b) {
		this.isSolid = b;
	}

	public boolean isSolid() {
		return this.isSolid;
	}

	public void isTop(boolean b) {

	}
	
	public Rectangle getBounds(){
		return new Rectangle(coordinate.x, coordinate.z, 1, 1);
	}

	public Vector3f getCoordinates() {
		return this.coordinate;
	}
	
	
}
