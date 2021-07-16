package yourcourt.model.projections;

import org.springframework.beans.factory.annotation.Value;

import yourcourt.model.Image;

public interface ProductProjection {
	Long getId();

	String getName();

	String getDescription();

	Integer getStock();

	Integer getTax();

	Double getPrice();

	Double getBookPrice();

	@Value("#{target.totalPrice()}")
	Double getTotalPrice();

	@Value("#{target.getProductType().getTypeName()}")
	String getProductType();

	Image getImage();

}
