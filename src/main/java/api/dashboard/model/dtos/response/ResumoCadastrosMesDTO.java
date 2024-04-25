package api.dashboard.model.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumoCadastrosMesDTO {

  private String labelMes;
  private Integer totalCadastros;

  public static ResumoCadastrosMesDTO newResumoCadastrosMesDTO(String labelMes, Integer totalCadastros) {
    return ResumoCadastrosMesDTO.builder()
            .labelMes(labelMes)
            .totalCadastros(totalCadastros)
            .build();
  }

}
