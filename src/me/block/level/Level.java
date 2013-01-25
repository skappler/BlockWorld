package me.block.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
	public ArrayList<Chunk> chunks;
	private Chunk currentChunk;
	
	BufferedImage heightMap;
	
	public Level(Player p) {

		this.player = p;
		this.chunks = new ArrayList<Chunk>();
	
//		loadTerrainLevel();
		
		loadLabyrinthLevel();
		
//		
//		this.chunks.add(new Chunk(0,0,this));
//		this.chunks.add(new Chunk(0, -1, this));
//		this.chunks.add(new Chunk(-1,0,this));
//		this.chunks.add(new Chunk(0,1,this));
//		this.chunks.add(new Chunk(1,0,this));
//		this.chunks.add(new Chunk(0,-2,this));
//		
//		for(int i = 0; i < 10;i++){
//			for(int j = 0; j < 10;j++){
////				this.chunks.add(new Chunk(i,j,this));
//			}
//		}
		
		
	}
	
	public void loadLabyrinthLevel(){
		InputStream in = this.getClass().getResourceAsStream("/me/block/level/labyrinth.png");
		try {
			heightMap = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int chunkAmountX = heightMap.getWidth() / 16;
		int chunkAmountZ = heightMap.getHeight() / 16;
		
		for(int i = 0; i < chunkAmountX;i++){
			for(int j = 0; j < chunkAmountZ;j++){
				
				Chunk c = new Chunk(i, j, this);
				c.loadLabyrinth(heightMap);
				chunks.add(c);
				
			}
		}
		
		for(Chunk c : chunks){
			c.createDisplayList();
		}

	}
	
	public void loadTerrainLevel(){
		
		InputStream in = this.getClass().getResourceAsStream("/me/block/level/gray_gimp128.bmp");
		try {
			heightMap = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		float[][] heights = new float[heightMap.getWidth()][heightMap.getHeight()];
		
		for(int i = 0; i < heightMap.getWidth();i++){
			for(int j = 0; j < heightMap.getHeight();j++){
				heights[i][j] = (float)(heightMap.getRGB(i,j)&0xff);
				if(heights[i][j] > 200f)
					heights[i][j] = 200f;
				
				if(heights[i][j] < 100f)
					heights[i][j] = 100f;
				
				heights[i][j] =(float) Math.floor( 4f + (heights[i][j] - 100f) * (8f/100f));
//				System.out.print(heights[i][j]+" ");
			}
//			System.out.println("");
		}
		
		int chunkAmountX = heightMap.getWidth() / 16;
		int chunkAmountZ = heightMap.getHeight() / 16;
		
		for(int i = 0; i < chunkAmountX;i++){
			for(int j = 0; j < chunkAmountZ;j++){
				
				Chunk c = new Chunk(i, j, this);
				c.loadTerrain(heights);
//				c.createDisplayList();
				chunks.add(c);
				
			}
		}
		
		for(Chunk c : chunks){
			c.createDisplayList();
		}
		
		
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
	
	public Chunk getChunkAt(float x, float z){
		
		Chunk ret = null;
		
		float cx = (float) Math.floor( x / 16);
		float cz = (float) Math.floor( z / 16);
				
		for(Chunk c : chunks){
			if(c.getCoordinates().x == cx && c.getCoordinates().z == cz)
				ret = c;
		}
		
		return ret;
		
	}

	public void update() {
		
		player.update();

	}
}
