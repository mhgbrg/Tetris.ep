package se.chalmers.matsho.Tetris;

import java.awt.Color;

public class TetrisPieceOld {
	private boolean[][] piece = {
			{false, false, false, false, false},
			{false, false, false, false, false},
			{true , true , true , true , false},
			{false, false, false, false, false},
			{false, false, false, false, false}
	};
	
	public void rotate() {
		// TODO: Determine and perform a rotation in the correct direction
	}
	
	public void rotateCW() {
		transpose();
		reverseRows();
	}
	
	public void rotateCCW() {
		transpose();
		reverseColumns();
	}
	
	private void transpose() {
		boolean[][] transposed = new boolean[piece[0].length][piece.length];
		
		for (int row = 0; row < piece.length; row++) {
			for (int col = 0; col < piece[row].length; col++) {
				transposed[col][row] = piece[row][col];
			}
		}
		
		piece = transposed;
	}
	
	private void reverseRows() {
		for (int i = 0; i < piece.length; i++) {
			reverseRow(i);
		}
	}
	
	private void reverseRow(int rowNumber) {
		boolean[] row = piece[rowNumber];
		for (int i = 0; i < row.length / 2; i++) {
			boolean tmp = row[i];
			row[i] = row[row.length - i - 1];
			row[row.length - i - 1] = tmp; 
		}
	}
	
	private void reverseColumns() {
		for (int i = 0; i < piece[0].length; i++) {
			reverseColumn(i);
		}
	}
	
	private void reverseColumn(int colNumber) {
		for (int i = 0; i < piece.length / 2; i++) {
			boolean tmp = piece[i][colNumber];
			piece[i][colNumber] = piece[piece.length - i - 1][colNumber];
			piece[piece.length - i - 1][colNumber] = tmp; 
		}
	}
	
	public void log() {
		for (int i = 0; i < piece.length; i++) {
			System.out.print("  [ ");
			for (int j = 0; j < piece.length; j++) {
				if (piece[i][j] == true) {
					System.out.print("0 ");
				} else {
					System.out.print("- ");
				}
			}
			System.out.println("]");
		}
		System.out.println();
	}
}
