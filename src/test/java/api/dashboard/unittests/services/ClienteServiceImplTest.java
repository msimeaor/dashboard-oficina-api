package api.dashboard.unittests.services;

import api.dashboard.enums.Genero;
import api.dashboard.exceptions.ZeroCountException;
import api.dashboard.model.dtos.request.ClienteRequestDTO;
import api.dashboard.model.dtos.request.TelefoneRequestDTO;
import api.dashboard.model.dtos.response.ClienteResponseDTO;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.entities.Cliente;
import api.dashboard.model.entities.Telefone;
import api.dashboard.model.services.impl.ClienteServiceImpl;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.businessLogicEndpoints.clientes.LogicaCadastrarCliente;
import api.dashboard.utilities.searches.AcessoDadosClientes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

  @InjectMocks
  private ClienteServiceImpl service;
  @Mock
  private AcessoDadosClientes acessoDadosClientes;
  @Mock
  private Calculos calculos;
  @Mock
  private LogicaCadastrarCliente logicaCadastrarCliente;

  private ClienteRequestDTO clienteRequestDTO;
  private TelefoneRequestDTO telefoneRequestDTO;
  private Cliente cliente;
  private Telefone telefone;
  private ClienteResponseDTO clienteResponseDTO;

  private final String PRIMEIRO_NOME = "Primeiro Nome Teste";
  private final String SOBRENOME = "Sobrenome Teste";
  private final String CPF = "00000000000";
  private final String EMAIL = "emailTeste@email.com";


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void whenGetEstatisticasClientesThenReturnSuccess() {
    when(acessoDadosClientes.getTotalRegistrosCadastrados()).thenReturn(100);
    when(calculos.calcularCrescimentoUltimoMesEmRelacaoAoTotal(any())).thenReturn(20.0d);
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
    when(acessoDadosClientes.getRegistrosCadastradosMesEspecifico(anyInt())).thenReturn(15);
    when(calculos.calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(any(), anyInt()))
            .thenReturn(33.333333333333336d);
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
    when(acessoDadosClientes.getRegistrosCadastradosMesEspecifico(anyInt())).thenReturn(0);

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

  @Test
  void whenCadastrarClienteThenReturnSuccess() {
    when(logicaCadastrarCliente.criarESalvarTelefoneDoCliente(
            any(TelefoneRequestDTO.class))).thenReturn(telefone);
    when(logicaCadastrarCliente.criarESalvarCliente(
            any(ClienteRequestDTO.class), any(Telefone.class))).thenReturn(cliente);
    when(logicaCadastrarCliente.criarLinksHateoasDeCliente(
            any(Cliente.class))).thenReturn(clienteResponseDTO);

    var content = service.cadastrarCliente(clienteRequestDTO);

    assertEquals(HttpStatus.CREATED, content.getStatusCode());
    assertEquals(ClienteResponseDTO.class, content.getBody().getClass());
    assertEquals(1L, content.getBody().getId());
    assertEquals(PRIMEIRO_NOME, content.getBody().getPrimeiroNome());
    assertEquals(SOBRENOME, content.getBody().getSobrenome());
    assertEquals(CPF, content.getBody().getCpf());
    assertEquals(Genero.MASCULINO, content.getBody().getGenero());
    assertEquals(EMAIL, content.getBody().getEmail());
    assertEquals(LocalDate.of(2000, 01, 01), content.getBody().getDataNascimento());
  }

  public void startEntities() {
    telefoneRequestDTO = TelefoneRequestDTO.builder()
            .ddd("000")
            .numero("000000000")
            .build();

    clienteRequestDTO = ClienteRequestDTO.builder()
            .primeiroNome(PRIMEIRO_NOME)
            .sobrenome(SOBRENOME)
            .cpf(CPF)
            .genero(Genero.MASCULINO)
            .email(EMAIL)
            .dataNascimento(LocalDate.of(2000, 01, 01))
            .telefoneRequestDTO(telefoneRequestDTO)
            .build();

    telefone = Telefone.builder()
            .id(1L)
            .ddd("000")
            .numero("000000000")
            .build();

    cliente = Cliente.builder()
            .id(1L)
            .primeiroNome(PRIMEIRO_NOME)
            .sobrenome(SOBRENOME)
            .cpf(CPF)
            .genero(Genero.MASCULINO)
            .email(EMAIL)
            .dataNascimento(LocalDate.of(2000, 01, 01))
            .dataCriacao(LocalDate.of(2024, 01, 01))
            .telefone(telefone)
            .build();

    clienteResponseDTO = ClienteResponseDTO.builder()
            .id(1L)
            .primeiroNome(PRIMEIRO_NOME)
            .sobrenome(SOBRENOME)
            .cpf(CPF)
            .genero(Genero.MASCULINO)
            .email(EMAIL)
            .dataNascimento(LocalDate.of(2000, 01, 01))
            .build();
  }
}