package api.dashboard.model.services.impl;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.ClienteService;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.searches.AcessoDadosCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

  private AcessoDadosCliente acessoDadosCliente;
  private Calculos calculos;

  public ClienteServiceImpl(AcessoDadosCliente acessoDadosCliente,
                            Calculos calculos) {

    this.acessoDadosCliente = acessoDadosCliente;
    this.calculos = calculos;
  }

  public ResponseEntity<EstatisticasDTO> getEstatisticasClientes() {
    Integer totalClientes = acessoDadosCliente.getTotalRegistrosCadastrados();
    Double porcentagemCrescimentoClientesUltimoMes = calculos
            .calcularCrescimentoUltimoMesEmRelacaoAoTotal(acessoDadosCliente);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Clientes", totalClientes, porcentagemCrescimentoClientesUltimoMes);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

  public ResponseEntity<EstatisticasDTO> getEstatisticasClientesByMes(Integer valorMes) {
    Integer totalClientes = acessoDadosCliente.getRegistrosCadastradosMesEspecifico(valorMes);
    Double porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado = calculos
            .calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(acessoDadosCliente, valorMes);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Clientes", totalClientes, porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

}
