package tests;

import view.GameInputGetter;
import view.GameInterfaceDrawer;
import view.GameOutputPrinter;
import view.interfaces.IMatrixDrawer;
import view.interfaces.IUserInputGetter;
import view.interfaces.IUserOutputPrinter;
import model.Game;
import model.GameBoardHandler;
import model.HighscoresHandler;
import model.Player;
import model.interfaces.IGame;
import model.interfaces.IGameBoardHandler;
import model.interfaces.IHighscoreFileHandler;
import model.interfaces.IPlayer;
import controller.GameEngine;
import controller.interfaces.IGameController;

public class GameTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		IGame game = new Game();
		IGameBoardHandler boardHandler = new GameBoardHandler();
		IHighscoreFileHandler highscoreHandler = new HighscoresHandler();
		
		IUserInputGetter userInputView = new GameInputGetter();
		IUserOutputPrinter userOutputView = new GameOutputPrinter();
		IMatrixDrawer gameBoardDrawer = new GameInterfaceDrawer();
		
		IPlayer one = new Player("Bechko", "X");
		IPlayer two = new Player("Sechko", "O");		
		
		IGameController controller = new GameEngine();
		controller.setGame(game);
		controller.setGameBoardHandler(boardHandler);
		controller.setHighscoreHandler(highscoreHandler);
		controller.setUserInputView(userInputView);
		controller.setUserOutputView(userOutputView);
		controller.setGameBoardDrawer(gameBoardDrawer);
		controller.play(one, two);

	}

}
