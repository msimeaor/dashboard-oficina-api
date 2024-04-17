package api.dashboard.utilities.clientes;

import api.dashboard.model.repositories.ClienteRepository;
import api.dashboard.utilities.GeracaoDatas;
import api.dashboard.utilities.interfaces.searches.AcessoDados;
import org.springframework.stereotype.Component;

@Component
public class AcessoDadosCliente implements AcessoDados {

  private ClienteRepository repository;
  private GeracaoDatas geracaoDatas;

  public AcessoDadosCliente(ClienteRepository repository,
                            GeracaoDatas geracaoDatas) {

    this.repository = repository;
    this.geracaoDatas = geracaoDatas;
  }

  public Integer getRegistrosCadastradosUltimos30Dias() {
    return repository.countClienteLast30DaysByDataCriacao(geracaoDatas.getUmMesAtras());
  }

  public Integer getRegistrosCadastradosEntreUltimos30E60Dias() {
    return repository.countClienteBetween2DatesByDataCriacao(
            geracaoDatas.getDoisMesesAtras(),
            geracaoDatas.getUmMesAtras());
  }

  public Integer getTotalRegistrosCadastrados() {
    return repository.countTotalClientesByDataCriacao();
  }

}
