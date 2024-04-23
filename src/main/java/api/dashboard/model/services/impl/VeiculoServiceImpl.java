package api.dashboard.model.services.impl;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.VeiculoService;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.searches.AcessoDadosVeiculos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VeiculoServiceImpl implements VeiculoService {

  private AcessoDadosVeiculos acessoDadosVeiculos;

  public VeiculoServiceImpl(AcessoDadosVeiculos acessoDadosVeiculos) {
    this.acessoDadosVeiculos = acessoDadosVeiculos;
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasVeiculos() {
    Integer totalVeiculos = acessoDadosVeiculos.getTotalRegistrosCadastrados();
    Double porcentagemCrescimentoVeiculosUltimoMes = new Calculos(acessoDadosVeiculos)
            .calcularCrescimentoUltimoMesEmRelacaoAoTotal();
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Veículos", totalVeiculos, porcentagemCrescimentoVeiculosUltimoMes);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasVeiculosByMes(Integer valorMes) {
    Integer totalVeiculos = acessoDadosVeiculos.getRegistrosCadastradosMesEspecifico(valorMes);
    Double porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado = new Calculos(acessoDadosVeiculos)
            .calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(valorMes);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Veículos", totalVeiculos, porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

}
