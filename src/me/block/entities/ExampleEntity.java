package me.block.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.block.screen.Sprite;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.geom.Rectangle;

public class ExampleEntity extends Entity{

	private BufferedImage texture;
	private Sprite sprite;
	
	public ExampleEntity(){
		
		InputStream in = this.getClass().getResourceAsStream(
				"/me/block/entities/example1.png");
		try {
			texture = ImageIO.read(in);
			sprite = new Sprite(texture,this,0.9f,1.8f,1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		position = new Vector3f();
		rotation = new Vector3f();
		
		position.x = 20f;
		position.y = 5f;
		position.z = 6f;
		
	}
	
	public ExampleEntity(int x,int y, int z){
		this();
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	@Override
	public void update() {

		sprite.update(level.getPlayer());
		rotation.y = sprite.getAngle();
	}

	@Override
	public void render() {
		
		sprite.render(position);
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
