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
	private Gui gui;
	
	public Screen(){
		
		player = new Player();
		level = new Level(player);
		player.setLevel(level);
		player.checkChunk();
		
		gui = new Gui(this);
	}
	
	public void renderGame(){
		level.render();
	}
	
	public void updateGame(){
		level.update();
		
		updateGui();
	}
	
	public void changeLevel(Level newLevel){
		this.level = newLevel;
		player.setLevel(newLevel);
	}
	
	public Level getLevel(){
		return level;
	}
	
	public Player getPlayer(){
		return player;
	}

	public void updateGui() {
		gui.update();
	}

	public void renderGui() {
		gui.render();
	}
}
