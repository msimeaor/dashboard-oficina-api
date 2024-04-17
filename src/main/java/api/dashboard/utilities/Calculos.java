package api.dashboard.utilities;

import api.dashboard.exceptions.ZeroRegistrosEncontradosException;
import api.dashboard.utilities.interfaces.CalculosInterface;
import api.dashboard.utilities.interfaces.searches.AcessoDados;

public class Calculos implements CalculosInterface {

  private AcessoDados acessoDados;

  public Calculos(AcessoDados acessoDados) {
    this.acessoDados = acessoDados;
  }

  public Double calcPorcentagemCrescimentoRegistrosCadastradosUltimos30DiasEmRelacaoMesAnterior() {
    Integer registrosCadastradosUltimoMes = acessoDados.getRegistrosCadastradosUltimos30Dias();
    Integer registrosCadastradosPenultimoMes = acessoDados.getRegistrosCadastradosEntreUltimos30E60Dias();
    validarRegistrosCadastradosPenultimoMes(registrosCadastradosPenultimoMes);
    Integer diferencaDeRegistros2Meses = registrosCadastradosUltimoMes - registrosCadastradosPenultimoMes;

    return (double) (diferencaDeRegistros2Meses * 100) / registrosCadastradosPenultimoMes;
  }

  private void validarRegistrosCadastradosPenultimoMes(Integer registrosCadastradosPenultimoMes) {
    if (registrosCadastradosPenultimoMes == 0)
      throw new ZeroRegistrosEncontradosException("Dados insuficientes!");
  }

}
