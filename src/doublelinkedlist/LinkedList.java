package doublelinkedlist;

import doublelinkedlist.exceptions.InvalidDataParameterException;
import doublelinkedlist.exceptions.LinkedListIndexException;

public class LinkedList {

	private int size;
	private Node start;
	private Node end;

	public LinkedList() {

		start = new Node();
		try {
			end = new Node("No data!", start, null);
		} catch (InvalidDataParameterException e) {
			System.out.println("You shouldn't be able to get here! :O");
		}
		size = 0;

	}

	public void add(Object item) throws InvalidDataParameterException {

		if (item != null) {
			Node currentLastNode = end.getPrevious();
			@SuppressWarnings("unused")
			Node newNode = new Node(item, currentLastNode, end);
			size++;
		}

	}

	public void insertAt(int index, Object item)
			throws LinkedListIndexException, InvalidDataParameterException {

		if (item != null) {
			try {
				if (index == size) {
					add(item);
				} else {
					Node previousNode = getNodeBeforeIndex(index);
					Node oldNodeAtIndex = previousNode.getNext();
					@SuppressWarnings("unused")
					Node newNode = new Node(item, previousNode, oldNodeAtIndex);
					size++;
				}
			} catch (NullPointerException e) {
				throw new LinkedListIndexException(
						"Index out of linked list's bounds!", e);
			}
		}

	}

	public void printAllElements() {
		Node nodeIterator = start;
		for (int i = 0; i < size; i++) {
			nodeIterator = nodeIterator.getNext();
			System.out.println(nodeIterator.getItem());
		}
		System.out.println();
	}

	public void remove(int index) throws LinkedListIndexException {

		if (size != 0) {
			try {
				Node previousNode = getNodeBeforeIndex(index);
				Node nodeAfterIndex = previousNode.getNext().getNext();
				Node.connectNodes(previousNode, nodeAfterIndex);
				size--;
			} catch (NullPointerException e) {
				throw new LinkedListIndexException(
						"Index out of linked list's bounds!", e);
			}
		} else {
			throw new LinkedListIndexException("No elements in the list!");
		}

	}

	public Object get(int index) throws LinkedListIndexException {

		if (size != 0) {
			try {
				Node previousNode = getNodeBeforeIndex(index);
				return previousNode.getNext().getItem();
			} catch (NullPointerException e) {
				throw new LinkedListIndexException(
						"Index out of linked list's bounds!", e);
			}
		} else {
			throw new LinkedListIndexException("No elements in the list!");
		}

	}

	public boolean contains(Object obj) {

		Node nodeIterator = start.getNext();
		for (int i = 0; i < size; i++) {
			if (nodeIterator.getItem().equals(obj))
				return true;
			nodeIterator = nodeIterator.getNext();
		}
		return false;

	}

	private Node getNodeBeforeIndex(int index) {

		if (index <= size / 2 && index >= 0) {
			Node nodeIterator = start;
			for (int i = 0; i < index; i++) {
				nodeIterator = nodeIterator.getNext();
			}
			return nodeIterator;
		} else if (index < size) {
			Node nodeIterator = end;
			for (int i = size + 1; i > index; i--) {
				nodeIterator = nodeIterator.getPrevious();
			}
			return nodeIterator;
		} else {
			return null;
		}

	}
	
	

	public int getSize() {

		return size;

	}

}
