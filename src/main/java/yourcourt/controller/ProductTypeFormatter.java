package yourcourt.controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import yourcourt.model.ProductType;
import yourcourt.service.ProductService;

@Component
public class ProductTypeFormatter implements Formatter<ProductType> {

	private final ProductService productService;


	@Autowired
	public ProductTypeFormatter(final ProductService ProductService) {
		this.productService = ProductService;
	}

	@Override
	public String print(final ProductType productType, final Locale locale) {
		return productType.getName();
	}

	@Override
	public ProductType parse(final String text, final Locale locale) throws ParseException {
		Collection<ProductType> findProductTypes = this.productService.findProductTypes();
		for (ProductType type : findProductTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
