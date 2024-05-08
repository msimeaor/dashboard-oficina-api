package api.dashboard.model.dtos.request;

import api.dashboard.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequestDTO {

  private String primeiroNome;
  private String sobrenome;
  private String cpf;
  private LocalDate dataNascimento;
  private Genero genero;
  private String email;
  private TelefoneRequestDTO telefoneRequestDTO;

}
