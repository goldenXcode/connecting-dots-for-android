package controller;

import java.util.Scanner;

import exceptions.InvalidLineIndexingException;
import exceptions.NoSuchDirectionException;
import model.Directions;
import model.Game;
import model.GameHandler;
import model.GameMenu;
import model.Line;
import model.Player;
import view.GameDrawer;
import view.OutputPrinter;

public class GameController {

	private HandlerController handlerController;
	private Game newGame;
	private GameDrawer drawer;
	private Player playerOne;
	private Player playerTwo;
	private GameMenu menu;

	public GameController() {
		this.playerOne = new Player();
		this.playerTwo = new Player();
		this.menu = new GameMenu();
		this.newGame = new Game(playerOne, playerTwo, menu);
		MenuController.setMenu(menu);
	}

	/**
	 * From this method the game is started.
	 */
	public void play() {
		String goodByeString = "Bye bye!";
		for (;;) {
			boolean startGame = MenuController.display();
			if (!startGame) {
				break;
			}
			int gameSize = newGame.getMenu().getSelectedSize();
			String showBoardSize = "\nBoard size set to " + gameSize + "!";
			OutputPrinter.printAString(showBoardSize);

			drawer = new GameDrawer();
			newGame.setHandler(new GameHandler(gameSize));
			this.handlerController = new HandlerController(newGame.getHandler());
			playSingleGame();
		}
		OutputPrinter.printAString(goodByeString);
	}

	/**
	 * Plays a single game. And also determine which one of the players is the
	 * winner, or if the game is a tie. Saves high scores in a file!
	 */
	private void playSingleGame() {

		while (!isOver()) {
			playTurn();
		}
		if (newGame.getPlayerOne().getCurrentScore() == newGame.getPlayerTwo()
				.getCurrentScore()) {
			newGame.setWinner(null);
		} else {
			newGame.setWinner((newGame.getPlayerOne().getCurrentScore() > newGame
					.getPlayerTwo().getCurrentScore()) ? newGame.getPlayerOne()
					: newGame.getPlayerTwo());
		}
		OutputPrinter.printWinner(newGame.getWinner());
		HighScoresController.saveHighscore(newGame.getPlayerOne(), newGame
				.getMenu().getSelectedSize());
		HighScoresController.saveHighscore(newGame.getPlayerTwo(), newGame
				.getMenu().getSelectedSize());
	}

	/**
	 * Used to ask the user to choose where he wants to enter a line, based on
	 * row, column and direction. Also checks if the user has made a score.
	 * 
	 * @param p
	 *            - representing each player in the game.
	 */
	private void askForLine(Player p) {
		String askForCoordinates = p.getName()
				+ ", please enter coordinates for a line: ";
		String scoreStr = "Good job!!! You scored !";
		boolean scores;
		int emptyCellsInStartOfTurn;
		// outer:
		do {
			scores = false;
			emptyCellsInStartOfTurn = newGame.getHandler().getEmptyCellsCount();
			OutputPrinter.printAString(askForCoordinates);

			drawer.draw(newGame.getHandler());

			if (newGame.getHandler().getEmptyCellsCount() == 0) {
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
				scores = handlerController.updateGameBoard(line, p.getSign());
			} catch (InvalidLineIndexingException e) {
				System.out.println(e.getMessage());
				askForLine(p);
			}
			// checking if the player has closed more than one cell in one move.
			if (scores) {
				int numberOfPointsScored = emptyCellsInStartOfTurn
						- this.newGame.getHandler().getEmptyCellsCount();
				OutputPrinter.printAString(scoreStr);
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
		String promptForDirection = "Please enter a direction (L, R, U, D):";
		String incorrectDirection = "The direction is incorrect!\n"
				+ "Please try again.";
		try {
			for (;;) {
				OutputPrinter.printAString(promptForDirection);
				String directionText = sc.next();
				if (!directionText.equals("R") && !directionText.equals("L")
						&& !directionText.equals("U")
						&& !directionText.equals("D")) {
					OutputPrinter.printAString(incorrectDirection);
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
		String askForANumber = "Please enter a valid number for " + rowOrCol
				+ ": ";
		String incorrectNegativeNumber = "The number must be positive!\n"
				+ "Please try again.";
		String tooBugNumber = "The number must be below or equal to: "
				+ this.newGame.getHandler().getColsCount() / 2
				+ "\nPlease try again.";
		int number = -1;
		for (;;) {
			OutputPrinter.printAString(askForANumber);
			try {
				number = Integer.parseInt(sc.nextLine());
				if (number < 0) {
					OutputPrinter.printAString(incorrectNegativeNumber);
					continue;
				} else if (number > this.newGame.getHandler().getColsCount() / 2) {
					OutputPrinter.printAString(tooBugNumber);
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
		int turns = newGame.getTurns();
		++turns;
		newGame.setTurns(turns);
		if (turns % 2 != 0) {
			askForLine(newGame.getPlayerOne());
		} else {
			askForLine(newGame.getPlayerTwo());
		}

	}

	/**
	 * Determines if the game is over.
	 * 
	 * @return - If is true the game is over, else is not.
	 */
	private boolean isOver() {

		if (newGame.getHandler().getEmptyCellsCount() == 0)
			return true;
		return false;

	}
}
