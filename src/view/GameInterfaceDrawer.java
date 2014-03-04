package view;

import view.interfaces.IMatrixDrawer;

public class GameInterfaceDrawer implements IMatrixDrawer {
	
	private String separator = "=======================================";

	@Override
	public void draw(String[][] matrix) {
		System.out.println(separator);

		// prints the number of a specific columns on the top of the game board
		int currentNumOfRow = 0;
		System.out.print("  ");
		for (int i = 0; i < matrix.length / 2 + 1; i++) {
			System.out.print(i + "   ");
		}
		System.out.println();
		// prints the game board
		for (int i = 0; i < matrix.length; i++) {
			// prints the number of a specific row on the left side of the game
			// board
			if (i % 2 == 0) {
				System.out.print(currentNumOfRow + " ");
				currentNumOfRow++;
			} else {
				System.out.print("  ");
			}
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println(separator);

	}

}
