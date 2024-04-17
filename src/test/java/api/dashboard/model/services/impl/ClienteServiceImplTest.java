package api.dashboard.model.services.impl;

import api.dashboard.exceptions.ZeroRegistrosEncontradosException;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.utilities.clientes.AcessoDadosCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

  @InjectMocks
  private ClienteServiceImpl service;
  @Mock
  private AcessoDadosCliente acessoDadosCliente;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void whenGetEstatisticasClientesThenReturnSuccess() {
    when(acessoDadosCliente.getTotalRegistrosCadastrados()).thenReturn(100);
    when(acessoDadosCliente.getRegistrosCadastradosUltimos30Dias()).thenReturn(15);
    when(acessoDadosCliente.getRegistrosCadastradosEntreUltimos30E60Dias()).thenReturn(10);

    var content = service.getEstatisticasClientes();

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Clientes", content.getBody().getNomeEntidade());
    assertEquals(100, content.getBody().getTotal());
    assertEquals(50.0d, content.getBody().getCrescimento());
  }

  @Test
  void whenGetEstatisticasClientesThenReturnZeroRegistrosEncontradosException() {
    when(acessoDadosCliente.getTotalRegistrosCadastrados()).thenReturn(100);
    when(acessoDadosCliente.getRegistrosCadastradosUltimos30Dias()).thenReturn(15);
    // O método lança a exception quando não houverem clientes cadastrados no mês retrasado a partir da data atual.
    // Por que a divisão no calculo é feita por 0.
    when(acessoDadosCliente.getRegistrosCadastradosEntreUltimos30E60Dias()).thenReturn(0);

    try {
      service.getEstatisticasClientes();
    } catch (Exception ex) {
      assertEquals(ZeroRegistrosEncontradosException.class, ex.getClass());
      assertEquals("Não há registros cadastrados no penúltimo mês!", ex.getMessage());
    }
  }

  public void startEntities() {}
}