package me.block.screen;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.LinkedList;

import me.block.Game;
import me.block.texture.MyTextureLoader;

public class Gui {

	private Screen screen;
	private LinkedList<GuiElement> elements;
	
	public Gui(Screen s){
		
		this.screen = s;
		this.elements = new LinkedList<GuiElement>();
		elements.add(new MiniMap(screen));
				
	}
	
	public void update(){
		
		for(GuiElement e : elements){
			e.update();
		}
		
	}
	
	public void render(){
		
		for(GuiElement e : elements){
			e.render();
		}
		
		if (Game.CROSSHAIR) {
			MyTextureLoader.CROSSHAIR.bind();
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glBegin(GL_QUADS);
			glTexCoord2d(0, 0);
			glVertex2i(Game.WIDTH / 2, Game.HEIGHT / 2);
			glTexCoord2d(1, 0);
			glVertex2i(Game.WIDTH / 2 + Game.CROSSHAIR_SIZE, Game.HEIGHT / 2);
			glTexCoord2d(1, 1);
			glVertex2i(Game.WIDTH / 2 + Game.CROSSHAIR_SIZE, Game.HEIGHT / 2 + Game.CROSSHAIR_SIZE);
			glTexCoord2d(0, 1);
			glVertex2i(Game.WIDTH / 2, Game.HEIGHT / 2 + Game.CROSSHAIR_SIZE);
			glEnd();
		}
		
	}
}
