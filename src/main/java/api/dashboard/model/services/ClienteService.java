package api.dashboard.model.services;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import org.springframework.http.ResponseEntity;

public interface ClienteService {

  ResponseEntity<EstatisticasDTO> getEstatisticasClientes(Integer mes);

}
