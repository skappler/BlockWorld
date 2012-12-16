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

/**
 * 
 * @author skappler
 * 
 *         A chunk stores the world data for a 16x16x16 block
 *         It is defined by x, y and z coordinates that are not 
 *         dependent from the coordinates of the blocks in
 *         the chunk. 
 *         The height is from 0 to 16, where the initial surface 
 *         is 8.
 *         
 */

public class Chunk {
	
	private Vector3f coordinates;
	private Block[] blocks;
	private int displayList;
	
	public Chunk(){
		blocks = new Block[16*16*16];
		
		loadStandardChunk();
		
		displayList = GL11.glGenLists(1);

		createDisplayList();
	}
	
	public void render(){
		
		GL11.glCallList(displayList);
		
	}
	
	public void update(){
		
	}
	
	public void loadStandardChunk(){
		for (int i = 0; i < 16; i++) { //x
			for (int j = 0; j < 16; j++) { //y
				for (int k = 0; k < 11; k++) { //z
					
					if(k < 8)
						blocks[16*16*i+16*j+k] = new GrassBlock(i, k, j);
					else{
						if((k<10) &&(i == 15 || i == 0) || (j == 15 || j== 0)){
							blocks[16*16*i+16*j+k]= new GrassBlock(i, k, j);
							blocks[16*16*i+16*j+k].isTop(false);
						}
						if(k == 10)
							blocks[16*16*i+16*j+k]= new GrassBlock(i, k, j);
					}
				}
			}
		}
		
		
	}
	
	private void createDisplayList(){
		
		GL11.glNewList(displayList, GL11.GL_COMPILE);
			MyTextureLoader.SPRITESHEET.bind();
			
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			GL11.glBegin(GL11.GL_QUADS);
				for(Block b : blocks){
					if(b != null)
						b.create();
				}
			GL11.glEnd();
		GL11.glEndList();
		
	}

}
