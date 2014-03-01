package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import view.OutputPrinter;

import model.Player;

public class InputController {

	/**
	 * Asking the player to enter his name.
	 */
	public static void askForName(Player player) {
		Scanner sc = new Scanner(System.in);
		String askForName = "Please enter your name: ";
		String name = "";
		do{
			OutputPrinter.printAString(askForName);
			name = sc.nextLine();
		}while (name == null || name.length() == 0); 
		player.setName(name);
	}

	/**
	 * Asking the player to choose a sign for the current game.
	 */
	public static void askForSign(Player player) {
		Scanner sc = new Scanner(System.in);
		String chooseAnothrSign = "This sign is already taken!\n"
				+ "Please choose a new one.";
		String typeMissMatch = "Please, enter a digit!";
		String invalidChoice = "Please enter a valid number from 1 to 4.";
		OutputPrinter.printAvailableSigns();
		try {
			int choice = sc.nextInt();
			if (choice < Player.getMinimumAvailableSignIndex()
					|| choice > Player.getMaximumAvailableSignIndex()) {
				throw new IndexOutOfBoundsException();
			}
			if (Player.getPreviousIconChoice() == Integer.MIN_VALUE) {
				Player.setPreviousIconChoice(choice - 1);
				player.setPlayerSign(choice - 1);
			} else if (Player.getPreviousIconChoice() == (choice - 1)) {
				OutputPrinter.printAString(chooseAnothrSign);
				askForSign(player);
			} else {
				player.setPlayerSign(choice - 1);
			}
		} catch (InputMismatchException e) {
			OutputPrinter.printAString(typeMissMatch);
			askForSign(player);
		} catch (IndexOutOfBoundsException e) {
			OutputPrinter.printAString(invalidChoice);
			askForSign(player);
		}
	}
}
