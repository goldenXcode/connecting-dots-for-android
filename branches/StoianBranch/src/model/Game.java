package model;

/**
 * The main class of the project, representing the game. It has a menu, MVC, and
 * two players in it. Also it counts the turns for each player. Based upon
 * everything it starts the game.
 * 
 * @author Home
 * 
 */
public class Game {

	private GameMenu menu;
	private GameHandler handler;
	private Player playerOne, playerTwo, winner;
	private int turns;

	public Game(Player playerOne, Player playerTwo, GameMenu menu) {
		setMenu(menu);
		setPlayerOne(playerOne);
		setPlayerTwo(playerTwo);
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		if (playerOne != null)
			this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		if (playerTwo != null)
			this.playerTwo = playerTwo;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public GameHandler getHandler() {
		return handler;
	}

	/**
	 * Setting a game handler.
	 */
	public void setHandler(IHandler handler) {
		if (handler != null && handler instanceof GameHandler) {
			this.handler = (GameHandler) handler;
		}
	}

	public GameMenu getMenu() {
		return menu;
	}

	public void setMenu(GameMenu menu) {
		this.menu = menu;
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}
}
