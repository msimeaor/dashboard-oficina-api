package api.dashboard.enums;

public enum Fabricante {

  VOLKSWAGEM("VOLKSWAGEM"),
  FIAT("FIAT"),
  CHEVROLET("CHEVROLET"),
  FORD("FORD"),
  TOYOTA("TOYOTA"),
  HYUNDAI("HYUNDAI"),
  RENAULT("RENAULT"),
  HONDA("HONDA"),
  JEEP("JEEP"),
  NISSAN("NISSAN"),
  MITSUBISHI("MITSUBISHI"),
  PEUGEOT("PEUGEOT"),
  CITROEN("CITROEN"),
  KIA("KIA"),
  MERCEDES("MERCEDES"),
  BMW("BMW"),
  AUDI("AUDI"),
  VOLVO("VOLVO"),
  LANDROVER("LANDROVER"),
  SUZUKI("SUZUKI"),
  PORSCHE("PORSCHE"),
  JAGUAR("JAGUAR"),
  SUBARU("SUBARU"),
  CHERY("CHERY"),
  MINI("MINI"),
  LIFAN("LIFAN"),
  JAC("JAC"),
  IVECO("IVECO"),
  LEXUS("LEXUS"),
  RAM("RAM");

  private String fabricante;

  Fabricante(String fabricante) {
    this.fabricante = fabricante;
  }

  public String getFabricante() {
    return this.fabricante;
  }

  public Fabricante getFabricanteByString(String nomeFabricante) {
    for (Fabricante f : Fabricante.values()) {
      if (f.getFabricante().equalsIgnoreCase(nomeFabricante)) return f;
    }

    throw new IllegalArgumentException("Fabricante não encontrada!");
  }

}
