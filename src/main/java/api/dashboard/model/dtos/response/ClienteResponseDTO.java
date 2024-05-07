package api.dashboard.model.dtos.response;

import api.dashboard.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponseDTO extends RepresentationModel<ClienteResponseDTO> {

  private Long id;
  private String primeiroNome;
  private String sobrenome;
  private String cpf;
  private LocalDate dataNascimento;
  private Genero genero;
  private String email;

}
