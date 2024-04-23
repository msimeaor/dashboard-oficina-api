package api.dashboard.utilities.searches;

import api.dashboard.model.repositories.VeiculoRepository;
import api.dashboard.utilities.GeracaoDatas;
import api.dashboard.utilities.interfaces.searches.AcessoDados;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AcessoDadosVeiculos implements AcessoDados {

  private VeiculoRepository repository;

  public AcessoDadosVeiculos(VeiculoRepository repository) {
    this.repository = repository;
  }

  @Override
  public Integer getRegistrosCadastradosUltimoMes() {
    return repository.countVeiculosLast30DaysByDataCriacao(GeracaoDatas.getUmMesAtras());
  }

  @Override
  public Integer getTotalRegistrosCadastrados() {
    return repository.countTotalVeiculosByDataCriacao();
  }

  @Override
  public Integer getRegistrosCadastradosMesEspecifico(Integer valorMes) {
    LocalDate primeiroDiaDoMes = GeracaoDatas.getDataPersonalizada(GeracaoDatas.getAnoAtual(), valorMes, 01);
    LocalDate ultimoDiaDoMes = GeracaoDatas.getUltimoDiaMes(primeiroDiaDoMes);

    return repository.countVeiculoBetween2Dates(primeiroDiaDoMes, ultimoDiaDoMes);
  }

}
