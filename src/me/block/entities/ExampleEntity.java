package me.block.entities;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

public class ExampleEntity extends Entity{

	private BufferedImage texture;
	private Texture tex;
	private int angle=0;
	
	public ExampleEntity(){
		
		InputStream in = this.getClass().getResourceAsStream(
				"/me/block/entities/example1.png");
		try {
			texture = ImageIO.read(in);
			tex = BufferedImageUtil.getTexture("EXAMPLE1", texture);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		position = new Vector3f();
		rotation = new Vector3f();
		
		position.x = 20f;
		position.y = 5f;
		position.z = 6f;
		
	}
	
	public ExampleEntity(int x, int z){
		this();
		position.x = x;
		position.z = z;
	}
	
	@Override
	public void update() {

		Vector2f view = new Vector2f();
		view.x = level.getPlayer().getViewVector().x;
		view.y  = level.getPlayer().getViewVector().z;
		float s = Math.signum(view.y);
		
		angle = (int) (s*Math.toDegrees(Vector2f.angle(view, new Vector2f(1f, 0f))));
		Display.setTitle(angle+" "+view);
		
	}

	@Override
	public void render() {
		
		tex.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		GL11.glPushMatrix();
				GL11.glTranslatef(position.x, position.y, position.z);
		GL11.glRotatef(360 - angle, 0f, 1f, 0f);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex3f(0f,0f,0f -0.45f);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex3f(0f,0f + 1.8f, 0f -0.45f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex3f(0f,0f +1.8f, 0f+0.45f);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex3f(0f,0f,0f +0.45f);			
		GL11.glEnd();
	
		GL11.glPopMatrix();
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
