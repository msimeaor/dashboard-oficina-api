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

  public VendaServiceImpl(AcessoDadosVendas acessoDadosVendas) {
    this.acessoDadosVendas = acessoDadosVendas;
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasVendas() {
    Integer totalVendas = acessoDadosVendas.getTotalRegistrosCadastrados();
    Double porcentagemCrescimentoVendasUltimoMes = new Calculos(acessoDadosVendas)
            .calcularCrescimentoUltimoMesEmRelacaoAoTotal();
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Vendas", totalVendas, porcentagemCrescimentoVendasUltimoMes);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasVendasByMes(Integer valorMes) {
    Integer totalVendas = acessoDadosVendas.getRegistrosCadastradosMesEspecifico(valorMes);
    Double porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado = new Calculos(acessoDadosVendas)
            .calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(valorMes);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Vendas", totalVendas, porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

}
