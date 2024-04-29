package api.dashboard.utilities.searches;

import api.dashboard.model.repositories.ClienteRepository;
import api.dashboard.utilities.GeracaoDatas;
import api.dashboard.utilities.interfaces.searches.AcessoDados;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AcessoDadosClientes implements AcessoDados {

  private ClienteRepository repository;

  public AcessoDadosClientes(ClienteRepository repository) {
    this.repository = repository;
  }

  @Override
  public Integer getRegistrosCadastradosUltimoMes() {
    return repository.countClienteLast30DaysByDataCriacao(GeracaoDatas.getUmMesAtras());
  }

  @Override
  public Integer getTotalRegistrosCadastrados() {
    return repository.countTotalClientesByDataCriacao();
  }

  @Override
  public Integer getRegistrosCadastradosMesEspecifico(Integer valorMes) {
    LocalDate primeiroDiaDoMes = GeracaoDatas.getDataPersonalizada(GeracaoDatas.getAnoAtual(), valorMes, 01);
    LocalDate ultimoDiaDoMes = GeracaoDatas.getUltimoDiaMes(primeiroDiaDoMes);

    return repository.countClienteBetween2Dates(primeiroDiaDoMes, ultimoDiaDoMes);
  }

}
