package me.block.level;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import me.block.cubes.Block;
import me.block.cubes.GrassBlock;
import me.block.util.MyTextureLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

/**
 * 
 * @author skappler
 * 
 *         A chunk stores the world data for a 16x16x16 block It is defined by
 *         x, y and z coordinates that are not dependent from the coordinates of
 *         the blocks in the chunk. The height is from 0 to 16, where the
 *         initial surface is 8. The coordinates are the position of the top
 *         left corner in chunks.
 * 
 */

public class Chunk {

	private Vector3f coordinates;
	private Block[] blocks; // Blockposition is 16*16*x + 16*y + z
	private int displayList;
	private Level level;

	public Chunk(int x, int y, Level l) {
		coordinates = new Vector3f(x, y, 0);
		level = l;
		blocks = new Block[16 * 16 * 16];

		loadStandardChunk();

		displayList = GL11.glGenLists(1);

		createDisplayList();
	}

	public void render() {

		GL11.glCallList(displayList);

	}

	public void update() {

	}

	public void loadStandardChunk() {
		for (int i = 0; i < 16; i++) { // x
			for (int j = 0; j < 16; j++) { // z
				for (int y = 0; y < 11; y++) { // y

					float x = i + coordinates.x*16;
					float z = j + coordinates.y*16;

					if (y < 8 ||y==10) {
						blocks[16 * 16 * i + 16 * y + j] = new GrassBlock(x, y,
								z);
					}

					if (y >= 8 && y < 10) {

						if (i == 0 || i == 15 || j == 0 || j == 15){
							blocks[16 * 16 * i + 16 * y + j] = new GrassBlock(
									x, y, z);
							blocks[16 * 16 * i + 16 * y + j].isTop(false);
						}
					}
					
					
				}
			}
		}

	}

	private void createDisplayList() {

		GL11.glNewList(displayList, GL11.GL_COMPILE);
		MyTextureLoader.SPRITESHEET.bind();

		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		GL11.glBegin(GL11.GL_QUADS);
		for (Block b : blocks) {
			if (b != null)
				b.create();
		}
		GL11.glEnd();
		GL11.glEndList();

	}
	
	public Block getBlock(int x, int y, int z){
		
		float cx = (float) Math.floor(x / 16);
		float cz = (float) Math.floor(z / 16);
		float cy = (float) Math.floor(y / 16);
		
		int ax = x % 16;
		int ay = y % 16;
		int az = z % 16;
		
		if(coordinates.x == cx && coordinates.z == cz){
			return blocks[16*16*ax + 16*ay + az];
		}
		
		Chunk tmp = level.getChunkAt(x, z);
		if(tmp == null)
			return null;
		return tmp.getBlock(x, y, z);
		
	}
	
	public Vector3f getCoordinates(){
		return coordinates;
	}
	
	

}
