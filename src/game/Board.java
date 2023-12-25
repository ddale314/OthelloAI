package game;

import java.awt.Color;
import java.awt.Point;

import main.DrawingSurface;
import processing.core.PApplet;

public class Board {
	public static final int BOARD_DIM = 6;
	public static final int TILE_SIZE = 100;
	public static final int[][] DIRECTIONS = { {0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1} }; 
	private Tile[][] board;
	
	public Board() {
		board = new Tile[BOARD_DIM][BOARD_DIM];
		for (int i = 0; i < BOARD_DIM; i++) {
			for (int j = 0; j < BOARD_DIM; j++) {
				board[i][j] = new Tile();
			}
		}
	}
	
	public Point clickToIndex(Point p, float x, float y, float width, float height) {
		double verticalSpacing = width / board.length;
		double horizontalSpacing = width / board[0].length;
		return new Point((int) ((p.getY() - y) / verticalSpacing), (int) ((p.getX() - x) / horizontalSpacing));
	}
	
	public void placePiece(int i, int j, Piece p) {
		board[i][j].setPiece(p);
	}

	
	public boolean inBounds(int i, int j) {
		return (i >= 0 && j >= 0 && i < BOARD_DIM && j < BOARD_DIM);
	}
	
	public boolean isValidWord(String s) {
		for (int i = 0; i < DrawingSurface.WORDS.length; i++) {
			if (DrawingSurface.WORDS[i].equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean flipIfValid(int i, int j, Color curr, String s) {
		boolean flipOccurred = false;
		for (int d = 0; d < DIRECTIONS.length; d++) {
			boolean flip = false;
			boolean seen = false;
			int incX = DIRECTIONS[d][1];
			int incY = DIRECTIONS[d][0];
			int y = i, x = j;
			while (inBounds(y, x)) {
				if (y == i && x == j) {
					if (flip == true) {
						break;
					}
					else {
						x += incX;
						y += incY;
						continue;
					}
				}
				if (flip == true) {
					board[y][x].getPiece().setColor(curr);
				}
				if (board[y][x].getPiece() == null) {
					break;
				}
				else if (board[y][x].getPiece().getColor() == curr) {
					if (seen == true) {
						if (flip == false) {
							String s2 = board[y][x].getPiece().getCharacter();
							boolean validWord = (isValidWord(s+s2)|| isValidWord(s2+s));
							if (!validWord) {
								break;
							}
							flip = true;
							incX = -incX;
							incY = -incY;
						}
					}
					else {
						break;
					}
				}
				else {
					seen = true;
				}
				
				x += incX;
				y += incY;
			}
			if (flip == true) {
				flipOccurred = true;
			}
		}
		return flipOccurred && (board[i][j].getPiece() == null);
	}
	
	public void draw(PApplet surface) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < BOARD_DIM; i++) {
			for (int j = 0; j < BOARD_DIM; j++) {
				board[i][j].draw(surface, x, y);
				x += TILE_SIZE;
			}
			x = 0;
			y += TILE_SIZE;
		}
	}

}
