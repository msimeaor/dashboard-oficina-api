package api.dashboard.unittests.services;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.VeiculoServiceImpl;
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

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void whenGetEstatisticasVeiculosThenReturnSuccess() {
    when(acessoDadosVeiculos.getTotalRegistrosCadastrados()).thenReturn(50);
    when(acessoDadosVeiculos.getRegistrosCadastradosUltimoMes()).thenReturn(10);
    var content = service.getEstatisticasVeiculos();

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Veículos", content.getBody().getNomeEntidade());
    assertEquals(50, content.getBody().getTotal());
    // O calculo do crescimento é: (registrosCadastradosUltimoMes * 100) / totalRegistros
    assertEquals(20.0d, content.getBody().getCrescimento());
  }

  public void startEntities() {}
}