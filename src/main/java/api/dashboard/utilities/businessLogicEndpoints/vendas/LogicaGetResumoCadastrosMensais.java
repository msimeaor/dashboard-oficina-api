package api.dashboard.utilities.businessLogicEndpoints.vendas;

import api.dashboard.model.dtos.response.ResumoCadastrosMesDTO;
import api.dashboard.utilities.interfaces.searches.AcessoDados;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class LogicaGetResumoCadastrosMensais {

  private List<String> mesesDoAno = new ArrayList<>(Arrays
          .asList("Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
                  "Jul", "Ago", "Set", "Out", "Nov", "Dez"));
  private List<ResumoCadastrosMesDTO> resumoCadastrosMesDTOsList = new ArrayList<>();

  public List<ResumoCadastrosMesDTO> getResumoCadastrosMensais(AcessoDados acessoDados) {
    IntStream.range(1, mesesDoAno.size() + 1).forEach(index -> {
      String mes = coletarMes(index);
      Integer totalCadastros = coletarTotalDeCadastros(acessoDados, index);
      instanciarResumoCadastrosMesDTOEInserirNaLista(mes, totalCadastros);
    });

    return resumoCadastrosMesDTOsList;
  }

  private String coletarMes(Integer index) {
    return mesesDoAno.get(index - 1);
  }

  private Integer coletarTotalDeCadastros(AcessoDados acessoDados, Integer index) {
    return acessoDados.getRegistrosCadastradosMesEspecifico(index);
  }

  private void instanciarResumoCadastrosMesDTOEInserirNaLista(String mes, Integer totalCadastros) {
    ResumoCadastrosMesDTO resumoCadastrosMesDTO = ResumoCadastrosMesDTO.newResumoCadastrosMesDTO(mes, totalCadastros);
    resumoCadastrosMesDTOsList.add(resumoCadastrosMesDTO);
  }

}
