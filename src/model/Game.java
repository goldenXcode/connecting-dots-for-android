package model;

import model.interfaces.IGame;

public class Game implements IGame {

	private int turns;
	private int gameSize;
	private int unscoredCells;

	public Game() {
		setGameSize(DEFAULT_SIZE_INDEX);
		turns = 0;
	}

	public void increaseTurn() {
		this.turns++;
	}

	public int getTurn() {
		return this.turns;
	}

	public void setGameSize(int sizeIndex) {
		if (sizeIndex >= 0 && sizeIndex < AVAILABLE_SIZES.length) {
			this.gameSize = AVAILABLE_SIZES[sizeIndex];
			unscoredCells = (int) Math.pow(gameSize, 2);
		}
	}

	public int getGameSize() {
		return this.gameSize;
	}

	@Override
	public int getUnscoredCells() {
		return unscoredCells;
	}

	@Override
	public void decreaseUnscoredCells() {
		unscoredCells--;

	}

}
