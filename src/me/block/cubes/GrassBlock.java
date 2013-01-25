package me.block.cubes;

import me.block.texture.MyTextureLoader;

import org.lwjgl.util.vector.Vector3f;

public class GrassBlock extends Block{

	public GrassBlock(Vector3f coord){
		super(coord,MyTextureLoader.GRASS_TOP,MyTextureLoader.GRASS_BOTTOM,MyTextureLoader.GRASS_SIDE);
				
	}
	
	public GrassBlock(float x, float y, float z){
		this(new Vector3f(x,y, z));
	}
	
	public void isTop(boolean b){
		if(b)
			side = MyTextureLoader.GRASS_SIDE;
		else{
			side = MyTextureLoader.GRASS_BOTTOM;
			top = MyTextureLoader.GRASS_BOTTOM;
		}
	}
}
