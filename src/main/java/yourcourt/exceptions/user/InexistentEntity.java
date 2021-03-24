package yourcourt.exceptions.user;

import java.util.NoSuchElementException;

public class InexistentEntity extends NoSuchElementException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InexistentEntity(String s) {
		super(s + " inexistente");
	}
	

}
