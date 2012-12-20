package me.block.level;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import me.block.cubes.Block;
import me.block.cubes.GrassBlock;
import me.block.util.MyTextureLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

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

	public Chunk(int x, int z, Level l) {
		coordinates = new Vector3f(x, 0, z);
		level = l;
		blocks = new Block[16 * 16 * 16];

		loadExampleChunk1();

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

					float x = i + coordinates.x * 16;
					float z = j + coordinates.y * 16;

					if (y < 8 || y == 10) {
						blocks[16 * 16 * i + 16 * y + j] = new GrassBlock(x, y,
								z);
					}

					if (y >= 8 && y < 10) {

						if (i == 0 || i == 15 || j == 0 || j == 15) {
							blocks[16 * 16 * i + 16 * y + j] = new GrassBlock(
									x, y, z);
							blocks[16 * 16 * i + 16 * y + j].isTop(false);
						}
					}

				}
			}
		}

	}

	public void loadExampleChunk1(){
		
		for(int i = 0; i < 16; i++){ //x
			for(int j = 0; j < 16; j++){ //z
				for(int k = 0; k < 5; k++){ //y

					float x = i + coordinates.x * 16;
					float z = j + coordinates.z * 16;
					
					if(k == 4 && i == 6 && (j==4 || j == 3)){
						blocks[16*16*i+16*k+j] = null;

					}else{
						blocks[16*16*i+16*k+j] = new GrassBlock(x, k, z);
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
			if (b != null && checkVisible(b))
				b.create();

		}
		GL11.glEnd();
		GL11.glEndList();

	}

	/**
	 * 
	 * @param b
	 * @return true if the block is visible
	 */
	public boolean checkVisible(Block b) {

		Vector3f posB = b.getCoordinates();
		int x = (int) posB.x;
		int y = (int) posB.y;
		int z = (int) posB.z;

		// The bools below are true if the side is Visible
		boolean up = (getBlock(x, y + 1, z) == null);
		boolean down = (getBlock(x, y - 1, z) == null);
		boolean back = (getBlock(x, y, z - 1) == null);
		boolean front = (getBlock(x, y - 1, z + 1) == null);
		boolean right = (getBlock(x + 1, y, z) == null);
		boolean left = (getBlock(x - 1, y - 1, z) == null);

		// System.out.println(up+" "+down+" "+back+" "+front+" "+right+" "+left);

		return (up || down || back || front || left || right);
	}

	public Block getBlock(int x, int y, int z) {

		if (y < 0 || y > 16)
			return null;

		float cx = (float) Math.floor(x / 16);
		float cz = (float) Math.floor(z / 16);
		float cy = (float) Math.floor(y / 16);

		if (x < 0)
			cx--;
		if (z < 0)
			cz--;

		// System.out.println(cx+" "+cy+" "+cz);

		int ax = Math.abs(x % 16);
		int ay = Math.abs(y % 16);
		int az = Math.abs(z % 16);

		// System.out.println(ax+" "+ay+" "+az);

		if (coordinates.x == cx && coordinates.z == cz) {
			return blocks[16 * 16 * ax + 16 * ay + az];
		}

		Chunk tmp = level.getChunkAt(x, z);
		if (tmp == null)
			return null;
		return tmp.getBlock(x, y, z);

	}

	public Vector3f getCoordinates() {
		return coordinates;
	}

}
