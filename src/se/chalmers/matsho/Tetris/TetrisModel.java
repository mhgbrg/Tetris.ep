package se.chalmers.matsho.Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Random;

public class TetrisModel extends GameModel {
	// The gameboard size
	private static Dimension size;
	
	private int score = 0;
	
	// Tile for the background
	private static final GameTile BLANK_TILE = new RectangularTile(Color.BLACK);
	
	// Startposition for new pieces
	private static Position START_POS;
	
	// Containers for current piece and current piece tile
	private TetrisPiece currentPiece;
	private PieceTile currentPieceTile;
	
	// Number of times the gameloop has run
	private int loopCount = 0;
	
	/**
	 * Default constructor. Blanks the board, sets start position for new pieces
	 * and gets and displays a new piece.
	 */
	public TetrisModel() {
		size = getGameboardSize();
		blankBoard();
		
		START_POS = new Position(size.width / 2 - 2, -1);
		getNewPiece();
		displayCurrentPiece();
	}
	
	/**
	 * Fills the entire board with blank tiles.
	 */
	private void blankBoard() {
		for (int row = 0; row < size.height; row++) {
			blankRow(row);
		}
	}
	
	/**
	 * Fills a single row with blank tiles. 
	 * 
	 * @param row The row to blank
	 */
	private void blankRow(final int row) {
		for (int col = 0; col < size.width; col++) {
			setGameboardState(col, row, BLANK_TILE);
		}
	}
	
	/**
	 * Sets the current piece to a new random piece and sets currentPieceTile
	 * to the correct color.
	 */
	private void getNewPiece() {
		Position startPos = new Position(START_POS.getX(), START_POS.getY());
		
		Random rand = new Random();
		switch (rand.nextInt(7)) {
		case 0:
			currentPiece = new OPiece(startPos, Color.YELLOW);
			break;
		case 1:
			currentPiece = new IPiece(startPos, Color.CYAN);
			break;
		case 2:
			currentPiece = new SPiece(startPos, Color.GREEN);
			break;
		case 3:
			currentPiece = new ZPiece(startPos, Color.RED);
			break;
		case 4:
			currentPiece = new LPiece(startPos, Color.ORANGE);
			break;
		case 5:
			currentPiece = new JPiece(startPos, Color.BLUE);
			break;
		case 6:
			currentPiece = new TPiece(startPos, new Color(255, 0, 255));
			break;
		}
		
		currentPieceTile = new PieceTile(currentPiece.getColor());
	}
	
	/**
	 * This method is called periodically to update the game state.
	 * 
	 * It takes the last user input and performs the corresponding action
	 * every time it runs. Every fourth time it automatically moves the current
	 * piece down one row.
	 * 
	 * @throws GameOverException 
	 */
	@Override
	public void gameUpdate(final int lastKey) throws GameOverException {
		loopCount++;
		
		blankCurrentPiece();
		updateAction(lastKey);
		if (loopCount == 4) {
			tryMoveDown();
			loopCount = 0;
		}
		displayCurrentPiece();
	}
	
