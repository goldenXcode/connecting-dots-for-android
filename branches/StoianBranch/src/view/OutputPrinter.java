package view;

import model.Player;
import controller.HighScoresController;

public class OutputPrinter {

	public static void printWinner(Player player) {

		if (player != null) {
			System.out.println(player.getName() + " is a winner with score of: " + player.getCurrentScore());
		} else {
			System.out.println("The game is a draw!");
		}
	}

	public static void printAString(String str) {
		if (str != null)
			System.out.println(str);
	}

	/*
	 * prints a String array in the console
	 */
	public static void printOptionsListForTheMenu(String[] array) {

		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
		System.out.print("Select option: ");

	}

	/*
	 * the "Show high scores" feature of the menu
	 */
	public static void showHighscores() {

		System.out.println("\n\tHIGHSCORES:\n\n"
				+ HighScoresController.allHighscoresToString());

	}

	public static void printAvailableSigns() {
		System.out.println("\nAvailable signs: ");
		for (int i = 0; i < Player.getPlayerSignsCount(); i++) {
			System.out
					.println((i + 1) + ". " + Player.getPlayerSigns(i) + "\t");
		}
		System.out.print("Select your sign:");
	}

}
