// Labbgrupp 74
// Mats Högberg
// Filip Hallqvist

package se.chalmers.matsho.Tetris;

import java.awt.Color;

public class SPiece extends TetrisPiece {
	public SPiece(Position pos, Color color) {
		super(new boolean[][][] {
			{
				{false, false, false, false},
				{false, false, true , true },
				{false, true , true , false},
				{false, false, false, false}
			}, {
				{false, false, true , false},
				{false, false, true , true },
				{false, false, false, true },
				{false, false, false, false}
			}
		}, pos, color);
	}
}
