package game;

import java.awt.Color;

import processing.core.PApplet;

public class Piece {
	private String character;
	private Color color;
	
	public Piece(String ch, Color col) {
		character = ch;
		color = col;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getCharacter() {
		return character;
	}
	
	public void draw(PApplet surface, int x, int y) {
		surface.fill(255, 255, 255);
		int xPos = x+Board.TILE_SIZE/2;
		int yPos = y+Board.TILE_SIZE/2;
		surface.stroke(color.getRed(), color.getGreen(), color.getBlue());
		surface.strokeWeight(5);
		surface.circle(xPos, yPos, 3*Board.TILE_SIZE/4);
		surface.fill(0, 0, 0);
		surface.textAlign(PApplet.CENTER, PApplet.CENTER);
		surface.text(character, xPos, yPos);
		surface.stroke(0, 0, 0);
		surface.strokeWeight(1);
	}
}
