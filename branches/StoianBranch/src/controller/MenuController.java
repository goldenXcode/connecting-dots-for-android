package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import view.OutputPrinter;

import model.GameMenu;

public class MenuController {

	private static GameMenu menu;
	private static Scanner scan = new Scanner(System.in);;

	public static void setMenu(GameMenu menu){
		MenuController.menu = menu;
	}
	
	/**
	 * displays the menu. Returns true if Play option is selected, and false if
	 * Exit option is selected, otherwise it cycles the menu display
	 */
	public static boolean display() {

		String announce = "\nGame menu: ";
		String wrongInput = "You have to enter a digit as input!";
		String wrongOption = "Invalid digit for menu options!";
		int selectedOption;
		for (;;) {
			OutputPrinter.printAString(announce);
			OutputPrinter.printOptionsListForTheMenu(GameMenu.getMenuOptions());
			try {
				selectedOption = scan.nextInt();
			} catch (InputMismatchException e) {
				scan.nextLine();
				OutputPrinter.printAString(wrongInput);
				continue;
			}

			switch (selectedOption) {
			case 1:
				return true;
			case 2:
				selectSize();
				break;
			case 3:
				OutputPrinter.showHighscores();
				break;
			case 0:
				return false;
			default:
				OutputPrinter.printAString(wrongOption);
			}
		}

	}

	/*
	 * the "Select size" feature of the menu
	 */
	private static void selectSize() {
		String askForSizeOfTheGameBoard = "Please select from the given sizes below: ";
		String incorrectInput = "You have to enter a digit as input!";
		String invalidDigit = "Invalid digit for size selection!";
		int sizeSelection;
		for (;;) {
			OutputPrinter.printAString(askForSizeOfTheGameBoard);
			OutputPrinter.printOptionsListForTheMenu(GameMenu
					.getAvailableSizeOptions());
			try {
				sizeSelection = scan.nextInt();
			} catch (InputMismatchException e) {
				scan.nextLine();
				OutputPrinter.printAString(incorrectInput);
				continue;
			}

			if (sizeSelection > 0
					&& sizeSelection <= GameMenu.getAvailableSizes().length) {
				menu.setSelectedSize(sizeSelection - 1);
				return;
			} else {
				OutputPrinter.printAString(invalidDigit);
			}
		}

	}
}
