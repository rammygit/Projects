package app.verticle.modal;

import java.util.LinkedList;

/**
 * LinkedList based implementation
 * doubly linked list.
 * 
 * @author ramkumarsundarajan
 *
 */
public class LinkedListStack implements Stack<String>{
	
	private final LinkedList<String> linkedList = new LinkedList<>();

	@Override
	public String peek() throws Exception {
		return linkedList.getFirst();
	}

	@Override
	public void push(String element) {
		// TODO Auto-generated method stub
		linkedList.addFirst(element);
	}

	@Override
	public String pop() throws Exception {
		String value = peek();
		linkedList.removeFirst();
		return value;
	}

}
