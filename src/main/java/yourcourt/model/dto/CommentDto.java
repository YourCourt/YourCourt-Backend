package yourcourt.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotNull(message = "El id de la noticia es obligatoria")
    private Long newsId;

    @NotBlank(message = "El campo contenido es obligatorio")
    @Length(max = 1000, message = "El tamaño del campo descripción es demasiado largo, y el máximo son 1000 caracteres.")
    private String content;


}