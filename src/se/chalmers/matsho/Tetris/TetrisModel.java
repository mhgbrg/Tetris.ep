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
		
		blankCurrentPiece();
		updateAction(lastKey);
		if (loopCount % 4 == 0 && loopCount != 0) {
			tryMoveDown();
		}
		displayCurrentPiece();
		
		loopCount++;
	}
	
	private boolean updateAction(final int key) throws GameOverException {
		switch (key) {
		case KeyEvent.VK_LEFT:
			if (isMoveLegal(-1, 0)) {
				currentPiece.moveLeft();
			}
			return true;
		case KeyEvent.VK_RIGHT:
			if (isMoveLegal(1, 0)) {
				currentPiece.moveRight();
			}
			return true;
		case KeyEvent.VK_UP:
			if (isRotateLegal()) {
				currentPiece.rotate();
			}
			return true;
		case KeyEvent.VK_DOWN:
			tryMoveDown();
			return true;
		case KeyEvent.VK_SPACE:
			return true;
		}
		return false;
	}
	
	private void tryMoveDown() throws GameOverException {
		if (isMoveLegal(0, 1)) {
			currentPiece.moveDown();			
		} else {
			displayCurrentPiece();
			getNewPiece();
			if (checkForCollision(currentPiece.getState(), currentPiece.getPos())) {
				throw new GameOverException(0);
			}
		}
	}
	
	private boolean isMoveLegal(final int deltaX, final int deltaY) {
		try {
			Position newPos = new Position(currentPiece.getPos().getX() + deltaX, currentPiece.getPos().getY() + deltaY);
			return !checkForCollision(currentPiece.getState(), newPos);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private boolean isRotateLegal() {
		try {
			return !checkForCollision(currentPiece.getNextState(), currentPiece.getPos());
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private boolean checkForCollision(boolean[][] state, Position pos) {
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
	
	private void blankCurrentPiece() {
		repaintCurrentPiece(BLANK_TILE);
	}
	
	private void displayCurrentPiece() {
		repaintCurrentPiece(currentPieceTile);
	}
	
	private void repaintCurrentPiece(GameTile tile) {
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
	
//	private void repaintPiece(boolean[][] state, Position pos, GameTile tile) {
//		for (int i = 0; i < state.length; i++) {
//			for (int j = 0; j < state[i].length; j++) {
//				if (state[i][j]) {
//					setGameboardState(
//						j + pos.getX(),
//						i + pos.getY(),
//						tile
//					);
//				}
//			}
//		}
//	}
}
