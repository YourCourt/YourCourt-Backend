package yourcourt.model.projections;

import java.time.LocalDate;
import java.util.List;

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
