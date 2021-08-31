package yourcourt.exceptions;

public class DateAttributeMustBePast extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateAttributeMustBePast(String attr) {
		super("La fecha de "+attr+" debe ser pasada.");
	}
	

}
