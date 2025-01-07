package com.mn.tetris;

import com.mn.tetris.service.ShapeService;
import com.mn.tetris.service.DisplayService;

public class GameRunner {
	public static void main(String[] args) {
		DisplayService b = new DisplayService();
		b.play();
		
//		ShapeService ss = new ShapeService();
//		ss.getShape();
	}
}
