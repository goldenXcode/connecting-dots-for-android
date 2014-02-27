package model;

import exceptions.InvalidLineIndexingException;

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
		initializeGameBoard();
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

	/**
	 * Fills a line in the matrix.
	 * 
	 * @param line
	 *            - the line which the player selected for drawing
	 * @return whether the player has scored a point or not
	 * @throws InvalidLineIndexingException
	 *             - when the index of the matrix is wrong
	 */
	public boolean updateGameBoard(Line line, String sign)
			throws InvalidLineIndexingException {

		String verticalLine = "|";
		String horizontalLine = "-";

		int row = (line.getRow() * 2);
		int col = (line.getCol() * 2);

		try {
			if (line.getDirection().equals(Directions.U)) {
				row -= 1;
				fillLine(verticalLine, row, col);
			} else if (line.getDirection().equals(Directions.D)) {
				row += 1;
				fillLine(verticalLine, row, col);
			} else if (line.getDirection().equals(Directions.R)) {
				col += 1;
				fillLine(horizontalLine, row, col);
			} else {
				col -= 1;
				fillLine(horizontalLine, row, col);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidLineIndexingException(
					"\nThis direction is getting out of the board's bounds!\nPlease try again.");
		}

		return hasScored(row, col, sign);

	}

	/**
	 * Checks if on a given position in the matrix, determined by the row and
	 * the column in the parameters list, is already taken. If is taken throws
	 * an exception, if is not fill the position with vertical or horizontal
	 * line based on the line parameter.
	 * 
	 * @param line
	 *            - it can accept vertical or horizontal line
	 * @param row
	 *            - the number of the row
	 * @param col
	 *            - the number of the column
	 * @throws InvalidLineIndexingException
	 *             - when the index of the matrix is wrong
	 */

	private void fillLine(String line, int row, int col)
			throws InvalidLineIndexingException {
		if (gameBoard[row][col] == " ") {
			gameBoard[row][col] = line;
		} else {
			throw new InvalidLineIndexingException(
					"This line is already drawn.");
		}
	}

	/**
	 * Determines if the player has scored a point, if is true also fills the
	 * player sign.
	 * 
	 * @param row
	 *            - number of the row
	 * @param col
	 *            - number of the column
	 * @param sign
	 *            - player sign
	 * @return - true if the player has scored, else false
	 */
	private boolean hasScored(int row, int col, String sign) {
		
		int startingEmptyCells = emptyCellsCount;

		/*
		 * if player has filed horizontal line
		 */
		if (row % 2 == 0) {

			// check if there is up cell
			if (row > 0) {

				if (!gameBoard[row - 2][col].equals(" ")
						&& !gameBoard[row - 1][col - 1].equals(" ")
						&& !gameBoard[row - 1][col + 1].equals(" ")) {

					// fill player sign
					fillPlayerSign(row - 1, col, sign);
				}
			}

			// check if there is down cell
			if (row < boardSize - 1) {

				if (!gameBoard[row + 2][col].equals(" ")
						&& !gameBoard[row + 1][col - 1].equals(" ")
						&& !gameBoard[row + 1][col + 1].equals(" ")) {

					// fill player sign
					fillPlayerSign(row + 1, col, sign);
				}
			}

		}

		/*
		 * player has filled vertical line
		 */
		else {

			// check if there is left cell
			if (col > 0) {
				if (!gameBoard[row - 1][col - 1].equals(" ")
						&& !gameBoard[row][col - 2].equals(" ")
						&& !gameBoard[row + 1][col - 1].equals(" ")) {

					// fill player sign
					fillPlayerSign(row, col - 1, sign);
				}
			}

			// check if there is right cell
			if (col < boardSize - 1) {
				if (!gameBoard[row - 1][col + 1].equals(" ")
						&& !gameBoard[row][col + 2].equals(" ")
						&& !gameBoard[row + 1][col + 1].equals(" ")) {

					// fill player sign
					fillPlayerSign(row, col + 1, sign);
				}
			}
		}

		return (startingEmptyCells == emptyCellsCount) ? false : true;
	}

	private void fillPlayerSign(int row, int col, String sign) {
		gameBoard[row][col] = sign;
		emptyCellsCount--;
	}

	/**
	 * Initializes the initial state of the gameBoard field.
	 */
	private void initializeGameBoard() {
		String point = ".";
		String space = " ";

		for (int i = 0; i < this.getRowsCount(); i++) {
			for (int j = 0; j < this.getColsCount(); j++) {
				if (i % 2 == 0 && j % 2 == 0) {
					this.gameBoard[i][j] = point;
				} else {
					this.gameBoard[i][j] = space;
				}
			}
		}
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

	public int getBoardSize() {
		return boardSize;
	}

}
