package yourcourt.security.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
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
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;

	@Column(name = "phone")
	@NotBlank
	@Digits(fraction = 0, integer = 10)
	private String phone;

	@Column(name = "creation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate creationDate;

	@Column(name = "membership_number")
	@Pattern(regexp = "\\b\\d{5}\\b")
	@NotBlank
	private String membershipNumber;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public User(String username, String password, String email, LocalDate birthDate, String phone,
			String membershipNumber) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.birthDate = birthDate;
		this.phone = phone;
		this.membershipNumber = membershipNumber;
	}

}
