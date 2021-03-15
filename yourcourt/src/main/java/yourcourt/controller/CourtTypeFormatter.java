package yourcourt.controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import yourcourt.model.CourtType;
import yourcourt.service.CourtService;

@Component
public class CourtTypeFormatter implements Formatter<CourtType> {

	private final CourtService courtService;


	@Autowired
	public CourtTypeFormatter(final CourtService courtService) {
		this.courtService = courtService;
	}

	@Override
	public String print(final CourtType courtType, final Locale locale) {
		return courtType.getName();
	}

	@Override
	public CourtType parse(final String text, final Locale locale) throws ParseException {
		Collection<CourtType> findCourtTypes = this.courtService.findCourtTypes();
		for (CourtType type : findCourtTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
