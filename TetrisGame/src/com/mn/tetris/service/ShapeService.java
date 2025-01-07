package com.mn.tetris.service;

import java.util.ArrayList;
import java.util.Random;

import com.mn.tetris.model.Coordinate;

public class ShapeService {

	public int[][] getShape() {
		// create shape
		int[][] shape = new int[4][4];
		Random random = new Random();
		try {
			// make an initial coordinate
			int x = random.nextInt(4);
			int y = random.nextInt(4);
			shape[x][y] = 1;
			// get possible spots	
			for(int i=0; i<3; i++) {
				ArrayList<Coordinate> arrCoor = getNextSpots(x, y, shape);
				int nextSpot = random.nextInt(arrCoor.size());
				x = arrCoor.get(nextSpot).getX();
				y = arrCoor.get(nextSpot).getY();
				shape[x][y]=1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return shape;
		}
	}

	/**
	 * find available coordinates
	 * 
	 * @param x     current raw
	 * @param y     current column
	 * @param shape current shape
	 * @return available coordinates
	 */
	private ArrayList<Coordinate> getNextSpots(int x, int y, int[][] shape) {
		ArrayList<Coordinate> arrCoor = new ArrayList<Coordinate>();
		try {
			// check up
			if (x - 1 >= 0 && shape[x - 1][y] == 0) {
				arrCoor.add(new Coordinate(x - 1, y));
			}
			// check left
			if (y - 1 >= 0 && shape[x][y - 1] == 0) {
				arrCoor.add(new Coordinate(x, y - 1));
			}
			// check down
			if (x + 1 < 4 && shape[x + 1][y] == 0) {
				arrCoor.add(new Coordinate(x + 1, y));
			}
			// check right
			if (y + 1 < 4 && shape[x][y + 1] == 0) {
				arrCoor.add(new Coordinate(x, y + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return arrCoor;
		}
	}
}
