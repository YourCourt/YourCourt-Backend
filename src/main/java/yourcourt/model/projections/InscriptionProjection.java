package yourcourt.model.projections;

import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author javvazzam
 * @author juanogtir
 */

public interface InscriptionProjection {
	
	Long getId();
	
	String getName();
	
	String getSurnames();
	
	String getEmail();
	
	String getPhone();
	
	String getObservations();
	
	@Value("#{target.getUser().getId()}")
	Long getUser();
	
	@Value("#{target.getCourse()}")
	CourseProjection getCourse();
  
  
}
