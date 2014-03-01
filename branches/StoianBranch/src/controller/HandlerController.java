package controller;

import model.Directions;
import model.GameHandler;
import model.IHandler;
import model.Line;
import exceptions.InvalidLineIndexingException;

public class HandlerController {

	private GameHandler handler;

	public HandlerController(IHandler handler) {
		if (handler != null && handler instanceof GameHandler) {
			this.handler = (GameHandler) handler;
		}
		initializeGameBoard();
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
		if (handler.getGameBoard(row, col) == " ") {
			handler.setGameBoard(line, row, col);
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

		int startingEmptyCells = handler.getEmptyCellsCount();

		/*
		 * if player has filed horizontal line
		 */
		if (row % 2 == 0) {

			// check if there is up cell
			if (row > 0) {

				if (!handler.getGameBoard(row - 2, col).equals(" ")
						&& !handler.getGameBoard(row - 1, col - 1).equals(" ")
						&& !handler.getGameBoard(row - 1, col + 1).equals(" ")) {

					// fill player sign
					fillPlayerSign(row - 1, col, sign);
				}
			}

			// check if there is down cell
			if (row < handler.getBoardSize() - 1) {

				if (!handler.getGameBoard(row + 2, col).equals(" ")
						&& !handler.getGameBoard(row + 1, col - 1).equals(" ")
						&& !handler.getGameBoard(row + 1, col + 1).equals(" ")) {

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
				if (!handler.getGameBoard(row - 1, col - 1).equals(" ")
						&& !handler.getGameBoard(row, col - 2).equals(" ")
						&& !handler.getGameBoard(row + 1, col - 1).equals(" ")) {

					// fill player sign
					fillPlayerSign(row, col - 1, sign);
				}
			}

			// check if there is right cell
			if (col < handler.getBoardSize() - 1) {
				if (!handler.getGameBoard(row - 1, col + 1).equals(" ")
						&& !handler.getGameBoard(row, col + 2).equals(" ")
						&& !handler.getGameBoard(row + 1, col + 1).equals(" ")) {

					// fill player sign
					fillPlayerSign(row, col + 1, sign);
				}
			}
		}

		return (startingEmptyCells == handler.getEmptyCellsCount()) ? false
				: true;
	}

	private void fillPlayerSign(int row, int col, String sign) {
		handler.setGameBoard(sign, row, col);
		// May be causing errors it was with -- ??? emptyCellsCout--;
		handler.setEmptyCellsCount(handler.getEmptyCellsCount() - 1);
	}

	/**
	 * Initializes the initial state of the gameBoard field.
	 */
	private void initializeGameBoard() {
		String point = ".";
		String space = " ";

		for (int i = 0; i < handler.getRowsCount(); i++) {
			for (int j = 0; j < handler.getColsCount(); j++) {
				if (i % 2 == 0 && j % 2 == 0) {
					handler.setGameBoard(point, i, j);
				} else {
					handler.setGameBoard(space, i, j);
				}
			}
		}
	}
}
