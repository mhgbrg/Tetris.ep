package se.chalmers.matsho.Tetris;

import java.awt.Color;

public class TetrisPieceTest {
	public static void main(String[] args) {
//		TetrisPieceAbstract p = new LPiece(new Position(0, 0), Color.BLUE);
		TetrisPieceOld p = new TetrisPieceOld();
		p.log();
		p.rotateCW();
		p.log();
		p.rotateCW();
		p.log();
		p.rotateCW();
		p.log();
		p.rotateCW();
		p.log();
	}
}
