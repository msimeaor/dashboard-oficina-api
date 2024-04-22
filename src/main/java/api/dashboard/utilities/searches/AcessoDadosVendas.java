package api.dashboard.utilities.searches;

import api.dashboard.model.repositories.VendaRepository;
import api.dashboard.utilities.GeracaoDatas;
import api.dashboard.utilities.interfaces.searches.AcessoDados;
import org.springframework.stereotype.Component;

@Component
public class AcessoDadosVendas implements AcessoDados {

  private VendaRepository repository;

  public AcessoDadosVendas(VendaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Integer getRegistrosCadastradosUltimoMes() {
    return repository.countVendasLast30DaysByDataCriacao(GeracaoDatas.getUmMesAtras());
  }

  @Override
  public Integer getTotalRegistrosCadastrados() {
    return repository.countTotalVendasByDataCriacao();
  }

  @Override
  public Integer getRegistrosCadastradosMesEspecifico(Integer valorMes) {
    return null;
  }
}
