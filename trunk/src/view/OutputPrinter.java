package view;

import model.Player;

public class OutputPrinter {

	private void printWinner(String name, String score, boolean isATie) {

		if (isATie == false) {
			System.out
					.println(name + " is a winner with score of: "
							+ score);
		} else {
			System.out.println("The game is a draw! Both players scored "
					+ score + " times.");
		}
	}
}
