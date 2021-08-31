/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package yourcourt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
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
@Table(name = "product_purchase_lines")
public class ProductPurchaseLine extends BaseEntity {
  @Column(nullable = false)
  @Min(1)
  private Integer quantity;

  @Column(nullable = false)
  @DecimalMin("0")
  private Double discount;

  @JsonBackReference
  @ManyToOne
  private ProductPurchase productPurchase;

  @JsonBackReference
  @ManyToOne
  private Product product;

  public Long getProductId() {
    return this.product.id;
  }

  public ProductPurchaseLine(Integer quantity, Double discount) {
    this.quantity = quantity;
    this.discount = discount;
  }
}
