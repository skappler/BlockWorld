package me.block.cubes;

import me.block.texture.MyTextureLoader;

import org.lwjgl.util.vector.Vector3f;

public class StoneFloorBlock extends Block{

	public StoneFloorBlock(Vector3f coord){
		super(coord,MyTextureLoader.STONEFLOOR,MyTextureLoader.STONEFLOOR,MyTextureLoader.STONEFLOOR);
	}
	
	public StoneFloorBlock(float x, float y, float z){
		this(new Vector3f(x,y, z));
	}
	
	
}
