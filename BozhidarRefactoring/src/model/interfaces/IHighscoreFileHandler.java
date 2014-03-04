package model.interfaces;

public interface IHighscoreFileHandler {

	static final int MAXIMUM_NUMBER_OF_HIGHSCORES = 10;
	static final int MAXIMUM_DIGITS_OF_SCORE = 2;
	static final String FILENAME_BASE = "highscores_";
	static final String fileExtension = ".txt";

	void getDataFromSource(String source);

	void insert(int index, String item);

	void remove(int index);
	
	boolean contains(String item);

	void writeDataToSource(String destination);

	String[] getAllData();
	
	int getSize();
	
	String getAt(int index);

}
