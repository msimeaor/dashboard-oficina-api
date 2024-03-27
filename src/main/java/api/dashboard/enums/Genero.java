package api.dashboard.enums;

public enum Genero {

  MASCULINO("M"),
  FEMININO("F");

  private final String sigla;

  Genero(String sigla) {
    this.sigla = sigla;
  }

  public String getSigla() {
    return this.sigla;
  }

  public Genero getBySigla(String sigla) {
    for (Genero g : Genero.values()) {
      if (g.getSigla().equalsIgnoreCase(sigla)) return g;
    }

    throw new IllegalArgumentException("Gênero não encontrado!");
  }

}
