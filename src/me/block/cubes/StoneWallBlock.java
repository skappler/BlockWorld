package me.block.cubes;

import me.block.texture.MyTextureLoader;

import org.lwjgl.util.vector.Vector3f;

public class StoneWallBlock extends Block{

	public StoneWallBlock(Vector3f coord){
		super(coord,MyTextureLoader.STONEWALL,MyTextureLoader.STONEWALL,MyTextureLoader.STONEWALL);
	}
	
	public StoneWallBlock(float x, float y, float z){
		this(new Vector3f(x,y, z));
	}
	
}
