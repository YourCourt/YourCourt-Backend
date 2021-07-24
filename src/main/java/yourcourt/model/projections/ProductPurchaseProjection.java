package yourcourt.model.projections;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;

import yourcourt.model.ProductPurchaseLine;

public interface ProductPurchaseProjection {
	Long getId();

	LocalDate getCreationDate();

	@Value("#{target.getUser().getId()}")
	Long getUser();

	@Value("#{target.getLines()}")
	Collection<ProductPurchaseLine> getLines();

	@Value("#{target.totalSum()}")
	Double getProductPurchaseSum();

}
