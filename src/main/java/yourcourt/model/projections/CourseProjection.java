package yourcourt.model.projections;

import java.time.LocalDate;

/**
 *
 * @author javvazzam
 * @author juanogtir
 */

public interface CourseProjection {
	
	Long getId();
	
	String getTitle();
	
	String getDescription();
		
	LocalDate getStartDate();
	
	LocalDate getEndDate();
  
  
}
