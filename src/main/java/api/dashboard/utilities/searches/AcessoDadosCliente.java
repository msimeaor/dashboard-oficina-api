package api.dashboard.utilities.searches;

import api.dashboard.model.repositories.ClienteRepository;
import api.dashboard.utilities.GeracaoDatas;
import api.dashboard.utilities.interfaces.searches.AcessoDados;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AcessoDadosCliente implements AcessoDados {

  private ClienteRepository repository;

  public AcessoDadosCliente(ClienteRepository repository) {
    this.repository = repository;
  }

  public Integer getRegistrosCadastradosUltimoMes() {
    return repository.countClienteLast30DaysByDataCriacao(GeracaoDatas.getUmMesAtras());
  }

  public Integer getTotalRegistrosCadastrados() {
    return repository.countTotalClientesByDataCriacao();
  }

  public Integer getRegistrosCadastradosMesEspecifico(Integer valorMes) {
    LocalDate primeiroDiaDoMes = GeracaoDatas.getDataPersonalizada(GeracaoDatas.getAnoAtual(), valorMes, 01);
    LocalDate ultimoDiaDoMes = GeracaoDatas.getUltimoDiaMes(primeiroDiaDoMes);

    return repository.countClienteBetween2Dates(primeiroDiaDoMes, ultimoDiaDoMes);
  }

}
