package yourcourt.exceptions.user;

import java.util.NoSuchElementException;

public class InexistentCourt extends NoSuchElementException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InexistentCourt() {
		super("Inexistent court");
	}
	

}
