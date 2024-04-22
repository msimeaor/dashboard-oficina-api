package api.dashboard.unittests.services;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.VendaServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class VendaServiceImplTest {

  @InjectMocks
  private VendaServiceImpl service;
  @Mock
  private AcessoDadosVendas acessoDadosVendas;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void whenGetEstatisticasVendasThenReturnSuccess() {
    when(acessoDadosVendas.getTotalRegistrosCadastrados()).thenReturn(25);
    when(acessoDadosVendas.getRegistrosCadastradosUltimoMes()).thenReturn(7);
    var content = service.getEstatisticasVendas();

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Vendas", content.getBody().getNomeEntidade());
    assertEquals(25, content.getBody().getTotal());
    // O calculo do crescimento é: (registrosCadastradosUltimoMes * 100) / totalRegistros
    assertEquals(28.0d, content.getBody().getCrescimento());
  }

  public void startEntities() {}

}