package me.block.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class MapFromText {

	public static BufferedImage mapFromString(String str){
		
		BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < 64; i++){
			for(int j = 0; j < 64; j++){
				img.setRGB(i, j, 0xffffffff);
			}
		}
		Graphics g = img.getGraphics();
		g.setColor(Color.BLACK);
		g.setFont(new Font( "Arial", Font.PLAIN, 40 ));
		g.drawString(str, 5, 30);
		
		return img;
	}
	
}
