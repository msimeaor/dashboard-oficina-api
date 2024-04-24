package api.dashboard.utilities.interfaces;

import api.dashboard.utilities.interfaces.searches.AcessoDados;

public interface CalculosInterface {

  Double calcularCrescimentoUltimoMesEmRelacaoAoTotal(AcessoDados acessoDados);
  Double calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(AcessoDados acessoDados, Integer valorMes);

}
