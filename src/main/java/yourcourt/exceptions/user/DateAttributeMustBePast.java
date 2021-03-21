package yourcourt.exceptions.user;

public class DateAttributeMustBePast extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateAttributeMustBePast(String attr) {
		super(attr+" must be a past date");
	}
	

}
