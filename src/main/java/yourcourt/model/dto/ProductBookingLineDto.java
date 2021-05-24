package yourcourt.model.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBookingLineDto {

  @Min(1)
  private Integer quantity;

  @DecimalMin("0")
  private Double discount;
  
  @NotNull(message = "El producto es obligatorio")
  private Long product;
  
}
