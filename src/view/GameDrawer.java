package view;

import model.IHandler;

/**
 * Class, representing the MVC drawer for the game.
 * 
 * @author Home
 * 
 */
public class GameDrawer implements IDrawer {

	/**
	 * Drawing the game board with number of rows and columns for ease of play.
	 * Also print numbers for each row and column.
	 */
	@Override
	public void draw(IHandler handler) {
		// prints the number of a specific columns on the top of the game board
		int currentNumOfRow = 0;
		System.out.print("  ");
		for (int i = 0; i < handler.getColsCount() / 2 + 1; i++) {
			System.out.print(i + "   ");
		}
		System.out.println();
		// prints the game board
		for (int i = 0; i < handler.getRowsCount(); i++) {
			// prints the number of a specific row on the left side of the game
			// board
			if (i % 2 == 0) {
				System.out.print(currentNumOfRow + " ");
				currentNumOfRow++;
			} else {
				System.out.print("  ");
			}
			for (int j = 0; j < handler.getColsCount(); j++) {
				System.out.print(handler.getStringForRowAndCol(i, j) + " ");
			}
			System.out.println();
		}
		System.out.println("============================================");

	}

}
