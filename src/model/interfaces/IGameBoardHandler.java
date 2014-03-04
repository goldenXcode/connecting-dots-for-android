package model.interfaces;

public interface IGameBoardHandler {

	int getSize();

	String get(int row, int col);

	void set(int row, int col, String c);

	String[][] getMatrix();
	
	void setBoard(int size);

}
