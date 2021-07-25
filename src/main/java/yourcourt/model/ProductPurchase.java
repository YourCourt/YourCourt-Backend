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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import yourcourt.model.serializers.UserSerializer;
import yourcourt.security.model.User;

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
@Table(name = "product_purchase")
public class ProductPurchase extends BaseEntity {

  @Column(name = "creation_date")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @PastOrPresent(message = "La fecha debe ser pasada o presente.")
  private LocalDate creationDate;

  public Double totalSum() {
    return this.getLines().stream().reduce(0.,
        (partialResult, line) -> partialResult + (line.getProduct().totalPrice() * line.getQuantity()), Double::sum);
  }

  @JsonManagedReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "productPurchase")
  private Collection<ProductPurchaseLine> lines;

  @JsonSerialize(using = UserSerializer.class)
  @ManyToOne
  private User user;

  public ProductPurchase(LocalDate creationDate) {
    this.creationDate = creationDate;
  }
}
