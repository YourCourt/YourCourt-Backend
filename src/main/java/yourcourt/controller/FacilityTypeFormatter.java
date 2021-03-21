package yourcourt.controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import yourcourt.model.FacilityType;
import yourcourt.service.FacilityService;

@Component
public class FacilityTypeFormatter implements Formatter<FacilityType> {

	private final FacilityService facilityService;


	@Autowired
	public FacilityTypeFormatter(final FacilityService facilityService) {
		this.facilityService = facilityService;
	}

	@Override
	public String print(final FacilityType facilityType, final Locale locale) {
		return facilityType.getName();
	}

	@Override
	public FacilityType parse(final String text, final Locale locale) throws ParseException {
		Collection<FacilityType> findFacilityTypes = this.facilityService.findFacilityTypes();
		for (FacilityType type : findFacilityTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
