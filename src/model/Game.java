package model;

import java.util.Scanner;
import exceptions.InvalidLineIndexingException;
import exceptions.NoSuchDirectionException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import controller.GameMVC;


import view.GameDrawer;
import view.GameMenu;
import view.InputChooser;


import doublelinkedlist.LinkedList;
import doublelinkedlist.exceptions.InvalidDataParameterException;
import doublelinkedlist.exceptions.LinkedListIndexException;

/**
 * The main class of the project, representing the game. It has a menu, MVC, and
 * two players in it. Also it counts the turns for each player. Based upon
 * everything it starts the game.
 * 
 * @author Home
 * 
 */
public class Game {

	private static final int MAXIMUM_NUMBER_OF_HIGHSCORES = 10;
	private static final int MAXIMUM_DIGITS_OF_SCORE = 2;
	private static final File[] highscoreFiles = {
			new File("highscores_5x5.txt"), new File("highscores_7x7.txt"),
			new File("highscores_9x9.txt") };
	private static File currentHighscores;
	private static LinkedList highscoresList = new LinkedList();

	private GameMenu menu;
	private GameMVC personalMVC;
	private Player playerOne, playerTwo, winner;
	private int turns;
	private int gameSize;

	public Game(Player playerOne, Player playerTwo) {
		this.personalMVC = new GameMVC();
		this.menu = new GameMenu();
		setPlayerOne(playerOne);
		setPlayerTwo(playerTwo);
	}

	/**
	 * From this method the game is started.
	 */
	public void play() {
		
		for (;;) {
			boolean startGame = menu.display();
			if (!startGame) {
				break;
			}
			gameSize = menu.getSelectedSize();
			System.out.println("Board size set to " + gameSize + "!");
			this.personalMVC.setDrawer(new GameDrawer());
			this.personalMVC.setHandler(new GameHandler(gameSize));
			playSingleGame();
		}
		System.out.println("Bye bye!");
	}

	/**
	 * Plays a single game. And also determine which one of the players is the
	 * winner, or if the game is a tie. Saves high scores in a file!
	 */
	private void playSingleGame() {

		while (!isOver()) {
			playTurn();
		}
		winner = (playerOne.getCurrentScore() > playerTwo.getCurrentScore()) ? playerOne : playerTwo;
		if (playerOne.getCurrentScore() == playerTwo.getCurrentScore()) {
			winner = null;
		}
		saveHighscore(playerOne, gameSize);
		saveHighscore(playerTwo, gameSize);
	}

	/**
	 * Used to ask the user to choose where he wants to enter a line, based on
	 * row, column and direction. Also checks if the user has made a score.
	 * 
	 * @param p
	 *            - representing each player in the game.
	 */
	private void askForLine(Player p) {

		boolean scores;
		int emptyCellsInStartOfTurn;
		// outer:
		do {
			scores = false;
			emptyCellsInStartOfTurn = this.personalMVC.getHandler()
					.getEmptyCellsCount();
			System.out.println(p.getName()
					+ ", please enter coordinates for a line: ");

			personalMVC.getDrawer().draw(personalMVC.getHandler());

			if (personalMVC.getHandler().getEmptyCellsCount() == 0) {
				break;
			}

			Scanner sc = new Scanner(System.in);

			Directions selectedDirection;
			Line line;
			int row = -1;
			int col = -1;
			for (;;) {
				row = askForIndex(sc, "Row");
				col = askForIndex(sc, "Col");
				try {
					selectedDirection = enterDirection(sc);
					line = new Line(row, col, selectedDirection);
					break;
				} catch (NoSuchDirectionException e) {
					System.out.println(e.getMessage());
				}
			}
			try {
				scores = personalMVC.getHandler().updateGameBoard(line,
						p.getSign());
			} catch (InvalidLineIndexingException e) {
				System.out.println(e.getMessage());
				askForLine(p);
			}
			// checking if the player has closed more than one cell in one move.
			if (scores) {
				int numberOfPointsScored = emptyCellsInStartOfTurn
						- this.personalMVC.getHandler().getEmptyCellsCount();
				System.out.println("Good job!!! You scored !");
				p.increaseScore(numberOfPointsScored);
			}
		} while (scores);

	}

	/**
	 * Asking from the player to enter a direction.
	 * 
	 * @param Dinnamicaly
	 *            - Scanner.
	 * @return Enum - direction from a string.
	 * @throws NoSuchDirectionException
	 *             - when the direction is wrong.
	 */
	private Directions enterDirection(Scanner sc)
			throws NoSuchDirectionException {
		try {
			for (;;) {
				System.out.println("Please enter a direction (L, R, U, D):");
				String directionText = sc.next();
				if (!directionText.equals("R") && !directionText.equals("L")
						&& !directionText.equals("U")
						&& !directionText.equals("D")) {
					System.out.println("The direction is incorrect!");
					System.out.println("Please try again.");
					continue;
				}

				Directions selectedDirection = Directions
						.valueOf(directionText);
				return selectedDirection;
			}
		} catch (IllegalArgumentException e) {
			throw new NoSuchDirectionException("There is no such direction!", e);
		}
	}

