package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import view.interfaces.IUserInputGetter;

public class GameInputGetter implements IUserInputGetter {

	private Scanner scan = new Scanner(System.in);

	public int displayOptions(String context, String[] options) {

		int selectedIndex = 0;

		for (;;) {
			System.out.println(context);
			for (int i = 0; i < options.length; i++) {
				System.out.println((i + 1) + ". " + options[i]);
			}
			try {
				selectedIndex = scan.nextInt() - 1;
			} catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("Please, enter a number!");
				continue;
			}
			if (selectedIndex >= 0 && selectedIndex < options.length) {
				break;
			}
			System.out.println("Please enter a valid number!");
		}
		return selectedIndex;

	}

	public int askForNumber(String question) {

		int result;

		for (;;) {
			System.out.println(question);
			try {
				result = scan.nextInt();
			} catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("Please, enter a number!");
				continue;
			}
			break;
		}

		return result;

	}

	public String askForText(String question) {

		String result;

		System.out.println(question);
		result = scan.nextLine();

		return result;

	}

}
