package game;

import java.awt.Color;

import processing.core.PApplet;

public class Tile {
	private Piece piece;
	
	public void setPiece(Piece p) {
		piece = p;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void draw(PApplet surface, int x, int y) {
		surface.fill(0, 255, 0);
		surface.stroke(0, 0, 0);
		surface.rect(x, y, Board.TILE_SIZE, Board.TILE_SIZE);
		surface.fill(0, 0, 0);
		if (piece != null) {
			piece.draw(surface, x, y);
		}
	}
}
