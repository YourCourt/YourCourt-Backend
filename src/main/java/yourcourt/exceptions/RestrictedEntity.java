package yourcourt.exceptions;


public class RestrictedEntity extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestrictedEntity(String s) {
		super("No puede obtener "+ s+" del que no es creador");
	}
	

}
