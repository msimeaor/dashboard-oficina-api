package api.dashboard.unittests.services;

import api.dashboard.exceptions.ZeroCountException;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.ClienteServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    when(acessoDadosCliente.getRegistrosCadastradosUltimoMes()).thenReturn(20);
    var content = service.getEstatisticasClientes();

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Clientes", content.getBody().getNomeEntidade());
    assertEquals(100, content.getBody().getTotal());
    // O calculo do crescimento é: (registrosCadastradosUltimoMes * 100) / totalRegistros
    assertEquals(20.0d, content.getBody().getCrescimento());
  }

  @Test
  void whenGetEstatisticasClientesByMesThenReturnSuccess() {
    when(acessoDadosCliente.getRegistrosCadastradosUltimoMes()).thenReturn(20);
    when(acessoDadosCliente.getRegistrosCadastradosMesEspecifico(anyInt())).thenReturn(15);
    var content = service.getEstatisticasClientesByMes(1);

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(EstatisticasDTO.class, content.getBody().getClass());
    assertEquals("Clientes", content.getBody().getNomeEntidade());
    assertEquals(15, content.getBody().getTotal());
    // O calculo do crescimento é: ((totalCadastrosUltimoMes - totalCadastrosMesEspecifico) * 100) / totalCadastrosMesEspecifico
    assertEquals(33.333333333333336d, content.getBody().getCrescimento());
  }

  @Test
  void whenGetEstatisticasClientesByMesThenReturnZeroCountException() {
    when(acessoDadosCliente.getRegistrosCadastradosUltimoMes()).thenReturn(20);
    when(acessoDadosCliente.getRegistrosCadastradosMesEspecifico(anyInt())).thenReturn(0);

    try {
      service.getEstatisticasClientesByMes(1);
    } catch (Exception ex) {
      assertEquals(ZeroCountException.class, ex.getClass());
      assertEquals("Dados insuficientes!", ex.getMessage());
    }

    /*
    A exception é lançada sempre que o total de clientes cadastrados no mês filtrado for igual a 0.
    Neste caso é impossivel calcular o crescimento de cadastros.
    */
  }

  public void startEntities() {}
}