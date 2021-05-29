package yourcourt.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

  @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
  @FutureOrPresent(message = "La fecha debe ser futura o presente.")
  private LocalDateTime startDate;
  
  @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
  @FutureOrPresent(message = "La fecha debe ser futura o presente.")
  private LocalDateTime endDate;

  @NotNull(message = "El usuario es obligatorio")
  private Long user;
  
  @NotNull(message = "La pista es obligatoria")
  private Long court;
  
  List<ProductBookingLineDto> lines;
  
}
