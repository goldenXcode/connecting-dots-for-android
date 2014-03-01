package doublelinkedlist;

import doublelinkedlist.exceptions.InvalidDataParameterException;

public class Node {

	private Object item;
	private Node previous;
	private Node next;

	public Node() {

		item = "No data";
		next = null;
		previous = null;

	}

	public Node(Object item, Node previous, Node next)
			throws InvalidDataParameterException {

		setItem(item);
		connectNodes(previous, this);
		connectNodes(this, next);

	}

	public static void connectNodes(Node previous, Node subsequent) {

		if (previous != null && subsequent != null) {
			previous.setNext(subsequent);
			subsequent.setPrevious(previous);
		}

	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) throws InvalidDataParameterException  {
		if (item != null)
			this.item = item;
		else
			throw new InvalidDataParameterException("Invalid data input!");
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

}
