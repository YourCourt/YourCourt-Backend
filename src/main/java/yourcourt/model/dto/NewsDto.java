package yourcourt.model.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import yourcourt.model.Construction;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto extends Construction{

	@NotBlank
	private String description;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date creationDate;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date editionDate;
}
