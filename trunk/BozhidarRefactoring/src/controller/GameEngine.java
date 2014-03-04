package controller;

import controller.interfaces.IGameController;
import exceptions.InvalidLineIndexingException;
import exceptions.NoSuchDirectionException;

import view.interfaces.IMatrixDrawer;
import view.interfaces.IUserInputGetter;
import view.interfaces.IUserOutputPrinter;
import model.Directions;
import model.interfaces.IGame;
import model.interfaces.IHighscoreFileHandler;
import model.interfaces.IPlayer;
import model.interfaces.IGameBoardHandler;

public class GameEngine implements IGameController {

	IPlayer playerOne, playerTwo;
	IGameBoardHandler gameBoardHandler;
	IHighscoreFileHandler highscoreHandler;
	IGame game;

	IUserInputGetter userInputView;
	IUserOutputPrinter userOutputView;
	IMatrixDrawer gameBoardDrawer;

	public GameEngine() {

	}

	/**
	 * From this method the game is started.
	 */
	public void play(IPlayer playerOne, IPlayer playerTwo) {

		this.playerOne = playerOne;
		this.playerTwo = playerTwo;

		boolean play = true;
		do {
			int menuSelection = userInputView.displayOptions("GAME MENU",
					IGame.MENU_OPTIONS);
			switch (menuSelection) {
			case 0:
				playSingleGame();
				break;
			case 1:
				selectGameSize();
				break;
			case 2:
				showAllHighscores();
				break;
			case 3:
				play = false;
				break;
			}
		} while (play);
		userOutputView.print("Bye bye!");
	}

	private String generateHighscoreText(int gameSize) {

		return String.valueOf(gameSize) + "x" + String.valueOf(gameSize);
	}

	private String generateHighscoreFileName(int gameSize) {

		return IHighscoreFileHandler.FILENAME_BASE
				+ generateHighscoreText(gameSize)
				+ IHighscoreFileHandler.fileExtension;
	}

	private void showAllHighscores() {

		for (int i = 0; i < IGame.AVAILABLE_SIZES.length; i++) {
			String fileName = generateHighscoreFileName(IGame.AVAILABLE_SIZES[i]);
			highscoreHandler.getDataFromSource(fileName);
			String[] highscores = highscoreHandler.getAllData();
			userOutputView.print("Highscores for game board: "
					+ generateHighscoreText(IGame.AVAILABLE_SIZES[i]),
					highscores);
		}

	}

	private void selectGameSize() {

		String[] sizeOptions = sizesToString();
		int sizeSelection = userInputView.displayOptions(
				"Please select from the given sizes below: ", sizeOptions);
		game.setGameSize(sizeSelection);

	}

	private String[] sizesToString() {
		String[] result = new String[IGame.AVAILABLE_SIZES.length];
		for (int i = 0; i < IGame.AVAILABLE_SIZES.length; i++) {
			result[i] = String.valueOf(IGame.AVAILABLE_SIZES[i]) + "x"
					+ String.valueOf(IGame.AVAILABLE_SIZES[i]);
		}
		return result;
	}

	/**
	 * Plays a single game. And also determine which one of the players is the
	 * winner, or if the game is a tie. Saves high scores in a file!
	 */
	private void playSingleGame() {

		gameBoardHandler.setBoard(game.getGameSize() * 2 + 1);
		userOutputView.print("You are playing with game board "
				+ generateHighscoreText(game.getGameSize()));
		initializeGameBoard();
		while (!isOver()) {
			playTurn();
		}
		if (playerOne.getCurrentScore() > playerTwo.getCurrentScore()) {
			userOutputView.print(playerOne.getName()
					+ " is a winner with score of: "
					+ playerOne.getCurrentScore());
		} else if (playerOne.getCurrentScore() < playerTwo.getCurrentScore()) {
			userOutputView.print(playerTwo.getName()
					+ " is a winner with score of: "
					+ playerTwo.getCurrentScore());
		} else if (playerOne.getCurrentScore() == playerTwo.getCurrentScore()) {
			userOutputView.print("The game is a draw! Both players scored "
					+ playerOne.getCurrentScore() + " times.");
		}
		saveHighscore(playerOne);
		saveHighscore(playerTwo);
	}

