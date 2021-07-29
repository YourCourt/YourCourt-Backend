package yourcourt.model.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPurchaseLineDto {

  @Min(1)
  @NotBlank
  private Integer quantity;

  @DecimalMin("0")
  @NotBlank
  private Double discount;

  @NotNull(message = "El producto es obligatorio")
  private Long productId;

}
