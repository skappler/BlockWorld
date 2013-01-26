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

	private Level level;
	private Player player;
	
	public Screen(){
		
		player = new Player();
		level = new Level(player);
		player.setLevel(level);
		player.checkChunk();
	}
	
	public void render(){
		level.render();
	}
	
	public void update(){
		level.update();
	}
	
	public void changeLevel(Level newLevel){
		this.level = newLevel;
		player.setLevel(newLevel);
	}
	
	public Player getPlayer(){
		return player;
	}
}
