package yourcourt.exceptions;

public class AttributeAlreadyExists extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttributeAlreadyExists(String attr) {
		super(attr+" ya existe");
	}
	

}
