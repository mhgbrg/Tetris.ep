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
	
	public void moveDown() {
		pos = new Position(pos.getX(), pos.getY() + 1);
	}
	
	public void moveLeft() {
		pos = new Position(pos.getX() - 1, pos.getY());
	}
	
	public void moveRight() {
		pos = new Position(pos.getX() + 1, pos.getY());
	}
	
	public boolean[][] getState() {
		return states[currentState];
	}
	
	public boolean[][] getNextState() {
		return states[(currentState + 1) % states.length];
	}
	
	public Position getPos() {
		return pos;
	}
	
	public Color getColor() {
		return color;
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
