package com.mn.tetris.service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DisplayService extends JFrame implements KeyListener {
	private JPanel panel;
	private static JLabel[][] grid;
	final static int ROWS = 24;
	final static int COLS = 12;
	JFrame frame;
	int[][] shape;
	private static int squareRow; // Initial row position of the square
	private static int squareCol; // Initial column position of the square
	private boolean isSettled;
	private ShapeService ss;

	private void display() {
		panel = new JPanel();
		panel.setLayout(new GridLayout(ROWS, COLS));
		grid = new JLabel[ROWS][COLS];
		squareRow = 2;
		squareCol = initalSpot();
		// Add components to the grid
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				JLabel cell = new JLabel("", SwingConstants.CENTER);
				cell.setOpaque(true); // Make the label background visible
				cell.setBackground(Color.WHITE); // Default cell color
				cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border to see the grid
				grid[row][col] = cell;
				panel.add(cell);
			}
		}

		ss = new ShapeService();
		shape = ss.getShape();

		// Display the shape on the grid
		updateShapeOnGrid(shape);
		addKeyListener(this);
		// Add the panel to the frame
		add(panel);
		setVisible(true);
	}

	private void configFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// GUI title
		setTitle("Game Tetris");
		setFocusable(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screenSize.width * 0.3); // 30% of screen width
		int height = (int) (screenSize.height * 0.8); // 80% of screen height
		this.setSize(width, height);
	}

	private static void updateShapeOnGrid(int[][] shape) {
//		  Reset all cells to white
		try {
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					grid[row][col].setBackground(Color.WHITE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Loop through the shape array and color the appropriate cells
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (shape[row][col] == 1) {
					if (squareRow >= 0 && squareRow + row < ROWS && squareCol >= 0 && squareCol + col < COLS) {
						grid[squareRow + row][squareCol + col].setBackground(Color.RED);
					}
				}
			}
		}
	}

	private boolean canMove(int[][] shape, int rowOffset, int colOffset) {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (shape[row][col] == 1) { // If this cell is part of the shape
					int newRow = squareRow + row + rowOffset;
					int newCol = squareCol + col + colOffset;

					// Check if the new position is out of bounds
					if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private int initalSpot() {
		Random random = new Random();
		return random.nextInt(COLS - 4);
	}
	
	private void settleShape() {
		isSettled = true; // Mark the shape as settled
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (shape[row][col] == 1) { // Part of the shape
					int gridRow = squareRow + row;
					int gridCol = squareCol + col;

					// Ensure within bounds
					if (gridRow >= 0 && gridRow < ROWS && gridCol >= 0 && gridCol < COLS) {
						grid[gridRow][gridCol].setBackground(Color.RED); // Mark as settled

					}
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int key = e.getKeyCode();
			if (isSettled) {
				return;
			}
			switch (key) {
			case KeyEvent.VK_DOWN:
				if (canMove(shape, 1, 0)) {
					squareRow++;// Move down
				} else {
					settleShape();
				}

				break;
			case KeyEvent.VK_RIGHT:
				if (!isSettled && canMove(shape, 0, 1))
					squareCol++; // Move left
				break;
			case KeyEvent.VK_LEFT:
				if (!isSettled && canMove(shape, 0, -1))
					squareCol--; // Move right
				break;
			case KeyEvent.VK_X:
				System.exit(0);
				break;
			}
			updateShapeOnGrid(shape);
		} catch (Exception excep) {
			excep.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void play() {
		configFrame();
		display();
	}
}
