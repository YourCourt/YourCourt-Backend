package yourcourt.model;

import java.time.LocalDate;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "courses")
public class Course extends BaseEntity {
	
  @NotBlank
  @Column(name = "title", length = 512)
  private String title;

  @NotBlank
  @Column(name = "description", length = 512)
  private String description;
  
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @Column(name = "start_date", length = 512)
  private LocalDate startDate;
  
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @Column(name = "end_date", length = 512)
  private LocalDate endDate;
  
}
