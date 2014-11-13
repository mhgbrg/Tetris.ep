package se.chalmers.matsho.Tetris;

import java.awt.Color;

public class ZPiece extends TetrisPiece {
	public ZPiece(Position pos, Color color) {
		super(new boolean[][][] {
			{
				{false, false, false, false},
				{false, true , true , false},
				{false, false, true , true },
				{false, false, false, false}
			}, {
				{false, false, false, true },
				{false, false, true , true },
				{false, false, true , false},
				{false, false, false, false}
			}
		}, pos, color);
	}
}
