package view;

import model.IHandler;

public interface IDrawer {

	/**
	 * 
	 * draws with a given handler
	 * @param handler - Object of a class implementing IHanlder
	 */
	void draw(IHandler handler);
	
}
