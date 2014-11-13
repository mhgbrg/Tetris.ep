package se.chalmers.matsho.Tetris;

import java.awt.Color;

public class IPiece extends TetrisPiece {
	public IPiece(Position pos, Color color) {
		super(new boolean[][][] {
			{
				{false, false, false, false},
				{true , true , true , true },
				{false, false, false, false},
				{false, false, false, false}
			}, {
				{false, false, true , false},
				{false, false, true , false},
				{false, false, true , false},
				{false, false, true , false}
			}
		}, pos, color);
	}
}
