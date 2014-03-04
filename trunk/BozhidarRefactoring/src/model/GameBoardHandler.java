package model;

import model.interfaces.IGameBoardHandler;

public class GameBoardHandler implements IGameBoardHandler {

	private String[][] gameBoard;
	private int boardSize;
	@Override
	public int getSize() {

		return boardSize;

	}

	@Override
	public String get(int row, int col) {

		return gameBoard[row][col];

	}

	@Override
	public void set(int row, int col, String item) {

		if (item != null) {
			gameBoard[row][col] = item;
		}

	}

	@Override
	public String[][] getMatrix() {
		return gameBoard;
	}

	

	@Override
	public void setBoard(int boardSize) {

		if (boardSize > 0) {
			this.boardSize = boardSize;
			gameBoard = new String[boardSize][boardSize];
		}

	}
	
	

}
