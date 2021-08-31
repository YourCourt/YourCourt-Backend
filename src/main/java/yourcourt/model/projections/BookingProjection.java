package yourcourt.model.projections;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import yourcourt.model.ProductBooking;

public interface BookingProjection{
	Long getId();
	
	LocalDate getCreationDate();
	
	LocalDateTime getStartDate();
	LocalDateTime getEndDate();
	
	@Value("#{target.getUser().getId()}")
	Long getUser();
	
	@Value("#{target.getCourt().getId()}")
	Long getCourt();
	
	ProductBooking getProductBooking();
	
	@Value("#{(target.getProductBooking() != null)?target.getProductBooking().totalSum():0.0}")
	Double getProductBookingSum();
	
}

