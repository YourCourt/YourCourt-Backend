package yourcourt.model;

import java.time.LocalDateTime;


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


import org.hibernate.validator.constraints.Length;

import lombok.Data;
import yourcourt.security.model.User;

@Data
@Entity
@Table(name = "comments", indexes = {
	@Index(columnList = "create_date desc")
})
public class Comment {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @NotBlank
	@Column(nullable = false)
	@Length(max = 1000)
	private String content;

    @Column(name = "create_date", nullable = false)
	private LocalDateTime createDate;

    @ManyToOne
	private User author;

    @ManyToOne
    @JsonBackReference
	private News news;

    public Long getNewsId() {
        return news.getId();
    }
    
}