	/**
	 * Asking from the player to enter number for rows or columns.
	 * 
	 * @param Dinnamicaly
	 *            entering number for rows or cols
	 * @param Row
	 *            Or Column
	 * @return number for Row or Column
	 * @throws WrongNumberException
	 */

	private int askForIndex(Scanner sc, String rowOrCol) {
		int number = -1;
		for (;;) {
			System.out.println("Please enter a valid number for " + rowOrCol
					+ ": ");
			try {
				number = Integer.parseInt(sc.nextLine());
				if (number < 0) {
					System.out.println("The number must be positive!");
					System.out.println("Please try again.");
					continue;
				} else if (number > this.personalMVC.getHandler()
						.getColsCount() / 2) {
					System.out.println("The number must be below or equal to: "
							+ this.personalMVC.getHandler().getColsCount() / 2);
					System.out.println("Please try again.");
					continue;
				}
			} catch (NumberFormatException e) {
				askForIndex(sc, rowOrCol);
			}
			break;
		}
		return number;
	}

	/**
	 * Plays a single turn.
	 */
	private void playTurn() {

		this.turns++;
		if (turns % 2 != 0) {
			askForLine(playerOne);
		} else {
			askForLine(playerTwo);
		}

	}

	/**
	 * Determines if the game is over.
	 * 
	 * @return - If is true the game is over, else is not.
	 */
	private boolean isOver() {

		if (personalMVC.getHandler().getEmptyCellsCount() == 0)
			return true;
		return false;

	}

	/**
	 * Saving high scores in a file, if there is one. Returns 
	 * @param player
	 * @param boardSize
	 * @return if the player has made it to the high scores
	 */
	private static boolean saveHighscore(Player player, int boardSize) {

		setCurrentHighscoreFile(boardSize);
		getHighscoresFromFileToList(currentHighscores);
		int newScore = player.getCurrentScore();
		String newScoreLine = "Name: " + player.getName() + " -> Score: "
				+ newScore;
		boolean playerHasHighscore = updateHighscoresList(newScore,
				newScoreLine);
		writeHighscoresListToFile(currentHighscores);
		highscoresList = new LinkedList();
		return playerHasHighscore;

	}

	/*
	 * returns if the given score has made it to the high scores
	 */
	private static boolean updateHighscoresList(int newScore,
			String newScoreLine) {

		if (!highscoresList.contains(newScoreLine)) {
			boolean inserted = false;
			for (int i = 0; i < highscoresList.getSize(); i++) {
				try {
					String scoreLine = highscoresList.get(i).toString();
					String scoreText = scoreLine.substring(scoreLine.length()
							- MAXIMUM_DIGITS_OF_SCORE, scoreLine.length());
					int score = Integer.parseInt(scoreText.replaceAll("[^0-9]",
							""));
					if (newScore >= score) {
						try {
							highscoresList.insertAt(i, newScoreLine);
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
					highscoresList.add(newScoreLine);
				} catch (InvalidDataParameterException e) {
					e.printStackTrace();
				}
			}
			if (highscoresList.getSize() > MAXIMUM_NUMBER_OF_HIGHSCORES) {
				try {
					highscoresList.remove(highscoresList.getSize() - 1);
				} catch (LinkedListIndexException e) {
					e.printStackTrace();
				}
			}
		}
		if (highscoresList.contains(newScoreLine)) {
			return true;
		}
		return false;

	}

	/**
	 * writes the high scores loaded in the linked list to a file
	 * @param highscoresFile
	 */
	private static void writeHighscoresListToFile(File highscoresFile) {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(highscoresFile));
			for (int i = 0; i < highscoresList.getSize(); i++) {
				try {
					writer.write(highscoresList.get(i).toString());
					if (i != highscoresList.getSize() - 1) {
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
	 * @param highscoresFile
	 */
	private static void getHighscoresFromFileToList(File highscoresFile) {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(highscoresFile));
			String textLine = reader.readLine();

			while (textLine != null) {
				try {
					highscoresList.add(textLine);
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
		for (int i = 0; i < highscoreFiles.length; i++) {
			allHighscores += "Highscores for game "
					+ GameMenu.getAvailableSizeAt(i) + "x"
					+ GameMenu.getAvailableSizeAt(i) + ":\n";
			allHighscores += getHighscoreFileToString(highscoreFiles[i]) + "\n";
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
		for (int i = 0; i < highscoresList.getSize(); i++) {
			try {
				highscoresText += "\t" + highscoresList.get(i) + "\n";
			} catch (LinkedListIndexException e) {
				e.printStackTrace();
			}
		}
		highscoresList = new LinkedList();
		return highscoresText;

	}

	/*
	 * Sets the current file for high scores. Cases depend on the available sizes in the game menu!! 
	 */
	private static void setCurrentHighscoreFile(int boardSize) {

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
			System.err
					.println("Can't find highscore file for the given board size!");
		}

	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

}
