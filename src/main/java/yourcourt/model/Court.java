package yourcourt.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
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
@Table(name = "courts")
public class Court extends Construction {
  @Column(name = "court_type")
  @Enumerated(EnumType.STRING)
  private CourtType courtType;
  
  @OrderBy("creationDate desc")
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "court")
  private Collection<Booking> bookings;
}
