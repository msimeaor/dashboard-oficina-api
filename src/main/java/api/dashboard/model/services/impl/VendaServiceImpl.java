package api.dashboard.model.services.impl;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.VendaService;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.searches.AcessoDadosVendas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VendaServiceImpl implements VendaService {

  private AcessoDadosVendas acessoDadosVendas;
  private Calculos calculos;

  public VendaServiceImpl(AcessoDadosVendas acessoDadosVendas,
                          Calculos calculos) {

    this.acessoDadosVendas = acessoDadosVendas;
    this.calculos = calculos;
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasVendas() {
    Integer totalVendas = acessoDadosVendas.getTotalRegistrosCadastrados();
    Double porcentagemCrescimentoVendasUltimoMes = calculos
            .calcularCrescimentoUltimoMesEmRelacaoAoTotal(acessoDadosVendas);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Vendas", totalVendas, porcentagemCrescimentoVendasUltimoMes);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasVendasByMes(Integer valorMes) {
    Integer totalVendas = acessoDadosVendas.getRegistrosCadastradosMesEspecifico(valorMes);
    Double porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado = calculos
            .calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(acessoDadosVendas, valorMes);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Vendas", totalVendas, porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

}
