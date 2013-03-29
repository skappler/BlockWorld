package me.block.level;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import me.block.Game;
import me.block.entities.Entity;
import me.block.entities.ExampleEntity;
import me.block.entities.Player;
import me.block.util.MapFromText;

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
	private LinkedList<Entity> entities;

	private BufferedImage mapImage;

	public Level(Player p) {

		p.setLevel(this);
		
		this.player = p;
		this.chunks = new ArrayList<Chunk>();
		this.entities = new LinkedList<Entity>();
		
		this.entities.add(new ExampleEntity(18,5,6));
		this.entities.add(new ExampleEntity(16,5, 6));
		this.entities.add(new ExampleEntity(14,5, 6));

		for(Entity e : entities){
			e.setLevel(this);
		}
		
//		 loadTerrainLevel();
//		 loadEarlyLevel();
		loadLabyrinthLevel();

		
	}

	public void loadEarlyLevel(){

		 this.chunks.add(new Chunk(0,0,this,true));
		 this.chunks.add(new Chunk(0, -1, this,true));
		 this.chunks.add(new Chunk(-1,0,this,true));
		 this.chunks.add(new Chunk(0,1,this,true));
		 this.chunks.add(new Chunk(1,0,this,true));
		 this.chunks.add(new Chunk(0,-2,this,true));
		
		 for(Chunk c : chunks){
			 c.createDisplayList();
		 }

	}
	
	public void loadLabyrinthLevel() {
		InputStream in = this.getClass().getResourceAsStream(
				"/me/block/level/labyrinth.png");
		try {
			mapImage = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int chunkAmountX = mapImage.getWidth() / 16;
		int chunkAmountZ = mapImage.getHeight() / 16;

		for (int i = 0; i < chunkAmountX; i++) {
			for (int j = 0; j < chunkAmountZ; j++) {

				Chunk c = new Chunk(i, j, this);
				c.loadLabyrinth(mapImage);
				chunks.add(c);

			}
		}

		for (Chunk c : chunks) {
			c.createDisplayList();
		}

	}

	public void loadTerrainLevel() {

		InputStream in = this.getClass().getResourceAsStream(
				"/me/block/level/gray_gimp128.bmp");
		try {
			mapImage = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		float[][] heights = new float[mapImage.getWidth()][mapImage
				.getHeight()];

		for (int i = 0; i < mapImage.getWidth(); i++) {
			for (int j = 0; j < mapImage.getHeight(); j++) {
				heights[i][j] = (float) (mapImage.getRGB(i, j) & 0xff);
				if (heights[i][j] > 200f)
					heights[i][j] = 200f;

				if (heights[i][j] < 100f)
					heights[i][j] = 100f;

				heights[i][j] = (float) Math.floor(4f + (heights[i][j] - 100f)
						* (8f / 100f));
				// System.out.print(heights[i][j]+" ");
			}
			// System.out.println("");
		}

		int chunkAmountX = mapImage.getWidth() / 16;
		int chunkAmountZ = mapImage.getHeight() / 16;

		for (int i = 0; i < chunkAmountX; i++) {
			for (int j = 0; j < chunkAmountZ; j++) {

				Chunk c = new Chunk(i, j, this);
				c.loadTerrain(heights);
				// c.createDisplayList();
				chunks.add(c);

			}
		}

		for (Chunk c : chunks) {
			c.createDisplayList();
		}

	}

	public void render() {

	
		
		// Position the camera
		glRotatef(player.camera.rotation.x, 1, 0, 0);
		glRotatef(player.camera.rotation.y, 0, 1, 0);
		glRotatef(player.camera.rotation.z, 0, 0, 1);

		glTranslatef(-player.camera.position.x, -player.camera.position.y,
				-player.camera.position.z);

		player.render();


		
			
		// Render the world
		for (Chunk c : chunks) {

			c.render();
		}

		for(Entity e : entities){
			e.render();
		}
		
		
	}

	public Chunk getChunkAt(float x, float z) {

		Chunk ret = null;

		float cx = (float) Math.floor(x / 16);
		float cz = (float) Math.floor(z / 16);

		for (Chunk c : chunks) {
			if (c.getCoordinates().x == cx && c.getCoordinates().z == cz)
				ret = c;
		}

		return ret;

	}

	public void update() {

		player.update();
		
		if(Game.RENDER_NICE)
			Collections.sort(entities, new Comparator<Entity>() {
	
				@Override
				public int compare(Entity o1, Entity o2) {
					
					//o1 < o2 => -1
					float distanceO1 = Math.abs(Vector3f.sub(o1.position, player.position, new Vector3f()).length());
					float distanceO2 = Math.abs(Vector3f.sub(o2.position, player.position, new Vector3f()).length());
	
					if(distanceO1 < distanceO2){
						return 1;
					}else{
						return -1;
					}
					
				}
			});
		
		for(Entity e : entities){
			e.update();
		}
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public BufferedImage getMapImage(){
		return mapImage;
	}
}
