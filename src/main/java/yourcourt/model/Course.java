package yourcourt.model;

import java.time.LocalDate;


import javax.persistence.Column;

import javax.persistence.Entity;
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
@Table(name = "inscriptions")
public class Course extends NamedEntity {
	
  @NotBlank
  @Column(name = "title", length = 512)
  private String title;

  @NotBlank
  @Column(name = "description", length = 512)
  private String description;
  
  @NotBlank
  @Column(name = "email", length = 512)
  private LocalDate startDate;
  
  @NotBlank
  @Column(name = "phone", length = 512)
  private LocalDate endDate;
  
}
