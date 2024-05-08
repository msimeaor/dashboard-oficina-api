package api.dashboard.model.services.impl;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.dtos.response.ResumoCadastrosMesDTO;
import api.dashboard.model.services.VendaService;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.businessLogicEndpoints.vendas.LogicaGetResumoCadastrosMensais;
import api.dashboard.utilities.searches.AcessoDadosVendas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaServiceImpl implements VendaService {

  private AcessoDadosVendas acessoDadosVendas;
  private Calculos calculos;
  private LogicaGetResumoCadastrosMensais logicaGetResumoCadastrosMensais;

  public VendaServiceImpl(AcessoDadosVendas acessoDadosVendas,
                          Calculos calculos,
                          LogicaGetResumoCadastrosMensais logicaGetResumoCadastrosMensais) {

    this.acessoDadosVendas = acessoDadosVendas;
    this.calculos = calculos;
    this.logicaGetResumoCadastrosMensais = logicaGetResumoCadastrosMensais;
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

  @Override
  public ResponseEntity<List<ResumoCadastrosMesDTO>> getResumoVendasMensais() {
    List<ResumoCadastrosMesDTO> resumoCadastrosMesDTOsList = logicaGetResumoCadastrosMensais
            .getResumoCadastrosMensais(acessoDadosVendas);
    return new ResponseEntity<>(resumoCadastrosMesDTOsList, HttpStatus.OK);
  }

}
