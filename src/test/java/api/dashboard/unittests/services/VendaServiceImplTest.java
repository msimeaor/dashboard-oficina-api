package api.dashboard.unittests.services;

import api.dashboard.exceptions.ZeroCountException;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.dtos.response.ResumoCadastrosMesDTO;
import api.dashboard.model.services.impl.VendaServiceImpl;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.businessLogicEndpoints.vendas.LogicaGetResumoCadastrosMensais;
import api.dashboard.utilities.searches.AcessoDadosVendas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class VendaServiceImplTest {

  @InjectMocks
  private VendaServiceImpl service;
  @Mock
  private AcessoDadosVendas acessoDadosVendas;
  @Mock
  private Calculos calculos;
  @Mock
  private LogicaGetResumoCadastrosMensais logicaGetResumoCadastrosMensais;

  private List<ResumoCadastrosMesDTO> resumoCadastrosMesDTOsList;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void whenGetEstatisticasVendasThenReturnSuccess() {
    when(acessoDadosVendas.getTotalRegistrosCadastrados()).thenReturn(25);
    when(calculos.calcularCrescimentoUltimoMesEmRelacaoAoTotal(any())).thenReturn(28.0d);
    var content = service.getEstatisticasVendas();

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Vendas", content.getBody().getNomeEntidade());
    assertEquals(25, content.getBody().getTotal());
    // O calculo do crescimento é: (registrosCadastradosUltimoMes * 100) / totalRegistros
    assertEquals(28.0d, content.getBody().getCrescimento());
  }

  @Test
  void whenGetEstatisticasVendasByMesThenReturnSuccess() {
    when(acessoDadosVendas.getRegistrosCadastradosMesEspecifico(anyInt())).thenReturn(8);
    when(calculos.calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(any(), anyInt()))
            .thenReturn(-12.5d);
    var content = service.getEstatisticasVendasByMes(1);

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Vendas", content.getBody().getNomeEntidade());
    assertEquals(8, content.getBody().getTotal());
    // O calculo do crescimento é: ((totalCadastrosUltimoMes - totalCadastrosMesEspecifico) * 100) / totalCadastrosMesEspecifico
    assertEquals(-12.5d, content.getBody().getCrescimento());
  }

  @Test
  void whenGetEstatisticasVendasByMesThenThrownZeroCountException() {
    when(acessoDadosVendas.getRegistrosCadastradosMesEspecifico(anyInt())).thenReturn(0);

    try {
      service.getEstatisticasVendasByMes(5);
    } catch (Exception ex) {
      assertEquals(ZeroCountException.class, ex.getClass());
      assertEquals("Dados insuficientes!", ex.getMessage());
    }

    /*
    A exception é lançada sempre que o total de vendas cadastradas no mês filtrado for igual a 0.
    Neste caso é impossivel calcular o crescimento de cadastros.
    */
  }

  @Test
  void whenGetResumoVendasMensaisThenReturnSuccess() {
    /*
    No teste, a lista passada só contem 1 objeto ResumoCadastrosMesDTO. Em um cenário real, a lista terá um objeto
    representando cada mês e o total de vendas cadastradas naquele mês.
    */
    when(logicaGetResumoCadastrosMensais.getResumoCadastrosMensais(any()))
            .thenReturn(resumoCadastrosMesDTOsList);

    var content = service.getResumoVendasMensais();

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(ResumoCadastrosMesDTO.class, content.getBody().get(0).getClass());
    assertEquals("Jan", content.getBody().get(0).getLabelMes());
    assertEquals(8, content.getBody().get(0).getTotalCadastros());
  }

  public void startEntities() {
    resumoCadastrosMesDTOsList =
            Arrays.asList(ResumoCadastrosMesDTO.newResumoCadastrosMesDTO("Jan", 8));
  }

}