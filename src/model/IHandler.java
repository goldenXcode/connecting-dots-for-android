package model;

public interface IHandler {

	/**
	 * 
	 * @return rows count
	 */
	int getRowsCount();

	/**
	 * 
	 * @return columns count
	 */
	int getColsCount();

	/**
	 * 
	 * @param row
	 *            - the row of the element for printing
	 * @param col
	 *            - the column of the element for printing
	 * @return the String for printing
	 */
	String getStringForRowAndCol(int row, int col);

}
