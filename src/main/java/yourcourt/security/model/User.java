package yourcourt.security.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import yourcourt.model.Booking;
import yourcourt.model.Comment;
import yourcourt.model.Image;
import yourcourt.model.Inscription;
import yourcourt.model.ProductPurchase;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(name = "email")
  @Email
  @NotBlank
  private String email;

  @Column(name = "birth_date")
  @Past(message = "La fecha debe ser pasada.")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  private LocalDate birthDate;

  @Column(name = "phone")
  @NotBlank
  @Pattern(regexp = "^(([+][(][0-9]{1,3}[)][ ])?([0-9]{6,12}))$")
  private String phone;

  @Column(name = "creation_date")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @PastOrPresent(message = "La fecha debe ser pasada o presente.")
  private LocalDate creationDate;

  @Column(name = "membership_number")
  @Pattern(regexp = "\\b\\d{5}\\b", message = "Debe ser de 5 digitos exactos.")
  @NotBlank
  private String membershipNumber;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;

  
  @OrderBy("creationDate desc")
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Collection<Booking> bookings;

  @OrderBy("creationDate desc")
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Collection<ProductPurchase> purchases;

  @OrderBy("creationDate desc")
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Collection<Comment> comments;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Collection<Inscription> inscriptions;

  @OneToOne(cascade = CascadeType.DETACH)
  private Image image;

  @Formula("(SELECT image.image_url FROM images image WHERE image.id=image_id)")
  private String imageUrl;

  public User(
    String username,
    String password,
    String email,
    LocalDate birthDate,
    String phone,
    String membershipNumber
  ) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.birthDate = birthDate;
    this.phone = phone;
    this.membershipNumber = membershipNumber;
  }
}
