package model;

import model.interfaces.IPlayer;

public class Player implements IPlayer {

	private String name;
	private String sign;
	private int currentScore;

	public Player(String name, String sign) {

		setName(name);
		setSign(sign);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && !name.equals(""))
			this.name = name;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		if (sign != null && !sign.equals(""))
			this.sign = sign;
	}

	public void increaseScore(int score) {
		if (score > 0) {
			currentScore += score;
		}
	}

	public int getCurrentScore() {
		return currentScore;
	}

}
