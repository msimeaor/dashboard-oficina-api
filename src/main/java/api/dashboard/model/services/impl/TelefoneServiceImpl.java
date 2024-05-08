package api.dashboard.model.services.impl;

import api.dashboard.dozermapper.DozzerMapper;
import api.dashboard.model.dtos.response.TelefoneResponseDTO;
import api.dashboard.model.entities.Telefone;
import api.dashboard.model.services.TelefoneService;
import api.dashboard.utilities.searches.AcessoDadosTelefones;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TelefoneServiceImpl implements TelefoneService {

  private AcessoDadosTelefones acessoDadosTelefones;

  public TelefoneServiceImpl(AcessoDadosTelefones acessoDadosTelefones) {
    this.acessoDadosTelefones = acessoDadosTelefones;
  }

  @Override
  public ResponseEntity<TelefoneResponseDTO> getTelefoneById(Long id) {
    Telefone telefone = acessoDadosTelefones.getTelefoneById(id);
    TelefoneResponseDTO telefoneResponseDTO = DozzerMapper.parseObject(telefone, TelefoneResponseDTO.class);
    // TODO Criar link para cliente
    return new ResponseEntity<>(telefoneResponseDTO, HttpStatus.OK);
  }

}
