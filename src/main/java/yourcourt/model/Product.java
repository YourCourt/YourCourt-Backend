package yourcourt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author javvazzam
 * @author juanogtir
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends NamedEntity {
  @NotBlank
  @Column(name = "description", length = 512)
  private String description;

  @Column(name = "product_type") 
  @Enumerated(EnumType.STRING)
  private ProductType productType;
}
