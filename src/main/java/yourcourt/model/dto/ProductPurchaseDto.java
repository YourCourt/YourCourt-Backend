package yourcourt.model.dto;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPurchaseDto {

  @NotNull(message = "El usuario es obligatorio")
  private Long user;

  Collection<ProductPurchaseLineDto> lines;

}
