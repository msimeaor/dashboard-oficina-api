package api.dashboard.utilities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class GeracaoDatas {

  private LocalDate hoje = LocalDate.now();
  private LocalDate umMesAtras = hoje.plusMonths(-1);
  private LocalDate doisMesesAtras = umMesAtras.plusMonths(-1);

}
