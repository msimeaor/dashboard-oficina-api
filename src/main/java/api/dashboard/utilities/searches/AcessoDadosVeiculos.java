package api.dashboard.utilities.searches;

import api.dashboard.model.repositories.VeiculoRepository;
import api.dashboard.utilities.GeracaoDatas;
import api.dashboard.utilities.interfaces.searches.AcessoDados;
import org.springframework.stereotype.Component;

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
    return null;
  }

}
