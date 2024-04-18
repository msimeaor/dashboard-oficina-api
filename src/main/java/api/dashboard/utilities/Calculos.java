package api.dashboard.utilities;

import api.dashboard.utilities.interfaces.CalculosInterface;
import api.dashboard.utilities.interfaces.searches.AcessoDados;

public class Calculos implements CalculosInterface {

  private AcessoDados acessoDados;

  public Calculos(AcessoDados acessoDados) {
    this.acessoDados = acessoDados;
  }

  public Double calcularCrescimentoUltimoMesEmRelacaoAoTotal() {
    Integer registrosCadastradosTotal = acessoDados.getTotalRegistrosCadastrados();
    Integer registrosCadastradosUltimoMes = acessoDados.getRegistrosCadastradosUltimoMes();

    return (double) (registrosCadastradosUltimoMes * 100) / registrosCadastradosTotal;
  }

}
