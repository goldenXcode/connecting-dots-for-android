package model;

/**
 * Class, representing the menu, loading in the beginning of the game
 * 
 * @author Home
 * 
 */
public class GameMenu {

	private static final String[] MENU_OPTIONS = { "1. Play", "2. Select size",
			"3. Show high scores", "0. Exit" };
	private static final int[] AVAILABLE_SIZES = { 5, 7, 9 };
	private static final String[] AVAILABLE_SIZE_OPTIONS;
	private static final int DEFAULT_SIZE;

	private int selectedSize;

	static {

		int sizesCount = AVAILABLE_SIZES.length;
		DEFAULT_SIZE = AVAILABLE_SIZES[0];
		AVAILABLE_SIZE_OPTIONS = new String[sizesCount];
		for (int i = 0; i < sizesCount; i++) {
			AVAILABLE_SIZE_OPTIONS[i] = (i + 1) + ". " + AVAILABLE_SIZES[i]
					+ "x" + AVAILABLE_SIZES[i];
		}

	}

	public GameMenu() {
		selectedSize = DEFAULT_SIZE;
	}

	public int getSelectedSize() {
		return selectedSize;
	}

	public void setSelectedSize(int selectedSize) {
		if (selectedSize >= 0 && selectedSize < 3)
			this.selectedSize = getAvailableSizeAt(selectedSize);
	}

	public static int getAvailableSizeAt(int index) {
		return AVAILABLE_SIZES[index];
	}

	public static int getAvailableSizesLength() {
		return AVAILABLE_SIZES.length;
	}

	public static String[] getMenuOptions() {
		return MENU_OPTIONS;
	}

	public static int[] getAvailableSizes() {
		return AVAILABLE_SIZES;
	}

	public static String[] getAvailableSizeOptions() {
		return AVAILABLE_SIZE_OPTIONS;
	}

	public static int getDefaultSize() {
		return DEFAULT_SIZE;
	}
}
