package yourcourt.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@Table(name = "inscriptions")
public class Inscription extends BaseEntity {
	
  @NotBlank
  @Column(name = "name", length = 512)
  private String name;

  @NotBlank
  @Column(name = "surnames", length = 512)
  private String surnames;
  
  @NotBlank
  @Email
  @Column(name = "email", length = 512)
  private String email;
  
  @NotBlank
  @Pattern(regexp = "^(([+][(][0-9]{1,3}[)][ ])?([0-9]{6,12}))$")
  @Column(name = "phone", length = 512)
  private String phone;
  
  @NotBlank
  @Column(name = "observations", length = 512)
  private String observations;
  
  @ManyToOne
  private User user;
  
  @ManyToOne
  private Course course;
  
}
