package api.dashboard.model.services;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.dtos.response.ResumoCadastrosMesDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VendaService {

  ResponseEntity<EstatisticasDTO> getEstatisticasVendas();
  ResponseEntity<EstatisticasDTO> getEstatisticasVendasByMes(Integer valorMes);
  ResponseEntity<List<ResumoCadastrosMesDTO>> getResumoVendasMensais();

}
