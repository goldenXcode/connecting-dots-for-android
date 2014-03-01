package model;

import controller.InputController;

/**
 * Class, representing the player in the game with name, sign and current score
 * 
 * @author random.org
 * 
 */
public class Player {

	private static final String[] PLAYER_SIGNS = { "X", "O", "^", "*" };
	private static int previousIconChoice = Integer.MIN_VALUE;
	private static final int MAXIMUM_AVAILABLE_SIGN_INDEX = 4;
	private static final int MINIMUM_AVAILABLE_SIGN_INDEX = 1;

	private String name;
	private String sign;
	private int currentScore;

	public Player() {
		InputController.askForName(this);
		InputController.askForSign(this);
	}

	/**
	 * 
	 * @return the player name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setting a name to the current player.
	 * 
	 * @param name
	 *            - name of the player.
	 */
	public void setName(String name) {
		if (name != null && !name.equals(""))
			this.name = name;
	}

	/**
	 * 
	 * @return the player sign.
	 */
	public String getSign() {
		return sign;
	}

	public static String getPlayerSigns(int index) {
		if (index >= 0 && index < PLAYER_SIGNS.length)
			return PLAYER_SIGNS[index];
		return "";
	}

	public void setPlayerSign(int signIndex) {
		if (signIndex >= 0 && signIndex < PLAYER_SIGNS.length)
			this.sign = PLAYER_SIGNS[signIndex];
	}

	/**
	 * 
	 * @return the player score.
	 */
	public int getCurrentScore() {
		return currentScore;
	}

	public static int getPlayerSignsCount() {
		return PLAYER_SIGNS.length;
	}

	/**
	 * Increments the current score of the player
	 */
	public void increaseScore(int score) {
		currentScore += score;
	}

	public static int getPreviousIconChoice() {
		return previousIconChoice;
	}

	public static void setPreviousIconChoice(int previousIconChoice) {
		if (previousIconChoice >= 0
				&& previousIconChoice < Player.PLAYER_SIGNS.length)
			Player.previousIconChoice = previousIconChoice;
	}

	public static int getMaximumAvailableSignIndex() {
		return MAXIMUM_AVAILABLE_SIGN_INDEX;
	}

	public static int getMinimumAvailableSignIndex() {
		return MINIMUM_AVAILABLE_SIGN_INDEX;
	}

}