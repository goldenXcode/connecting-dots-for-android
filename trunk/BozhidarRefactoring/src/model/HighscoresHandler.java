package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import utils.doublelinkedlist.exceptions.LinkedListIndexException;
import utils.doublelinkedlist.exceptions.InvalidDataParameterException;
import model.interfaces.IHighscoreFileHandler;
import utils.doublelinkedlist.LinkedList;

public class HighscoresHandler implements IHighscoreFileHandler {

	private static LinkedList highscoresList;

	@Override
	public void getDataFromSource(String sourceFile) {

		highscoresList = new LinkedList();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(sourceFile));
			String textLine = reader.readLine();

			while (textLine != null) {
				try {
					highscoresList.add(textLine);
					textLine = reader.readLine();
				} catch (InvalidDataParameterException | IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void insert(int index, String item) {

		try {
			highscoresList.insertAt(index, item);
		} catch (LinkedListIndexException | InvalidDataParameterException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void writeDataToSource(String destinationFile) {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(destinationFile));
			for (int i = 0; i < highscoresList.getSize(); i++) {
				try {
					writer.write(highscoresList.get(i).toString());
					if (i != highscoresList.getSize() - 1) {
						writer.write(System.getProperty("line.separator"));
					}
				} catch (LinkedListIndexException | IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void remove(int index) {

		try {
			highscoresList.remove(index);
		} catch (LinkedListIndexException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String[] getAllData() {
		String[] highscoresText = new String[highscoresList.getSize()];
		for (int i = 0; i < highscoresList.getSize(); i++) {
			try {
				highscoresText[i] = (String) highscoresList.get(i);
			} catch (LinkedListIndexException e) {
				e.printStackTrace();
			}
		}
		return highscoresText;
	}

	@Override
	public boolean contains(String item) {
		return highscoresList.contains(item);
	}

	@Override
	public int getSize() {
		return highscoresList.getSize();
	}

	@Override
	public String getAt(int index) {
		try {
			return (String) highscoresList.get(index);
		} catch (LinkedListIndexException e) {
			e.printStackTrace();
		}
		return "";
	}

}
