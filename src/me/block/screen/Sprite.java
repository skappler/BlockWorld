package me.block.screen;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.BufferedImageUtil;

import me.block.entities.Entity;
import me.block.entities.Player;

//TODO add animation / spritesheets


public class Sprite {

	private int angle = 0;
	private Texture tex;
	private Entity entity;
	private float width;
	private float height;
	private int frames;
	private int currentFrame = 1;
	
	public Sprite(Texture t, Entity ent, float width, float height, int frames){
		
		this.entity = ent;
		this.width = width;
		this.height = height;
		this.frames = frames;
		this.tex = t;
		
				
	}
	
	public void update(Player p){
		Vector2f view = new Vector2f();
		view.x = p.getViewVector().x;
		view.y  = p.getViewVector().z;
		float s = Math.signum(view.y);
		
		angle = (int) (s*Math.toDegrees(Vector2f.angle(view, new Vector2f(1f, 0f))));
	}
	
	public void render(Vector3f position){
		
		tex.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		GL11.glPushMatrix();
				GL11.glTranslatef(position.x, position.y, position.z);
		GL11.glRotatef(360 - angle, 0f, 1f, 0f);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glNormal3f(-1, 0,0);
			GL11.glVertex3f(0f,0f,0f -width/2f);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex3f(0f,0f + height, 0f -width/2f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex3f(0f,0f + height, 0f+ width/2f);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex3f(0f,0f,0f +width/2f);			
		GL11.glEnd();
	
		GL11.glPopMatrix();
		
	}
	
	public int getAngle(){
		return angle;
	}
	
}
