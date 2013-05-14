package me.block.screen;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.image.BufferedImage;
import java.io.IOException;

import me.block.Game;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

public class MiniMap extends GuiElement{

	private Screen screen;
	private Texture mapTex;
	private BufferedImage map;
	private BufferedImage origMap;

	private int oldX;
	private int oldZ;
	
	
	public MiniMap(Screen s){
		
		this.screen = s;
		BufferedImage mapTemp = screen.getLevel().getMapImage();
		
		this.origMap = new BufferedImage(mapTemp.getColorModel(),mapTemp.copyData(null),mapTemp.isAlphaPremultiplied(),null);
		this.map = new BufferedImage(mapTemp.getColorModel(),mapTemp.copyData(null),mapTemp.isAlphaPremultiplied(),null);

		oldX = 0;
		oldZ = 0;
	}
	
	@Override
	public void update() {

		int x = (int) Math.floor(screen.getPlayer().getPosition().x);
		int z = (int) Math.floor(screen.getPlayer().getPosition().z);
		
		try {
			map.setRGB(oldX,oldZ, origMap.getRGB(oldX, oldZ));
			map.setRGB(x,z, 0xff0000);

			mapTex = BufferedImageUtil.getTexture("BLANK", map);
		} catch (IOException e) {
			e.printStackTrace();
		} catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		oldX = x;
		oldZ = z;
		
	}

	@Override
	public void render() {
		
		if (Game.MINIMAP) {
			mapTex.bind();
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glPushMatrix();
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2i(0, 0);
				glTexCoord2f(1f, 0f);
				glVertex2i(192, 0);
				glTexCoord2f(1f, 1f);
				glVertex2i(192, 192);
				glTexCoord2f(0f, 1f);
				glVertex2i(0, 192);
			glEnd();
			glPopMatrix();
		}
		
		
		
	}

}
