package api.dashboard.utilities.interfaces.searches;

public interface AcessoDados {

  Integer getRegistrosCadastradosUltimos30Dias();
  Integer getRegistrosCadastradosEntreUltimos30E60Dias();
  Integer getTotalRegistrosCadastrados();

}
