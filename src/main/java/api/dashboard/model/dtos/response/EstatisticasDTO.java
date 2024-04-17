package api.dashboard.model.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstatisticasDTO {

  private String nomeEntidade;
  private Integer total;
  private Double crescimento;

  public static EstatisticasDTO newEstatisticasDTO(String nomeEntidade, Integer total, Double crescimento) {
    return EstatisticasDTO.builder()
            .nomeEntidade(nomeEntidade)
            .total(total)
            .crescimento(crescimento)
            .build();
  }

}
