package yourcourt.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import yourcourt.model.serializers.UserSerializer;
import yourcourt.security.model.User;

@Data
@Entity
@Table(name = "comments", indexes = {
	@Index(columnList = "creation_date desc")
})
public class Comment {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @NotBlank
	@Column(nullable = false)
	@Length(max = 1000)
	private String content;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate creationDate;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
	private User user;

    @ManyToOne
    @JsonBackReference
	private News news;

    public Long getNewsId() {
        return news.getId();
    }
    
}