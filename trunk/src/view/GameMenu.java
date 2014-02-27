package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.Game;


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

	private static Scanner scan = new Scanner(System.in);

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

	/**
	 * displays the menu. Returns true if Play option is selected, and false if
	 * Exit option is selected, otherwise it cycles the menu display
	 */
	public boolean display() {

		int selectedOption;
		for (;;) {
			System.out.println("Game menu: ");
			printOptionsList(MENU_OPTIONS);
			try {
				selectedOption = scan.nextInt();
			} catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("You have to enter a digit as input!");
				continue;
			}

			switch (selectedOption) {
			case 1:
				return true;
			case 2:
				selectSize();
				break;
			case 3:
				showHighscores();
				break;
			case 0:
				return false;
			default:
				System.out.println("Invalid digit for menu options!");
			}
		}

	}

	/*
	 * prints a String array in the console
	 */
	private void printOptionsList(String[] array) {

		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
		System.out.print("Select option: ");

	}

	/*
	 * the "Select size" feature of the menu
	 */
	private void selectSize() {

		int sizeSelection;
		for (;;) {
			System.out.println("Please select from the given sizes below: ");
			printOptionsList(AVAILABLE_SIZE_OPTIONS);
			try {
				sizeSelection = scan.nextInt();
			} catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("You have to enter a digit as input!");
				continue;
			}

			if (sizeSelection > 0 && sizeSelection <= AVAILABLE_SIZES.length) {
				selectedSize = AVAILABLE_SIZES[sizeSelection - 1];
				return;
			} else {
				System.out.println("Invalid digit for size selection!");
			}
		}

	}

	/*
	 * the "Show high scores" feature of the menu
	 */
	public void showHighscores() {

		System.out
				.println("\n\tHIGHSCORES:\n\n" + Game.allHighscoresToString());

	}

	public int getSelectedSize() {

		return selectedSize;

	}

	public static int getAvailableSizeAt(int index) {
		return AVAILABLE_SIZES[index];
	}

	public static int getAvailableSizesLength() {
		return AVAILABLE_SIZES.length;
	}

}
