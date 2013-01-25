package me.block.level;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import me.block.Game;
import me.block.cubes.Block;
import me.block.cubes.GrassBlock;
import me.block.texture.MyTextureLoader;

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
	public Block[] blocks; // Blockposition is 16*16*x + 16*y + z
	private int displayList;
	private Level level;

	public Chunk(int x, int z, Level l) {
		coordinates = new Vector3f(x, 0, z);
		level = l;
		blocks = new Block[16 * 16 * 16];

//		loadExampleChunk1();

//		displayList = GL11.glGenLists(1);
//
//		createDisplayList();
	}

	public void render() {

		GL11.glCallList(displayList);

	}

	public void update() {

	}

	public void loadTerrain(float[][] terrain){
		
		int xBase = (int) (this.coordinates.x * 16);
		int zBase = (int) (this.coordinates.z * 16);
		
		
		for (int i = 0; i < 16; i++) { // x
			for (int j = 0; j < 16; j++) { // z
				for (int y = 0; y < 16; y++) { // y

					if(terrain[i+xBase][j+zBase] >= y )
						addBlock(i, y, j);

				}
			}
		}
		
		displayList = GL11.glGenLists(1);

		createDisplayList();
		
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
		
		displayList = GL11.glGenLists(1);

		createDisplayList();

	}

	public void loadExampleChunk1() {

		for (int i = 0; i < 16; i++) { // x
			for (int j = 0; j < 16; j++) { // z
				for (int k = 0; k < 5; k++) { // y

//					float x = i + coordinates.x * 16;
//					float z = j + coordinates.z * 16;

					if (k == 4 && i == 6 && (j == 4 || j == 3)) {
						blocks[16 * 16 * i + 16 * k + j] = null;

					} else {
						addBlock(i, k, j);
//						blocks[16 * 16 * i + 16 * k + j] = new GrassBlock(x, k,
//								z);
					}
				}
			}
			
			displayList = GL11.glGenLists(1);

			createDisplayList();
		}

//		blocks[16 * 16 * 5 + 16 * 5 + 5] = new GrassBlock(
//				coordinates.x * 16 + 5, 5, coordinates.z * 16 + 5);
//		blocks[16 * 16 * 5 + 16 * 6 + 5] = new GrassBlock(
//				coordinates.x * 16 + 5, 6, coordinates.z * 16 + 5);
//		blocks[16 * 16 * 5 + 16 * 5 + 6] = new GrassBlock(
//				coordinates.x * 16 + 5, 5, coordinates.z * 16 + 6);
		
		addBlock(5, 5, 5);
		addBlock(5, 6, 5);
		addBlock(5, 5, 6);
		
//		addBlock(2, 7, 2);
//		addBlock(2, 6, 3);

	}
	
	public void addBlock(int x, int y, int z){
		
		float xx = x + coordinates.x * 16;
		float yy = y;
		float zz = z + coordinates.z*16;
		
		blocks[16*16*x + 16*y + z] = new GrassBlock(xx, yy, zz);
		
	}
	
	public void removeBlock(int x, int y, int z){
		
		blocks[16*16*x + 16*y + z] = null;
		
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

		if(b == null)
			return false;
		
		Vector3f posB = b.getCoordinates();
//		System.out.println("position got " +posB.toString());
		
		int x = (int) Math.floor( posB.x);
		int y = (int) Math.floor( posB.y);
		int z = (int) Math.floor( posB.z);
		
		// The bools below are true if the side is Visible
		boolean up = (getBlock(x, y + 1, z) == null);
		boolean down = (getBlock(x, y - 1, z) == null);
		boolean back = (getBlock(x, y, z - 1) == null);
		boolean front = (getBlock(x, y - 1, z + 1) == null);
		boolean right = (getBlock(x + 1, y, z) == null);
		boolean left = (getBlock(x - 1, y - 1, z) == null);

//		if(getBlock(x, y, z - 1) != null)
//		System.out.println("neighbour "+z+" "+getBlock(x, y, z - 1).getCoordinates().toString());
//		 System.out.println(up+" "+down+" "+back+" "+front+" "+right+" "+left);

		return (up || down || back || front || left || right);
	}

	public Block getBlock(int x, int y, int z) {

		if (y < 0 || y > 16)
			return null;

		float xx = (float) x;
		float zz = (float) z;
		
		float cx = (float) Math.floor(xx / 16);
		float cz = (float) Math.floor(zz / 16);
//		float cy = (float) Math.floor(y / 16);		

		int ax = x % 16;//(int) Math.floor(x - coordinates.x*16);
		int ay = y;
		int az = z % 16;//(int) Math.floor(z - coordinates.z * 16);
		
		while(ax < 0){
			ax += 16;
		}
		while(az < 0){
			az += 16;
		}
		

		if (coordinates.x == cx && coordinates.z == cz) {
			try {
				return blocks[16 * 16 * ax + 16 * ay + az];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("ArrayIndexException! Not good, but okay");
				return null;
			}
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
