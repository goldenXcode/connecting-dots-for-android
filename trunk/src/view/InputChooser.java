package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.Player;

public class InputChooser {

	static int previousIconChoice = Integer.MIN_VALUE;
	
	   /**
     * Asking the player to enter his name.
     */
    public void askForName(Player player) {
            Scanner sc = new Scanner(System.in);
            String name = " ";
            do {
                    System.out.println("Please enter your name: ");
                    name = sc.nextLine();
            } while (name == null || name.length() == 0);
            player.setName(name);
    }
    
    /**
     * Asking the player to choose a sign for the current game.
     */
     public void askForSign(Player player) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Available signs: \n" );
            for(int i = 0; i < Player.getPlayerSignsCount(); i++){
            	System.out.print((i+1) + ". " + Player.getPlayerSigns(i) + "\t");
            }
            System.out.print("Select your sign:");
            try {
            	//Have to add validation for input using new IndexOutOfBoundsException
                    int choice = scan.nextInt();
                    player.setSign(choice-1);
            } catch (InputMismatchException e) {
                    scan.nextLine();
                    System.out.println("Please, enter a digit!");
                    askForSign(player);
            }
    }

}
