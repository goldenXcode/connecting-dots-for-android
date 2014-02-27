package model;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class, representing the player in the game with name, sign and current score
 * 
 * @author random.org
 * 
 */

public class Player {

	private static final String[] PLAYER_SIGNS = { "X", "O", "^", "*" };

	private String name;
	private String sign;
	private int currentScore;

	public Player(String name, int sign) {
		setName(name);
		setSign(sign);
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
	 * Increments the current score of the player
	 */
	public void increaseScore(int score) {
		currentScore += score;
	}

	/**
	 * 
	 * @return the player name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return the player sign.
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * 
	 * @return the player score.
	 */
	public int getCurrentScore() {
		return currentScore;
	}

	public void setSign(int signIndex) {
		if (signIndex >= 0 && signIndex < PLAYER_SIGNS.length)
			this.sign = PLAYER_SIGNS[signIndex];
	}

	public static String getPlayerSigns(int index) {
		if (index >= 0 && index < PLAYER_SIGNS.length)
			return PLAYER_SIGNS[index];
		return "";
	}
	
	public static int getPlayerSignsCount(){
		return PLAYER_SIGNS.length;
	}

}