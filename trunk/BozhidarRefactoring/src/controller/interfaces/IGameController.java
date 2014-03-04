package controller.interfaces;

import view.interfaces.IMatrixDrawer;
import view.interfaces.IUserInputGetter;
import view.interfaces.IUserOutputPrinter;
import model.interfaces.IGame;
import model.interfaces.IGameBoardHandler;
import model.interfaces.IHighscoreFileHandler;
import model.interfaces.IPlayer;

public interface IGameController {

	void play(IPlayer playerOne, IPlayer playerTwo);

	void setGameBoardHandler(IGameBoardHandler gameBoardHandler);

	void setHighscoreHandler(IHighscoreFileHandler highscoreHandler);

	void setGame(IGame game);

	void setUserInputView(IUserInputGetter userInputView);

	void setUserOutputView(IUserOutputPrinter userOutputView);

	void setGameBoardDrawer(IMatrixDrawer gameBoardDrawer);

}
