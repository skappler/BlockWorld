package me.block.screen;

import me.block.entities.Player;
import me.block.level.Level;

/**
 * 
 * @author skappler
 * 
 * The screen class holds the whole gamegraphics, except the menus.
 */

public class Screen {

	Level level;
	
	public Screen(){
		
		level = new Level(new Player());
		
	}
	
	public void render(){
		level.render();
	}
	
	public void update(){
		level.update();
	}
	
}
