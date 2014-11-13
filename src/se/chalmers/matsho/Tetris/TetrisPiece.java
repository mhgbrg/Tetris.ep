package se.chalmers.matsho.Tetris;

import java.awt.Color;

public abstract class TetrisPiece {
	private boolean[][][] states;
	private int currentState = 0;
	private Position pos;
	private Color color;
	
	public TetrisPiece(boolean[][][] states, Position pos, Color color) {
		this.states = states;
		this.pos = pos;
		this.color = color;
	}
	
	public void rotate() {
		currentState = (currentState + 1) % states.length;
	}
	
	public void log() {
		boolean[][] state = states[currentState];
		for (int i = 0; i < state.length; i++) {
			System.out.print("  [ ");
			for (int j = 0; j < state.length; j++) {
				if (state[i][j] == true) {
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
