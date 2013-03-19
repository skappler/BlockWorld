package me.block.screen;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.block.Game;
import me.block.util.MapFromText;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

public class Gui {

	private BufferedImage map;
	private int[] cols; //x*width+y
	private Texture mapTex;
	private float oldX;
	private float oldZ;
	
	public Gui(){
		InputStream in = this.getClass().getResourceAsStream(
				"/me/block/level/labyrinth.png");
		try {
			map = ImageIO.read(in);
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		map = MapFromText.mapFromString("AAA");

		cols = new int[map.getHeight()*map.getWidth()];
		
		for(int i = 0; i < map.getHeight();i++){
			for(int j = 0; j < map.getWidth(); j++){
				cols[i*map.getHeight()+j] = map.getRGB(i, j);
			}
		}
		
		oldX = 0f;
		oldZ = 0f;
		
	}
	
	public void update(float x, float z){
		
		map.setRGB((int)oldX , (int)oldZ,cols[(int)oldX*map.getHeight()+(int)oldZ]);	
		
		oldX = x;
		oldZ = z;
		
		
		map.setRGB((int)Math.floor(x),(int) Math.floor(z), 0xff0000);
		try {
			mapTex = BufferedImageUtil.getTexture("BLANK", map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void render(){
		
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
		
		if (Game.CROSSHAIR) {
			glBegin(GL_QUADS);
			glVertex2i(Game.WIDTH / 2, Game.HEIGHT / 2);
			glVertex2i(Game.WIDTH / 2 + 8, Game.HEIGHT / 2);
			glVertex2i(Game.WIDTH / 2 + 8, Game.HEIGHT / 2 + 8);
			glVertex2i(Game.WIDTH / 2, Game.HEIGHT / 2 + 8);
			glEnd();
		}
	}
}
