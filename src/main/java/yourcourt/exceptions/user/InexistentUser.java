package yourcourt.exceptions.user;

import java.util.NoSuchElementException;

public class InexistentUser extends NoSuchElementException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InexistentUser() {
		super("Inexistent user");
	}
	

}
