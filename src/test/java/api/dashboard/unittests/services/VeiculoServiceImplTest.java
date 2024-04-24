package api.dashboard.unittests.services;

import api.dashboard.exceptions.ZeroCountException;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.VeiculoServiceImpl;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.searches.AcessoDadosVeiculos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class VeiculoServiceImplTest {

  @InjectMocks
  private VeiculoServiceImpl service;
  @Mock
  private AcessoDadosVeiculos acessoDadosVeiculos;
  @Mock
  Calculos calculos;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void whenGetEstatisticasVeiculosThenReturnSuccess() {
    when(acessoDadosVeiculos.getTotalRegistrosCadastrados()).thenReturn(50);
    when(calculos.calcularCrescimentoUltimoMesEmRelacaoAoTotal(any())).thenReturn(20.0d);
    var content = service.getEstatisticasVeiculos();

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Veículos", content.getBody().getNomeEntidade());
    assertEquals(50, content.getBody().getTotal());
    // O calculo do crescimento é: (registrosCadastradosUltimoMes * 100) / totalRegistros
    assertEquals(20.0d, content.getBody().getCrescimento());
  }

  @Test
  void whenGetEstatisticasVeiculosByMesThenReturnSuccess() {
    when(acessoDadosVeiculos.getRegistrosCadastradosMesEspecifico(anyInt())).thenReturn(15);
    when(calculos.calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(any(), anyInt()))
            .thenReturn(-33.333333333333336d);
    var content = service.getEstatisticasVeiculosByMes(1);

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Veículos", content.getBody().getNomeEntidade());
    assertEquals(15, content.getBody().getTotal());
    // O calculo do crescimento é: ((totalCadastrosUltimoMes - totalCadastrosMesEspecifico) * 100) / totalCadastrosMesEspecifico
    assertEquals(-33.333333333333336d, content.getBody().getCrescimento());
  }

  @Test
  void whenGetEstatisticasVeiculosByMesThenThrownZeroCountException() {
    when(acessoDadosVeiculos.getRegistrosCadastradosMesEspecifico(anyInt())).thenReturn(0);

    try {
      service.getEstatisticasVeiculosByMes(5);
    } catch (Exception ex) {
      assertEquals(ZeroCountException.class, ex.getClass());
      assertEquals("Dados insuficientes!", ex.getMessage());
    }

    /*
    A exception é lançada sempre que o total de veiculos cadastrados no mês filtrado for igual a 0.
    Neste caso é impossivel calcular o crescimento de cadastros.
    */

  }

  public void startEntities() {}
}