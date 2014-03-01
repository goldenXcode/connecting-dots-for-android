package model;

/**
 * Class, representing the MVC handler for the game, has a background game board
 * matrix, on which the logic is done. Here the game board is updated after
 * every turn.
 * 
 * @author Home
 * 
 */
public class GameHandler implements IHandler {

	private String[][] gameBoard;
	private int boardSize;
	private int emptyCellsCount;

	public GameHandler(int boardSize) {
		setBoardSize((2 * boardSize) + 1);
		gameBoard = new String[this.boardSize][this.boardSize];
		emptyCellsCount = (int) Math.pow(boardSize, 2);
	}

	@Override
	public int getRowsCount() {
		return boardSize;
	}

	@Override
	public int getColsCount() {
		return boardSize;
	}

	@Override
	public String getStringForRowAndCol(int row, int col) {

		return String.valueOf(gameBoard[row][col]);

	}

	public int getBoardSize() {
		return boardSize;
	}

	/**
	 * Validates and then sets the value of boardSize field.
	 * 
	 * @param boardSize
	 *            - length of the board
	 */
	private void setBoardSize(int boardSize) {

		if (boardSize > 0) {
			this.boardSize = boardSize;
		}
	}

	/**
	 * 
	 * @return empty cells count on the game board
	 */
	public int getEmptyCellsCount() {
		return emptyCellsCount;
	}

	public void setEmptyCellsCount(int emptyCellsCount) {
		if (emptyCellsCount >= 0 && emptyCellsCount < this.emptyCellsCount)
			this.emptyCellsCount = emptyCellsCount;
	}

	public String getGameBoard(int row, int col) {
		return gameBoard[row][col];
	}

	public void setGameBoard(String str, int row, int col) {
		if (str != null && (row >= 0 && row < this.boardSize)
				&& (col >= 0 && col < this.boardSize))
			this.gameBoard[row][col] = str;
	}

}
