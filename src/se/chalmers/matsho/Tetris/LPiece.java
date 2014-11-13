package se.chalmers.matsho.Tetris;

import java.awt.Color;

public class LPiece extends TetrisPiece {
	public LPiece(Position pos, Color color) {
		super(new boolean[][][] {
			{
				{false, false, false, true },
				{false, true , true , true },
				{false, false, false, false},
				{false, false, false, false}
			}, {
				{false, false, true , false},
				{false, false, true , false},
				{false, false, true , true },
				{false, false, false, false}
			}, {
				{false, false, false, false},
				{false, true , true , true },
				{false, true , false, false},
				{false, false, false, false}
				
			}, {
				{false, true , true , false},
				{false, false, true , false},
				{false, false, true , false},
				{false, false, false, false}
			}
		}, pos, color);
	}
}
