package se.chalmers.matsho.Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.util.Random;

public class TetrisModel extends GameModel {
	private static Dimension size;
	
	private static final GameTile BLANK_TILE = new GameTile();
	
	private static Position START_POS;
	
	private TetrisPiece currentPiece;
	private RectangularTile currentPieceTile;
	
	private int loopCount = 0;
	
	public TetrisModel() {
		size = getGameboardSize();
		blankBoard();
		
		START_POS = new Position(size.width / 2 - 2, -1);
		getNewPiece();
		displayCurrentPiece();
	}
	
	private void blankBoard() {
		for (int i = 0; i < size.width; i++) {
			blankRow(i);
		}
	}
	
	private void blankRow(final int i) {
		for (int j = 0; j < size.height; j++) {
			setGameboardState(i, j, BLANK_TILE);
		}
	}
	
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
			currentPiece = new SPiece(startPos, Color.RED);
			break;
		case 3:
			currentPiece = new ZPiece(startPos, Color.ORANGE);
			break;
		case 4:
			currentPiece = new LPiece(startPos, Color.GREEN);
			break;
		case 5:
			currentPiece = new JPiece(startPos, Color.BLUE);
			break;
		case 6:
			currentPiece = new TPiece(startPos, Color.PINK);
			break;
		}
		
		currentPieceTile = new RectangularTile(currentPiece.getColor());
	}
	
	@Override
	public void gameUpdate(final int lastKey) throws GameOverException {
		if (lastKey == KeyEvent.VK_LEFT
				|| lastKey == KeyEvent.VK_RIGHT
				|| lastKey == KeyEvent.VK_UP
				|| lastKey == KeyEvent.VK_DOWN
				|| lastKey == KeyEvent.VK_SPACE
				|| loopCount % 4 == 0) {
			
			blankCurrentPiece();
			updateAction(lastKey);
			if (loopCount % 4 == 0 && loopCount != 0) {
				currentPiece.moveDown();
			}
			displayCurrentPiece();
		}
		
		loopCount++;
	}
	
	private void updateAction(final int key) {
		switch (key) {
		case KeyEvent.VK_LEFT:
			currentPiece.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			currentPiece.moveRight();
			break;
		case KeyEvent.VK_UP:
			if (isRotateLegal()) {
				currentPiece.rotate();
			}
			break;
		case KeyEvent.VK_DOWN:
			currentPiece.moveDown();
			break;
		case KeyEvent.VK_SPACE:
//			currentPiece.drop();
			break;
		}
	}
	
	private boolean isRotateLegal() {
		boolean[][] nextState = currentPiece.getNextState();
		try {
			return checkForCollision(nextState);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private boolean checkForCollision(boolean[][] state) {
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j]) {
					if (getGameboardState(
							j + currentPiece.getPos().getX(),
							i + currentPiece.getPos().getY()) != BLANK_TILE) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private void blankCurrentPiece() {
		repaintCurrentPiece(BLANK_TILE);
	}
	
	private void displayCurrentPiece() {
		repaintCurrentPiece(currentPieceTile);
	}
	
	private void repaintCurrentPiece(GameTile tile) {
		boolean[][] state = currentPiece.getState();
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j]) {
					setGameboardState(
						j + currentPiece.getPos().getX(),
						i + currentPiece.getPos().getY(),
						tile
					);
				}
			}
		}
	}
}
