package app.verticle.modal;

/**
 * Obviously not production ready 
 * 
 * @author ramkumarsundarajan
 *
 * @param <T>
 */
public interface Stack<T> {
	
	T peek() throws Exception;
	
	void push(T element);
	
	T pop() throws Exception;

}
