package api.dashboard.model.services;

import api.dashboard.model.dtos.response.TelefoneResponseDTO;
import org.springframework.http.ResponseEntity;

public interface TelefoneService {

  ResponseEntity<TelefoneResponseDTO> getTelefoneById(Long id);

}
