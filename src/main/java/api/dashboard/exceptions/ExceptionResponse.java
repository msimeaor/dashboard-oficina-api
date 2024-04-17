package api.dashboard.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {

  private Integer codigoHttpErro;
  private String mensagemErro;
  private String detalhesErro;
  private Date timestamp;

}
