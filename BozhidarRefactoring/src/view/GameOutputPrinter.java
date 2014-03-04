package view;

import view.interfaces.IUserOutputPrinter;

public class GameOutputPrinter implements IUserOutputPrinter {

	@Override
	public void print(String text) {

		System.out.println(text);

	}

	@Override
	public void print(String context, String[] list) {

		System.out.println(context);
		for (int i = 0; i < list.length; i++) {
			System.out.println("\t" + list[i]);
		}
		System.out.println();

	}

}
