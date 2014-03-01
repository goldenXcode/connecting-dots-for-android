package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.GameMenu;
import model.HighScores;
import model.Player;
import doublelinkedlist.LinkedList;
import doublelinkedlist.exceptions.InvalidDataParameterException;
import doublelinkedlist.exceptions.LinkedListIndexException;

public class HighScoresController {
	
	/**
	 * Saving high scores in a file, if there is one. Returns
	 * 
	 * @param player
	 * @param boardSize
	 * @return if the player has made it to the high scores
	 */
	public static boolean saveHighscore(Player player, int boardSize) {

		HighScores.setCurrentHighscoreFile(boardSize);
		getHighscoresFromFileToList(HighScores.getCurrentHighscores());
		int newScore = player.getCurrentScore();
		String newScoreLine = "Name: " + player.getName() + " -> Score: "
				+ newScore;
		boolean playerHasHighscore = updateHighscoresList(newScore,
				newScoreLine);
		writeHighscoresListToFile(HighScores.getCurrentHighscores());
		HighScores.setHighscoresList(new LinkedList());
		return playerHasHighscore;

	}

	/*
	 * returns if the given score has made it to the high scores
	 */
	private static boolean updateHighscoresList(int newScore,
			String newScoreLine) {

		if (!HighScores.getHighscoresList().contains(newScoreLine)) {
			boolean inserted = false;
			for (int i = 0; i < HighScores.getHighscoresList().getSize(); i++) {
				try {
					String scoreLine = HighScores.getHighscoresList().get(i)
							.toString();
					String scoreText = scoreLine.substring(scoreLine.length()
							- HighScores.getMaximumDigitsOfScore(),
							scoreLine.length());
					int score = Integer.parseInt(scoreText.replaceAll("[^0-9]",
							""));
					if (newScore >= score) {
						try {
							HighScores.getHighscoresList().insertAt(i,
									newScoreLine);
							inserted = true;
						} catch (InvalidDataParameterException e) {
							e.printStackTrace();
						}
						break;
					}
				} catch (LinkedListIndexException e) {
					e.printStackTrace();
				}
			}
			if (!inserted) {
				try {
					HighScores.getHighscoresList().add(newScoreLine);
				} catch (InvalidDataParameterException e) {
					e.printStackTrace();
				}
			}
			if (HighScores.getHighscoresList().getSize() > HighScores
					.getMaximumNumberOfHighscores()) {
				try {
					HighScores.getHighscoresList().remove(
							HighScores.getHighscoresList().getSize() - 1);
				} catch (LinkedListIndexException e) {
					e.printStackTrace();
				}
			}
		}
		if (HighScores.getHighscoresList().contains(newScoreLine)) {
			return true;
		}
		return false;

	}

	/**
	 * writes the high scores loaded in the linked list to a file
	 * 
	 * @param highscoresFile
	 */
	private static void writeHighscoresListToFile(File highscoresFile) {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(highscoresFile));
			for (int i = 0; i < HighScores.getHighscoresList().getSize(); i++) {
				try {
					writer.write(HighScores.getHighscoresList().get(i)
							.toString());
					if (i != HighScores.getHighscoresList().getSize() - 1) {
						writer.write(System.getProperty("line.separator"));
					}
				} catch (LinkedListIndexException | IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * loads the linked list with higshcores from a file
	 * 
	 * @param highscoresFile
	 */
	private static void getHighscoresFromFileToList(File highscoresFile) {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(highscoresFile));
			String textLine = reader.readLine();

			while (textLine != null) {
				try {
					HighScores.getHighscoresList().add(textLine);
					textLine = reader.readLine();
				} catch (InvalidDataParameterException | IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 
	 * @return string, representing all high scores in the game
	 */
	public static String allHighscoresToString() {

		String allHighscores = "";
		for (int i = 0; i < HighScores.getHighscorefiles().length; i++) {
			allHighscores += "Highscores for game "
					+ GameMenu.getAvailableSizeAt(i) + "x"
					+ GameMenu.getAvailableSizeAt(i) + ":\n";
			allHighscores += getHighscoreFileToString(HighScores
					.getHighscorefiles()[i]) + "\n";
		}
		return allHighscores;
	}

	/**
	 * 
	 * @param highscoresFile
	 * @return a string, representing high scores in a file
	 */
	private static String getHighscoreFileToString(File highscoresFile) {

		getHighscoresFromFileToList(highscoresFile);
		String highscoresText = "";
		for (int i = 0; i < HighScores.getHighscoresList().getSize(); i++) {
			try {
				highscoresText += "\t" + HighScores.getHighscoresList().get(i)
						+ "\n";
			} catch (LinkedListIndexException e) {
				e.printStackTrace();
			}
		}
		HighScores.setHighscoresList(new LinkedList());
		return highscoresText;

	}
}
