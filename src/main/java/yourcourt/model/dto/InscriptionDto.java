package yourcourt.model.dto;


import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionDto {
	
	@NotBlank(message = "El nombre es obligatorio")
	private String name;

	@NotBlank(message = "Los apellidos son obligatorios")
	private String surnames;

	@NotBlank(message = "El email es obligatorio")
	private String email;

	@NotBlank(message = "El teléfono es obligatorio")
	private String phone;

	@NotBlank
	@Length(max = 512, message = "El tamaño del campo observaciones es demasiado largo, y el máximo son 512 caracteres.")
	private String observations;

}
