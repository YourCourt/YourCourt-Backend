package yourcourt.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

  @Column(nullable = false)
  @NotNull(message = "El stock es obligatorio")
  @Min(0)
  private Integer stock;

  @Column(nullable = false)
  @NotNull(message = "El impuesto es obligatorio")
  @Min(0)
  private Integer tax;

  @Column(nullable = false)
  @NotNull(message = "El precio es obligatorio")
  @DecimalMin("0")
  private Double price;

  @Column(name = "book_price",nullable = false)
  @NotNull(message = "El precio de alquiler es obligatorio")
  @DecimalMin("0")
  private Double bookPrice;


  public Double totalPrice(){
    Double price=this.getPrice()+(this.getPrice()*this.getTax().doubleValue()/100.0);
    return Math.round(price * 100.0) / 100.0;
  }

  @JsonManagedReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private Collection<ProductBookingLine> lines;

  @JsonManagedReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private Collection<ProductPurchaseLine> purchaseLines;

  @ManyToOne
  private ProductType productType;
}
