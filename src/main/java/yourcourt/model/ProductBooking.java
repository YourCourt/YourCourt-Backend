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
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import yourcourt.model.serializers.BookingSerializer;

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
@Table(name = "product_booking")
public class ProductBooking extends BaseEntity {
  @JsonSerialize(using = BookingSerializer.class)
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "booking_id")
  private Booking booking;
  
  public Double totalSum() {
	  return this.getLines()
	  .stream()
      .reduce(
        0., (partialResult, line) -> partialResult + (line.getProduct().getPrice()*line.getQuantity()), Double::sum);
  }
 

  @JsonManagedReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "productBooking")
  private Collection<ProductBookingLine> lines;
}
