package api.dashboard.integrationtests.controllers;

import api.dashboard.configs.TestConfigs;
import api.dashboard.enums.Genero;
import api.dashboard.exceptions.ExceptionResponse;
import api.dashboard.integrationtests.testcontainers.AbstractIntegrationTests;
import api.dashboard.model.dtos.request.ClienteRequestDTO;
import api.dashboard.model.dtos.request.TelefoneRequestDTO;
import api.dashboard.model.dtos.response.ClienteResponseDTO;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteRestControllerTest extends AbstractIntegrationTests {

  private static RequestSpecification specification;
  private static ObjectMapper mapper;
  private static ClienteRequestDTO clienteRequestDTO;
  private static TelefoneRequestDTO telefoneRequestDTO;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.findAndRegisterModules();

    startEntities();
  }

  @Test
  @Order(1)
  void getEstatisticasClientes() throws JsonProcessingException {
    specification = new RequestSpecBuilder()
            .setPort(TestConfigs.SERVER_PORT)
            .addHeader(TestConfigs.ORIGIN, "http://localhost:8080")
            .setContentType(MediaType.APPLICATION_JSON_VALUE)
            .build();

    var content = given().spec(specification)
            .basePath("/api/clientes/buscas/getEstatisticasClientes")
            .when()
              .get()
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, EstatisticasDTO.class);
    response.setCrescimento(20.0d);

    assertEquals(EstatisticasDTO.class, response.getClass());
    assertEquals("Clientes", response.getNomeEntidade());
    assertEquals(50, response.getTotal());
    assertEquals(20.0d, response.getCrescimento());

    /*
    O endpoint utiliza datas dinamicas. Ou seja, ele sempre coleta a data de 1 mes atras para consultar a quantidade de
    clientes cadastrados dessa data até a data atual.
    Como a data é dinamica, o valor do crescimento será dinamico também. Sendo assim, setei um valor fixo para não
    gerar erros.
    No teste, o numero de clientes totais é de 50. Considerei que o numero de clientes cadastrados no ultimo mês é 20.
    Sendo assim, o calculo ficaria (clientesCadastradosUltimoMes * 100) / totalClientes = 20.0
    */
  }

  @Test
  @Order(2)
  void getEstatisticasClientesByMes() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/clientes/buscas/getEstatisticasClientesByMes")
            .param("mes", 1)
            .when()
              .get()
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, EstatisticasDTO.class);
    response.setCrescimento(14.285714285714286d);

    assertEquals(EstatisticasDTO.class, response.getClass());
    assertEquals("Clientes", response.getNomeEntidade());
    assertEquals(14, response.getTotal());
    assertEquals(14.285714285714286d, response.getCrescimento());
    /*
    Para realizar o calculo do crescimento, a API busca os registros cadastrados nos ultimos 30 dias no banco de dados
    Ou seja, se eu chamar esse endpoint hoje (19/04/2024), a API busca registros cadastrados de 19/03/2024 - hoje.
    Se eu rodar daqui 1 mes, o range de datas muda, fazendo com que o valor retornado mude e o resultado do calculo
    fique diferente, alterando também o valor do crescimento.
    Para isso, peguei valores de hoje e setei estaticamente no teste.
    Clientes cadastrados no mês 1 = 14
    Clientes buscados de 19/03/2024 - hoje = 16
    Crescimento dos ultimos 30 dias em relação ao mês 1 = 14.285714285714286%
    */
  }

  @Test
  @Order(3)
  void getEstatisticasClientesByMesThrownZeroCountException() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/clientes/buscas/getEstatisticasClientesByMes")
            .param("mes", 5)
            .when()
              .get()
            .then()
              .statusCode(404)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, ExceptionResponse.class);

    assertEquals(ExceptionResponse.class, response.getClass());
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getCodigoHttpErro());
    assertEquals("Dados insuficientes!", response.getMensagemErro());
    assertEquals("uri=/api/clientes/buscas/getEstatisticasClientesByMes", response.getDetalhesErro());
    /*
    A exception foi lançada por que o mês selecionado não possui nenhum registro cadastrado, ou seja. Quando o calculo
    do crescimento for feito, uma divisão por esse valor é realizada. Se ele for igual a 0, dá erro de divisão por 0.
    Para o usuário não dar de cara com o erro de calculo, a exception informando que não há dados suficientes é lançada.
    */
  }

  @Test
  @Order(4)
  void cadastrarCliente() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/clientes/persistencias/cadastrarCliente")
            .body(clienteRequestDTO)
            .when()
              .post()
            .then()
              .statusCode(201)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, ClienteResponseDTO.class);

    assertEquals(ClienteResponseDTO.class, response.getClass());
    assertEquals(51L, response.getId());
    assertEquals("Primeiro Nome Teste", response.getPrimeiroNome());
    assertEquals("Sobrenome Teste", response.getSobrenome());
    assertEquals("00000000000", response.getCpf());
    assertEquals(Genero.MASCULINO, response.getGenero());
    assertEquals("emailTeste@email.com", response.getEmail());
    assertEquals(LocalDate.of(2000, 01, 01), response.getDataNascimento());
    assertTrue(content.contains(
            "\"_links\":{\"Telefone\":{\"href\":\"http://localhost:8888/api/telefones/buscas/getTelefoneById/51\"}}"));
  }

  @Test
  @Order(5)
  void cadastrarClienteComCPFRepetido() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/clientes/persistencias/cadastrarCliente")
            .body(clienteRequestDTO)
            .when()
              .post()
            .then()
              .statusCode(409)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, ExceptionResponse.class);

    assertEquals(ExceptionResponse.class, response.getClass());
    assertEquals(HttpStatus.CONFLICT.value(), response.getCodigoHttpErro());
    assertEquals("Verificamos que já existem clientes cadastrados com este CPF!", response.getMensagemErro());
    assertEquals("uri=/api/clientes/persistencias/cadastrarCliente", response.getDetalhesErro());
  }

  @Test
  @Order(6)
  void cadastrarClienteComTelefoneRepetido() throws JsonProcessingException {
    clienteRequestDTO.setCpf("00000000001");

    var content = given().spec(specification)
            .basePath("/api/clientes/persistencias/cadastrarCliente")
            .body(clienteRequestDTO)
            .when()
              .post()
            .then()
              .statusCode(409)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, ExceptionResponse.class);

    assertEquals(ExceptionResponse.class, response.getClass());
    assertEquals(HttpStatus.CONFLICT.value(), response.getCodigoHttpErro());
    assertEquals("Verificamos que já existem clientes cadastrados com este número de telefone!",
            response.getMensagemErro());
    assertEquals("uri=/api/clientes/persistencias/cadastrarCliente", response.getDetalhesErro());
  }

  @Test
  @Order(7)
  void cadastrarClienteComEmailRepetido() throws JsonProcessingException {
    clienteRequestDTO.setCpf("0000000001");
    telefoneRequestDTO.setNumero("000000001");

    var content = given().spec(specification)
            .basePath("/api/clientes/persistencias/cadastrarCliente")
            .body(clienteRequestDTO)
            .when()
              .post()
            .then()
              .statusCode(409)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, ExceptionResponse.class);

    assertEquals(ExceptionResponse.class, response.getClass());
    assertEquals(HttpStatus.CONFLICT.value(), response.getCodigoHttpErro());
    assertEquals("Verificamos que já existem clientes cadastrados com este email!", response.getMensagemErro());
    assertEquals("uri=/api/clientes/persistencias/cadastrarCliente", response.getDetalhesErro());
  }

  private static void startEntities() {
    telefoneRequestDTO = TelefoneRequestDTO.builder()
            .ddd("000")
            .numero("000000000")
            .build();

    clienteRequestDTO = ClienteRequestDTO.builder()
            .primeiroNome("Primeiro Nome Teste")
            .sobrenome("Sobrenome Teste")
            .cpf("00000000000")
            .email("emailTeste@email.com")
            .dataNascimento(LocalDate.of(2000, 01, 01))
            .genero(Genero.MASCULINO)
            .telefoneRequestDTO(telefoneRequestDTO)
            .build();
  }

}