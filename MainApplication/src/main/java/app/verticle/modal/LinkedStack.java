package app.verticle.modal;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class LinkedStack implements Stack<String>{

	Node top = null;

	int size = 0;


	@Override
	public String peek() throws Exception {
		if(size == 0) throw new Exception("Cannot peek on empty stack!");
		return top.element;
	}

	@Override
	public void push(String element) {
		
		top = new Node(element, top);
		size++;

	}

	@Override
	public String pop() {
		
		Node node = top;
		top = top.next;
		return node.element;
	}


	private class Node{

		String element;
		Node next;

		public  Node(String element,Node node){
			this.element = element;
			this.next = node;
			
		}
	}

}
