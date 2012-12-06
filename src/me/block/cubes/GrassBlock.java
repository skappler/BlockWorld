package me.block.cubes;

import org.lwjgl.util.vector.Vector3f;

public class GrassBlock extends Block{

	public GrassBlock(Vector3f coord){
		super(coord);
		
		this.top = 1;
		this.bottom = 2;
		this.side = 0;
		if(coord.y < 0)
			this.side = 2;
	}
	
	public GrassBlock(float x, float y, float z){
		this(new Vector3f(x,y, z));
	}
	
	public void isOverground(boolean b){
		if(b)
			side = 0;
		else
			side = 2;
	}
}
