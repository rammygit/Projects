package app.verticle.modal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class Whisky {

	private static final AtomicInteger COUNTER = new AtomicInteger();

	private int id;

	private String name;

	private String origin;

	public Whisky(String name, String origin) {
		this.id = COUNTER.getAndIncrement();
		this.name = name;
		this.origin = origin;
	}
	
	public Whisky(int id,String name,String origin){
		this(name,origin);
		this.id = id;
	}

	public Whisky() {
		this.id = COUNTER.getAndIncrement();
	}

	public String getName() {
		return name;
	}

	public String getOrigin() {
		return origin;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

}
