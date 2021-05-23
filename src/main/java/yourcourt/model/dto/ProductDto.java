package yourcourt.model.dto;

import javax.validation.constraints.NotBlank;


import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yourcourt.model.ProductType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto{
	
	@NotBlank(message = "El nombre es obligatorio")
	private String name;

	@NotBlank(message = "La descripcion es obligatoria")
	@Length(max = 512, message = "El tamaño del campo descripcion es demasiado largo, y el máximo son 512 caracteres.")
	private String description;
	
	private ProductType productType;
	
}
