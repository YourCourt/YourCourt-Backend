package yourcourt.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDto {
  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  @NotBlank(message = "La descripcion es obligatoria")
  @Length(
    max = 512,
    message = "El tamaño del campo descripcion es demasiado largo, y el maximo son 512 caracteres."
  )
  private String description;

  @NotNull(message = "El tipo es obligatorio")
  private String facilityType;
}
