package main;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import game.Board;
import game.Piece;
import processing.core.PApplet;
import processing.core.PFont;

public class DrawingSurface extends PApplet {
	public static final String[] CHARACTERS = {"本", "下", "金", "早", "名", "雨", "手", "足", "日", "右", "左", "文", "上", "木",
			"目", "気", "人", "火", "口", "天", "車", "中", "年", "女", "男", "水", "土", "学", "小", "月", "力", "山", "大", "入", "出", "空"};
	public static final String[] WORDS = {"本金", "本名", "本手", "本日", "本文", "本木", "本目", "本気", "本人", "本天", "本中", "本年", "本水", "本土", "本学", "本月", "本山", "手本", "大本", 
			"下金", "下名", "下手", "下足", "下日", "下文", "下木", "下目", "下人", "下火", "下口", "下天", "下車", "下中", "下女", "下男", "下水", "下土", "下学", "下山",
			"金文", "金木", "金目", "金気", "金人", "金口", "金車", "金力", "金山", "手水", "手中", "手口", "手下", "手足", "手金", "手早", "大手", "上手", "山手", "名手", "人手", 
			"小手", "女手", "空手", "早手", "入手", "男手", "左手", "右手", "土手", "大水", "大口", "大山", "大名", "大人", "大足", "大本", "大気", "大小", "大女", "大火", "大雨", 
			"大学", "大空", "大金", "大目", "大男", "大木", "中小", "中火", "中年", "中学", "中日", "手中", "水中", "口中", "山中", "女中", "火中", "年中", "車中", "雨中", "空中", "文中", "日中",
			"水上", "水気", "水車", "水力", "水月", "山水", "上水", "下水", "名水", "出水", "雨水", "早水", "入水", "上手", "上下", "上気", "上空", "上目", "口上", "山上", "年上", "車上", "目上", "左上", "右上", "天上",
			"下山", "下足", "下火", "下車", "足下", "年下", "目下", "右下", "左下", "天下", "月下", "山手", "山水", "山中", "山上", "山女", "山車", "山男", "山月", "大山", "名山", "本山", "火山", "入山",
			"口中", "口上", "口火", "口車", "口金", "口早", "手口", "大口", "人口", "出口", "小口", "火口", "早口", "入口", "目口", "人名", "人出", "人気", "人力", "人目", "人文", "名人", "小人", "女人", "文人",
			"名車", "名目", "名文", "名月", "名木", "車名", "学名", "目力", "空目", "右目", "左目", "木目", "足早", "出足", "小足", "雨足", "早足", "右足", "左足", "土足", "小口", "小女",
			"小火", "小雨", "小学", "小金", "小男", "土木", "火気", "火車", "火力", "出火", "出力", "出車", "出金", "出入", "出土", "早出", "女気", "空気", "男気", "天気",
			"年女", "雨女", "男女", "天女", "空車", "年金", "年男", "年月", "年日", "学年", "学力", "力学", "入学", "文学", "雨空", "雨男", "雨天", "気力", "入力", "天空", "天文", "文月", "左右", "右左", "月日", "土日"};
	private Board gameBoard;
	private ArrayList<Piece> unplaced;
	private PFont font;
	private Piece holding;
	private int turn;
	
	public void settings() {
		setSize(1200, 600);
	}
	
	public void setup() {
		gameBoard = new Board();
		PFont font = createFont("HiraMaruProN-W4", 32);
		textFont(font);
		unplaced = new ArrayList<>();
		for (int i = 0; i < CHARACTERS.length; i++) {
			unplaced.add(new Piece(CHARACTERS[i], new Color(0, 0, 0)));
		}
		turn = 1;
	}
	
	public void draw() {
		background(255, 255, 255);
		int x = Board.BOARD_DIM*Board.TILE_SIZE;
		int y = 0;
		int index = 0;
		boolean done = false;
		for (int i = 0; i < Board.BOARD_DIM; i++) {
			for (int j = 0; j < Board.BOARD_DIM; j++) {
				if (index >= unplaced.size()) {
					done = true;
					break;
				}
				unplaced.get(index).draw(this, x, y);
				x += 100;
				index++;
			}
			if (done) {
				break;
			}
			x = Board.BOARD_DIM*Board.TILE_SIZE;
			y += 100;
		}
		gameBoard.draw(this);
	}
	
	public void mousePressed() {
		Point click = new Point(mouseX, mouseY);
		float dim = height;
		Point index = gameBoard.clickToIndex(click, 0, 0, dim, dim);
		Color c = (turn % 2 == 0 ? Color.RED : Color.BLUE);
		if (index.y >= Board.BOARD_DIM) {
			int i = Board.BOARD_DIM*index.x+(index.y-Board.BOARD_DIM);
			if (i < unplaced.size()) {
				holding = unplaced.get(i);
			}
		}
		else if (index.x < Board.BOARD_DIM && holding != null) {
			if (turn <= 4 || gameBoard.flipIfValid(index.x, index.y, c, holding.getCharacter())) {
				holding.setColor(c);
				gameBoard.placePiece(index.x, index.y, holding);
				unplaced.remove(holding);
				holding = null;
				turn++;
			}
		}
	}
}
