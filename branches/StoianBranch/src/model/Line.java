package model;

import exceptions.NoSuchDirectionException;

/**
 * Class, representing a line in the game with coordinates and direction
 * 
 * @author Home
 * 
 */
public class Line {

	private int row = -1;
	private int col = -1;
	private Directions direction = null;

	public Line(int row, int col, Directions direction)
			throws NoSuchDirectionException {
		this.setRow(row);
		this.setCol(col);
		this.setDirection(direction);
	}

	/**
	 * 
	 * @return current row number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setting the row number with the parameter row.
	 * 
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * 
	 * @return current column number.
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Setting the column number with the parameter col.
	 * 
	 * @param col
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * 
	 * @return current direction.
	 */
	public Directions getDirection() {
		return direction;
	}

	/**
	 * Setting the direction for the current line.
	 * 
	 * @param direction
	 *            - choose the direction for the line. It can be R,L,U,D.
	 * @throws NoSuchDirectionException
	 *             - when the direction is not one of the above.
	 */
	public void setDirection(Directions direction)
			throws NoSuchDirectionException {

		if (direction == null
				|| ((!direction.equals(Directions.L))
						&& (!direction.equals(Directions.R))
						&& (!direction.equals(Directions.U)) && (!direction
							.equals(Directions.D)))) {
			throw new NoSuchDirectionException("Invalid direction!");
		} else {
			this.direction = direction;
		}

	}

}
