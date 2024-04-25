package api.dashboard.utilities.interfaces;

import api.dashboard.model.dtos.response.ResumoCadastrosMesDTO;
import api.dashboard.utilities.interfaces.searches.AcessoDados;

import java.util.List;

public interface LogicaGetResumoCadastrosMensaisInterface {

  List<ResumoCadastrosMesDTO> getResumoCadastrosMensais(AcessoDados acessoDados);

}
