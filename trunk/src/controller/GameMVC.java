package controller;

import view.GameDrawer;
import view.IDrawer;
import model.GameHandler;
import model.IHandler;

/**
 * Class, representing the MVC for the game.
 * 
 * @author Home
 * 
 */
public class GameMVC implements IMVC {

	private GameHandler handler;
	private GameDrawer drawer;

	/**
	 * Setting a game drawer.
	 */
	@Override
	public void setDrawer(IDrawer drawer) {
		if (drawer != null && drawer instanceof GameDrawer) {
			this.drawer = (GameDrawer) drawer;
		}
	}

	/**
	 * Setting a game handler.
	 */
	@Override
	public void setHandler(IHandler handler) {
		if (handler != null && handler instanceof GameHandler) {
			this.handler = (GameHandler) handler;
		}
	}

	@Override
	public GameHandler getHandler() {

		return handler;

	}

	@Override
	public GameDrawer getDrawer() {

		return drawer;

	}

}
