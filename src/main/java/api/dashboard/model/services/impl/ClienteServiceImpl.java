package api.dashboard.model.services.impl;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.ClienteService;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.searches.AcessoDadosClientes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

  private AcessoDadosClientes acessoDadosClientes;
  private Calculos calculos;

  public ClienteServiceImpl(AcessoDadosClientes acessoDadosClientes,
                            Calculos calculos) {

    this.acessoDadosClientes = acessoDadosClientes;
    this.calculos = calculos;
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasClientes() {
    Integer totalClientes = acessoDadosClientes.getTotalRegistrosCadastrados();
    Double porcentagemCrescimentoClientesUltimoMes = calculos
            .calcularCrescimentoUltimoMesEmRelacaoAoTotal(acessoDadosClientes);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Clientes", totalClientes, porcentagemCrescimentoClientesUltimoMes);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasClientesByMes(Integer valorMes) {
    Integer totalClientes = acessoDadosClientes.getRegistrosCadastradosMesEspecifico(valorMes);
    Double porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado = calculos
            .calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(acessoDadosClientes, valorMes);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Clientes", totalClientes, porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

}
