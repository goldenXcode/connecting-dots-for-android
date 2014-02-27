package controller;

import model.IHandler;
import view.IDrawer;

public interface IMVC {

	/**
	 * sets the handler
	 * @param handler - object of a class implementing IHandler
	 */
	void setHandler(IHandler handler);

	/**
	 * 
	 * @return the handler
	 */
	IHandler getHandler();

	/**
	 * sets the drawer
	 * @param drawer - object of a class implementing IDrawer
	 */
	void setDrawer(IDrawer drawer);

	/**
	 * 
	 * @return the drawer
	 */
	IDrawer getDrawer();

}
