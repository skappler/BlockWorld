package me.block.entities;

import me.block.level.Chunk;
import me.block.level.Level;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 * 
 * @author skappler
 * 
 *         This class represents every kind of entity
 * 
 */

public abstract class Entity {

	public Level level;
	public Chunk currentChunk;

	public Vector3f rotation;
	public Vector3f position;

	public float speed;
	public float height;

	public Entity() {

		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);

	}

	public abstract void update();

	public abstract void render();

	public void setLevel(Level l) {
		this.level = l;
	}

	public void checkChunk() {

		float cx = (float) Math.floor(position.x / 16);
		float cz = (float) Math.floor(position.z / 16);

		// currentChunk = level.getChunkAt(position.x, position.z);

		if (currentChunk != null && cx == currentChunk.getCoordinates().x
				&& cz == currentChunk.getCoordinates().z)
			return;

		currentChunk = level.getChunkAt(position.x, position.z);

//		 if (currentChunk == null)
//		 Display.setTitle("NULL");
//		 else
//		 Display.setTitle(currentChunk.getCoordinates().x + " "
//		 + currentChunk.getCoordinates().z);

	}
}
