package api.dashboard.model.services;

import api.dashboard.model.dtos.request.ClienteRequestDTO;
import api.dashboard.model.dtos.response.ClienteResponseDTO;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import org.springframework.http.ResponseEntity;

public interface ClienteService {

  ResponseEntity<EstatisticasDTO> getEstatisticasClientes();
  ResponseEntity<EstatisticasDTO> getEstatisticasClientesByMes(Integer valorMes);
  ResponseEntity<ClienteResponseDTO> cadastrarCliente(ClienteRequestDTO clienteRequestDTO);

}
