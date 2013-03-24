package me.block.screen;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.LinkedList;

import me.block.Game;

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
			glBegin(GL_QUADS);
			glVertex2i(Game.WIDTH / 2, Game.HEIGHT / 2);
			glVertex2i(Game.WIDTH / 2 + Game.CROSSHAIR_SIZE, Game.HEIGHT / 2);
			glVertex2i(Game.WIDTH / 2 + Game.CROSSHAIR_SIZE, Game.HEIGHT / 2 + Game.CROSSHAIR_SIZE);
			glVertex2i(Game.WIDTH / 2, Game.HEIGHT / 2 + Game.CROSSHAIR_SIZE);
			glEnd();
		}
		
	}
}