	/**
	 * Used to ask the user to choose where he wants to enter a line, based on
	 * row, column and direction. Also checks if the user has made a score.
	 * 
	 * @param p
	 *            - representing each player in the game.
	 */
	private void playTurn(IPlayer p) {

		boolean scores;
		int unscoredCellsInStartOfTurn;
		do {
			scores = false;
			unscoredCellsInStartOfTurn = game.getUnscoredCells();

			gameBoardDrawer.draw(gameBoardHandler.getMatrix());
			userOutputView.print(p.getName() + ", it's your turn: \n");

			Directions selectedDirection;
			int selectedRow = -1;
			int selectedCol = -1;
			for (;;) {
				selectedRow = askForIndex("row");
				selectedCol = askForIndex("col");
				try {
					selectedDirection = enterDirection();
					break;
				} catch (NoSuchDirectionException e) {
					userOutputView.print(e.getMessage());
				}
			}
			try {
				scores = updateGameBoard(selectedRow, selectedCol,
						selectedDirection, p.getSign());
			} catch (InvalidLineIndexingException e) {
				userOutputView.print(e.getMessage());
				playTurn(p);
			}
			if (scores) {
				int numberOfPointsScored = unscoredCellsInStartOfTurn
						- game.getUnscoredCells();
				p.increaseScore(numberOfPointsScored);
				userOutputView
						.print("Good job!!! You scored ! Your current score is "
								+ p.getCurrentScore()
								+ ". Total unscored cells left: "
								+ game.getUnscoredCells());
			}
		} while (scores && !isOver());

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
	private Directions enterDirection() throws NoSuchDirectionException {
		Directions selectedDirection = null;
		for (;;) {
			String directionText = userInputView
					.askForText("Please enter a direction (L, R, U, D): ");

			try {
				selectedDirection = Directions.valueOf(directionText);
			} catch (IllegalArgumentException e) {
				userOutputView
						.print("The direction you've entered is incorrect! Please try again.");
				continue;
			}
			break;
		}
		return selectedDirection;
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

	private int askForIndex(String item) {
		int number = -1;
		for (;;) {
			number = userInputView
					.askForNumber("Please enter a valid number for " + item
							+ ": ");
			if (number < 0) {
				userOutputView
						.print("The number must be positive! Please try again.");
				continue;
			} else if (number > gameBoardHandler.getSize() / 2) {
				userOutputView.print("The number must be below or equal to: "
						+ gameBoardHandler.getSize() / 2
						+ "! Please try again.");
				continue;
			}
			break;
		}
		return number;
	}

	/**
	 * Plays a single turn.
	 */
	private void playTurn() {

		game.increaseTurn();
		if (game.getTurn() % 2 != 0) {
			playTurn(playerOne);
		} else {
			playTurn(playerTwo);
		}

	}

	/**
	 * Determines if the game is over.
	 * 
	 * @return - If is true the game is over, else is not.
	 */
	private boolean isOver() {

		if (game.getUnscoredCells() == 0)
			return true;
		return false;

	}

	/**
	 * Saving high scores in a file, if there is one. Returns
	 * 
	 * @param player
	 * @param boardSize
	 * @return if the player has made it to the high scores
	 */
	private boolean saveHighscore(IPlayer player) {

		String fileName = generateHighscoreFileName(game.getGameSize());
		highscoreHandler.getDataFromSource(fileName);
		int newScore = player.getCurrentScore();
		String name = player.getName();
		String newScoreLine = generateScoreTextLine(name, newScore);
		boolean playerHasHighscore = updateHighscoresList(newScore,
				newScoreLine);
		highscoreHandler.writeDataToSource(fileName);
		return playerHasHighscore;

	}

	private String generateScoreTextLine(String playerName, int playerScore) {

		return "Name: " + playerName + " -> Score: " + playerScore;

	}

	/*
	 * returns if the given score has made it to the high scores
	 */
	private boolean updateHighscoresList(int newScore, String newScoreLine) {

		if (!highscoreHandler.contains(newScoreLine)) {
			boolean inserted = false;
			for (int i = 0; i < highscoreHandler.getSize(); i++) {
				String scoreLine = highscoreHandler.getAt(i);
				String scoreText = scoreLine.substring(scoreLine.length()
						- IHighscoreFileHandler.MAXIMUM_DIGITS_OF_SCORE,
						scoreLine.length());
				int score = Integer
						.parseInt(scoreText.replaceAll("[^0-9]", ""));
				if (newScore >= score) {
					highscoreHandler.insert(i, newScoreLine);
					inserted = true;
					break;
				}
			}
			int lastIndex = highscoreHandler.getSize() - 1;
			if (!inserted) {
				highscoreHandler.insert(lastIndex, newScoreLine);
			}
			if (highscoreHandler.getSize() > IHighscoreFileHandler.MAXIMUM_NUMBER_OF_HIGHSCORES) {
				highscoreHandler.remove(lastIndex);
			}
		}
		if (highscoreHandler.contains(newScoreLine)) {
			return true;
		}
		return false;

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
	private boolean updateGameBoard(int rowIndex, int colIndex,
			Directions direction, String sign)
			throws InvalidLineIndexingException {

		String verticalLine = "|";
		String horizontalLine = "-";

		int row = (rowIndex * 2);
		int col = (colIndex * 2);

		try {
			switch (direction) {
			case U:
				row -= 1;
				fillLine(verticalLine, row, col);
				break;
			case D:
				row += 1;
				fillLine(verticalLine, row, col);
				break;
			case R:
				col += 1;
				fillLine(horizontalLine, row, col);
				break;
			case L:
				col -= 1;
				fillLine(horizontalLine, row, col);
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidLineIndexingException(
					"\nThis direction is getting out of the board's bounds!\nPlease try again.",
					e);
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
		if (gameBoardHandler.get(row, col).equals(" ")) {
			gameBoardHandler.set(row, col, line);
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

		int startingEmptyCells = game.getUnscoredCells();

		/*
		 * if player has filed horizontal line
		 */
		if (row % 2 == 0) {

			// check if there is up cell
			if (row > 0) {

				if (!gameBoardHandler.get(row - 2, col).equals(" ")
						&& !gameBoardHandler.get(row - 1, col - 1).equals(" ")
						&& !gameBoardHandler.get(row - 1, col + 1).equals(" ")) {

					// fill player sign
					fillPlayerSign(row - 1, col, sign);
				}
			}

			// check if there is down cell
			if (row < gameBoardHandler.getSize() - 1) {

				if (!gameBoardHandler.get(row + 2, col).equals(" ")
						&& !gameBoardHandler.get(row + 1, col - 1).equals(" ")
						&& !gameBoardHandler.get(row + 1, col + 1).equals(" ")) {

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
				if (!gameBoardHandler.get(row - 1, col - 1).equals(" ")
						&& !gameBoardHandler.get(row, col - 2).equals(" ")
						&& !gameBoardHandler.get(row + 1, col - 1).equals(" ")) {

					// fill player sign
					fillPlayerSign(row, col - 1, sign);
				}
			}

			// check if there is right cell
			if (col < gameBoardHandler.getSize() - 1) {
				if (!gameBoardHandler.get(row - 1, col + 1).equals(" ")
						&& !gameBoardHandler.get(row, col + 2).equals(" ")
						&& !gameBoardHandler.get(row + 1, col + 1).equals(" ")) {

					// fill player sign
					fillPlayerSign(row, col + 1, sign);
				}
			}
		}

		return (startingEmptyCells == game.getUnscoredCells()) ? false : true;
	}

	private void fillPlayerSign(int row, int col, String sign) {
		gameBoardHandler.set(row, col, sign);
		game.decreaseUnscoredCells();
	}

	/**
	 * Initializes the initial state of the gameBoard field.
	 */
	private void initializeGameBoard() {
		String point = ".";
		String space = " ";

		for (int i = 0; i < gameBoardHandler.getSize(); i++) {
			for (int j = 0; j < gameBoardHandler.getSize(); j++) {
				if (i % 2 == 0 && j % 2 == 0) {
					gameBoardHandler.set(i, j, point);
				} else {
					gameBoardHandler.set(i, j, space);
				}
			}
		}
	}

	public void setGameBoardHandler(IGameBoardHandler gameBoardHandler) {
		if (gameBoardHandler != null)
			this.gameBoardHandler = gameBoardHandler;
	}

	public void setHighscoreHandler(IHighscoreFileHandler highscoreHandler) {
		if (highscoreHandler != null)
			this.highscoreHandler = highscoreHandler;
	}

	public void setGame(IGame game) {
		if (game != null)
			this.game = game;
	}

	public void setUserInputView(IUserInputGetter userInputView) {
		if (userInputView != null)
			this.userInputView = userInputView;
	}

	public void setUserOutputView(IUserOutputPrinter userOutputView) {
		if (userOutputView != null)
			this.userOutputView = userOutputView;
	}

	public void setGameBoardDrawer(IMatrixDrawer gameBoardDrawer) {
		if (gameBoardDrawer != null)
			this.gameBoardDrawer = gameBoardDrawer;
	}

}
