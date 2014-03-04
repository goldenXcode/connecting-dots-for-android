package model.interfaces;

public interface IGame {

	static final String[] MENU_OPTIONS = { "Play", "Select size",
			"Show high scores", "Exit" };
	static final int[] AVAILABLE_SIZES = { 5, 7, 9 };
	static final int DEFAULT_SIZE_INDEX = 0;

	int getTurn();

	void increaseTurn();

	int getGameSize();

	void setGameSize(int sizeIndex);

	int getUnscoredCells();

	void decreaseUnscoredCells();

}
