// Labbgrupp 74
// Mats Högberg
// Filip Hallqvist

package se.chalmers.matsho.Tetris;

import java.awt.Color;

/**
 * This class represents a tetris piece.
 * 
 * @author Mats Högberg
 * @author Filip Hallqvist
 */
public abstract class TetrisPiece {
	private boolean[][][] states;
	private int currentState = 0;
	private Position pos;
	private Color color;
	
	/**
	 * Constructor for TetrisPiece
	 * 
	 * @param states The states this piece can have
	 * @param pos Position of the piece on the board
	 * @param color Color of the piece
	 */
	public TetrisPiece(boolean[][][] states, Position pos, Color color) {
		this.states = states;
		this.pos = pos;
		this.color = color;
	}
	
	/**
	 * Rotates the piece by 90 degrees
	 */
	public void rotate() {
		currentState = (currentState + 1) % states.length;
	}
	
	/**
	 * Increments the Y coordinate by 1
	 */
	public void moveDown() {
		pos = new Position(pos.getX(), pos.getY() + 1);
	}
	
	/**
	 * Decrements the X coordinate by 1
	 */
	public void moveLeft() {
		pos = new Position(pos.getX() - 1, pos.getY());
	}
	
	/**
	 * Increments the X coordinate by 1
	 */
	public void moveRight() {
		pos = new Position(pos.getX() + 1, pos.getY());
	}
	
	/**
	 * @return Current state of the piece
	 */
	public boolean[][] getState() {
		return states[currentState];
	}
	
	/**
	 * @return State of the piece after next rotate
	 */
	public boolean[][] getNextState() {
		return states[(currentState + 1) % states.length];
	}
	
	/**
	 * @return Position of the piece
	 */
	public Position getPos() {
		return pos;
	}
	
	/**
	 * @return Color of the piece
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @return Logs the state of the piece to the console.
	 */
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
