package api.dashboard.model.services.impl;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.ClienteService;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.clientes.AcessoDadosCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

  private AcessoDadosCliente acessoDadosCliente;

  public ClienteServiceImpl(AcessoDadosCliente acessoDadosCliente) {
    this.acessoDadosCliente = acessoDadosCliente;
  }

  public ResponseEntity<EstatisticasDTO> getEstatisticasClientes() {
    Integer totalClientes = acessoDadosCliente.getTotalRegistrosCadastrados();
    Double porcentagemCrescimentoClientesUltimoMes = new Calculos(acessoDadosCliente)
            .calcPorcentagemCrescimentoRegistrosCadastradosUltimos30DiasEmRelacaoMesAnterior();

    EstatisticasDTO estatisticasDTO = EstatisticasDTO.builder()
            .nomeEntidade("Clientes")
            .total(totalClientes)
            .crescimento(porcentagemCrescimentoClientesUltimoMes)
            .build();

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

}
