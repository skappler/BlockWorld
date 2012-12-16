package me.block.level;

import java.util.ArrayList;

import me.block.cubes.Block;
import me.block.cubes.GrassBlock;
import me.block.entities.Player;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * @author skappler
 * 
 *         This class represents a game level. It contains the world and the
 *         player information
 * 
 */

public class Level {

	private Player player;
	private ArrayList<Chunk> chunks;
	private Chunk currentChunk;
	

	public Level(Player p) {

		this.player = p;
		this.chunks = new ArrayList<Chunk>();
	
		this.chunks.add(new Chunk());

	}

	public void render() {

		// Position the camera
		glRotatef(player.camera.rotation.x, 1, 0, 0);
		glRotatef(player.camera.rotation.y, 0, 1, 0);
		glRotatef(player.camera.rotation.z, 0, 0, 1);

		glTranslatef(-player.camera.position.x,
				-player.camera.position.y,
				-player.camera.position.z);

		player.render();

		// Render the world
		for (Chunk c : chunks) {
			c.render();
		}

	}

	public void update() {

		player.update();

	}
}
