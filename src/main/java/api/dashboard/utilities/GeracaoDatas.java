package api.dashboard.utilities;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class GeracaoDatas {

  public static LocalDate getHoje() {
    return LocalDate.now();
  }

  public static Integer getAnoAtual() {
    return getHoje().getYear();
  }

  public static LocalDate getUmMesAtras() {
    return getHoje().plusMonths(-1);
  }

  public static LocalDate getDataPersonalizada(Integer ano, Integer mes, Integer dia) {
    return LocalDate.of(ano, mes, dia);
  }

  public static LocalDate getUltimoDiaMes(LocalDate data) {
    return data.with(TemporalAdjusters.lastDayOfMonth());
  }

}
