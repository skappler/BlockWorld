package me.block.cubes;

import me.block.util.MyTextureLoader;

import org.lwjgl.util.vector.Vector3f;

public class GrassBlock extends Block{

	public GrassBlock(Vector3f coord){
		super(coord);
		
		this.top = MyTextureLoader.GRASS_TOP;
		this.bottom = MyTextureLoader.GRASS_BOTTOM;
		this.side = MyTextureLoader.GRASS_SIDE;
		
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
