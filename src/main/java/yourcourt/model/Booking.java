package yourcourt.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import yourcourt.model.serializers.ProductBookingSerializer;
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
@Table(name = "bookings")
public class Booking extends BaseEntity {
  @Column(name = "creation_date")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @PastOrPresent(message = "La fecha debe ser pasada o presente.")
  private LocalDate creationDate;

  @Column(name = "book_date")
  @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
  @FutureOrPresent(message = "La fecha debe ser futura o presente.")
  private LocalDateTime bookDate;

  @JsonSerialize(using = UserSerializer.class)
  @ManyToOne
  private User user;

  @JsonSerialize(using = ProductBookingSerializer.class)
  @OneToOne(mappedBy = "booking", optional = true, cascade = CascadeType.ALL)
  private ProductBooking productBooking;
  
  public Booking(LocalDate creationDate,LocalDateTime bookDate) {
	  this.creationDate=creationDate;
	  this.bookDate=bookDate;
  }
}
