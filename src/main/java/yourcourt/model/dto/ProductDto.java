package yourcourt.model.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  @NotBlank(message = "La descripcion es obligatoria")
  @Length(
    max = 512,
    message = "El tamaño del campo descripcion es demasiado largo, y el maximo son 512 caracteres."
  )
  private String description;
  
  @NotNull(message= "El stock es obligatorio")
  @Min(0)
  private Integer stock;
  
  @NotNull(message= "El impuesto es obligatorio")
  @Min(0)
  private Integer tax;

  @NotNull(message= "El precio es obligatorio")
  @DecimalMin("0")
  private Double price;

  @NotNull(message= "El tipo es obligatorio")
  private String productType;
}
