package se.chalmers.matsho.Tetris;

import java.awt.Color;

public class TetrisPieceTest {
	public static void main(String[] args) {
		TetrisPiece p = new ZPiece(new Position(0, 0), Color.BLUE);
		p.log();
		p.rotate();
		p.log();
		p.rotate();
		p.log();
		p.rotate();
		p.log();
		p.rotate();
		p.log();
	}
}
