package api.dashboard.utilities;

import api.dashboard.exceptions.ZeroCountException;
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

  public Double calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(Integer valorMes) {
    Integer registrosCadastradosUltimoMes = acessoDados.getRegistrosCadastradosUltimoMes();
    Integer registrosCadastradosMesSelecionado = acessoDados.getRegistrosCadastradosMesEspecifico(valorMes);
    validarTotalRegistrosCadastrados(registrosCadastradosMesSelecionado);
    Integer diferencaDeCadastros = registrosCadastradosUltimoMes - registrosCadastradosMesSelecionado;

    return (double) (diferencaDeCadastros * 100) / registrosCadastradosMesSelecionado;
  }

  private void validarTotalRegistrosCadastrados(Integer totalRegistros) {
    if (totalRegistros == 0)
      throw new ZeroCountException("Dados insuficientes!");
  }

}
