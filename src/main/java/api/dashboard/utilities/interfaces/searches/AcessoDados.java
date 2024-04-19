package api.dashboard.utilities.interfaces.searches;

public interface AcessoDados {

  Integer getRegistrosCadastradosUltimoMes();
  Integer getTotalRegistrosCadastrados();
  Integer getRegistrosCadastradosMesEspecifico(Integer valorMes);

}
