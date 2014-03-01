package model;

import java.io.File;

import doublelinkedlist.LinkedList;

public class HighScores {

	private static final int MAXIMUM_NUMBER_OF_HIGHSCORES = 10;
	private static final int MAXIMUM_DIGITS_OF_SCORE = 2;
	private static final File[] highscoreFiles = {
			new File("highscores_5x5.txt"), new File("highscores_7x7.txt"),
			new File("highscores_9x9.txt") };
	private static File currentHighscores;
	private static LinkedList highscoresList = new LinkedList();

	public static int getMaximumNumberOfHighscores() {
		return MAXIMUM_NUMBER_OF_HIGHSCORES;
	}

	public static int getMaximumDigitsOfScore() {
		return MAXIMUM_DIGITS_OF_SCORE;
	}

	public static File getCurrentHighscores() {
		return currentHighscores;
	}

	public static void setCurrentHighscores(File currentHighscores) {
		if (currentHighscores != null)
			HighScores.currentHighscores = currentHighscores;
	}

	public static LinkedList getHighscoresList() {
		return highscoresList;
	}

	public static File[] getHighscorefiles() {
		return highscoreFiles;
	}

	/*
	 * Sets the current file for high scores. Cases depend on the available
	 * sizes in the game menu!!
	 */
	public static void setCurrentHighscoreFile(int boardSize) {
		String defaultMsg = "Can't find highscore file for the given board size!";
		switch (boardSize) {
		case 5:
			currentHighscores = highscoreFiles[0];
			break;
		case 7:
			currentHighscores = highscoreFiles[1];
			break;
		case 9:
			currentHighscores = highscoreFiles[2];
			break;
		default:
			System.out.println(defaultMsg);
		}

	}

	public static void setHighscoresList(LinkedList highscoresList) {
		if (highscoresList != null)
			HighScores.highscoresList = highscoresList;
	}
}
