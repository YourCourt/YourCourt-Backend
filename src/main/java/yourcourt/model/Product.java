package yourcourt.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
  
  @JsonManagedReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private Collection<ProductBookingLine> lines;

  @Column(name = "product_type")
  @Enumerated(EnumType.STRING)
  private ProductType productType;
}