	/**
	 * Performs an action depending on user input.
	 * 
	 * * up arrow - rotate piece
	 * * down arrow - move piece down one row
	 * * left arrow - move piece one column to the left
	 * * right arrow - move piece one column to the right
	 * * space key - drop piece to the buttom
	 * 
	 * @param key The key that has been pressed
	 * @throws GameOverException
	 */
	private void updateAction(final int key) throws GameOverException {
		switch (key) {
			case KeyEvent.VK_LEFT:
				if (isMoveLegal(-1, 0)) {
					currentPiece.moveLeft();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (isMoveLegal(1, 0)) {
					currentPiece.moveRight();
				}
				break;
			case KeyEvent.VK_UP:
				if (isRotateLegal()) {
					currentPiece.rotate();
				}
				break;
			case KeyEvent.VK_DOWN:
				tryMoveDown();
				break;
			case KeyEvent.VK_SPACE:
				dropPiece();
				break;
		}
	}
	
	/**
	 * Drops a piece to the bottom.
	 * 
	 * @throws GameOverException
	 */
	private void dropPiece() throws GameOverException {
		boolean loop = true;
		while (loop) {
			loop = tryMoveDown();
		}
	}
	
	/**
	 * Tries to move the current piece down one row.
	 * 
	 * If the move can't be completed, the current piece is considered 'dead'
	 * and a new random piece is generated.  
	 * 
	 * @return true if the piece is moved, false if not
	 * @throws GameOverException
	 */
	private boolean tryMoveDown() throws GameOverException {
		if (isMoveLegal(0, 1)) {
			currentPiece.moveDown();
			return true;
		} else {
			displayCurrentPiece();
			lineClear();
			getNewPiece();
			if (checkForCollision(currentPiece.getState(), currentPiece.getPos())) {
				throw new GameOverException(this.score);
			}
			return false;
		}
	}
	
	/**
	 * Loops through all and checks for line clears. Updates score if 
	 * one or more lines have been cleared.
	 */
	private void lineClear() {
		int combo = 0;
		
		for (int row = 0; row < size.height; row++) {
			if (checkFullRow(row)) {
				combo++;
				blankRow(row);
				shiftBoardDown(row);
				blankRow(0);
			}
		}
		
		switch(combo) {
			case 1:
				this.score += 40;
				break;
			case 2:
				this.score += 100;
				break;
			case 3:
				this.score += 300;
				break;
			case 4:
				this.score += 1200;
				break;
		}
	}
	
	/**
	 * Checks if a row has been filled
	 * 
	 * @param row The row to check
	 * @return true if full, otherwise false
	 */
	private boolean checkFullRow(final int row) {
		for (int col = 0; col < size.width; col++) {
			if (getGameboardState(col, row) == BLANK_TILE) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Shifts the entire board down one row, starting from fromRow and going up
	 * 
	 * @param fromRow The row to start from
	 */
	private void shiftBoardDown(final int fromRow) {
		for (int i = fromRow - 1; i >= 0; i--) {
			shiftRowDown(i);
		}
	}
	
	/**
	 * Shifts a row down one step
	 * 
	 * @param row The row to shift
	 */
	private void shiftRowDown(final int row) {
		for (int col = 0; col < size.width; col++) {
			setGameboardState(col, row + 1, getGameboardState(col, row));
		}
	}
	
	/**
	 * Checks if a move is legeal
	 * 
	 * @param deltaX Movement in x-axis
	 * @param deltaY Movement in y-axis
	 * @return True if move is legal, otherwise false
	 */
	private boolean isMoveLegal(final int deltaX, final int deltaY) {
		try {
			Position newPos = new Position(currentPiece.getPos().getX() + deltaX, currentPiece.getPos().getY() + deltaY);
			return !checkForCollision(currentPiece.getState(), newPos);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	/**
	 * Checks if rotate is legal
	 * 
	 * @return True if move is lega, otherwise false
	 */
	private boolean isRotateLegal() {
		try {
			return !checkForCollision(currentPiece.getNextState(), currentPiece.getPos());
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	/**
	 * Checks if a piece state on a given position collides with something 
	 * 
	 * @param state The state to check with
	 * @param pos The position to check from
	 * @return True if collision, otherwise false
	 */
	private boolean checkForCollision(final boolean[][] state, final Position pos) {
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j]) {
					if (getGameboardState(
							j + pos.getX(),
							i + pos.getY()) != BLANK_TILE) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Fills the current piece on current position with blank tiles
	 */
	private void blankCurrentPiece() {
		repaintCurrentPiece(BLANK_TILE);
	}
	
	/**
	 * Fills the current piece on current position with currentPieceTile
	 */
	private void displayCurrentPiece() {
		repaintCurrentPiece(currentPieceTile);
	}
	
	/**
	 * Fills the squares for the current piece with a specified tile 
	 * 
	 * @param tile The tile to fill with
	 */
	private void repaintCurrentPiece(final GameTile tile) {
		boolean[][] state = currentPiece.getState();
		Position pos = currentPiece.getPos();
		
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j]) {
					setGameboardState(
						j + pos.getX(),
						i + pos.getY(),
						tile
					);
				}
			}
		}
	}
}
