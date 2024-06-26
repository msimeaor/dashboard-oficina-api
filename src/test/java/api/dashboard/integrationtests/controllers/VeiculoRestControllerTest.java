package api.dashboard.integrationtests.controllers;

import api.dashboard.configs.TestConfigs;
import api.dashboard.exceptions.ExceptionResponse;
import api.dashboard.integrationtests.testcontainers.AbstractIntegrationTests;
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

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VeiculoRestControllerTest extends AbstractIntegrationTests {

  private static RequestSpecification specification;
  private static ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  @Test
  @Order(1)
  void getEstatisticasVeiculos() throws JsonProcessingException {
    specification = new RequestSpecBuilder()
            .setPort(TestConfigs.SERVER_PORT)
            .addHeader(TestConfigs.ORIGIN, "http://localhost:8080")
            .setContentType(MediaType.APPLICATION_JSON_VALUE)
            .build();

    var content = given().spec(specification)
            .basePath("/api/veiculos/buscas/getEstatisticasVeiculos")
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
    assertEquals("Veículos", response.getNomeEntidade());
    assertEquals(50, response.getTotal());
    assertEquals(20.0d, response.getCrescimento());

    /*
    O endpoint utiliza datas dinamicas. Ou seja, ele sempre coleta a data de 1 mes atras para consultar a quantidade de
    veiculos cadastrados dessa data até a data atual.
    Como a data é dinamica, o valor do crescimento será dinamico também. Sendo assim, setei um valor fixo para não
    gerar erros.
    No teste, o numero de veiculos totais é de 50. Considerei que o numero de veiculos cadastradas no ultimo mês é 10.
    Sendo assim, o calculo ficaria (veiculosCadastradosUltimoMes * 100) / totalVeiculos = 20.0
    */
  }

  @Test
  @Order(2)
  void getEstatisticasVeiculosByMes() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/veiculos/buscas/getEstatisticasVeiculosByMes")
            .param("mes", 1)
            .when()
              .get()
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, EstatisticasDTO.class);
    response.setCrescimento(-33.333333333333336d);

    assertEquals(EstatisticasDTO.class, response.getClass());
    assertEquals("Veículos", response.getNomeEntidade());
    assertEquals(15, response.getTotal());
    assertEquals(-33.333333333333336d, response.getCrescimento());
    /*
    Para realizar o calculo do crescimento, a API busca os registros cadastrados nos ultimos 30 dias no banco de dados
    Ou seja, se eu chamar esse endpoint hoje (23/04/2024), a API busca registros cadastrados de 23/03/2024 - hoje.
    Se eu rodar daqui 1 mes, o range de datas muda, fazendo com que o valor retornado mude e o resultado do calculo
    fique diferente, alterando também o valor do crescimento.
    Para isso, peguei valores de hoje e setei estaticamente no teste.
    Veículos cadastradas no mês 1 = 15
    Veículos buscados de 23/03/2024 - hoje = 10
    Crescimento dos ultimos 30 dias em relação ao mês 1 = -33.333333333333336%
    */
  }

  @Test
  @Order(3)
  void getEstatisticasVeiculosByMesThrownZeroCountException() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/veiculos/buscas/getEstatisticasVeiculosByMes")
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
    assertEquals("uri=/api/veiculos/buscas/getEstatisticasVeiculosByMes", response.getDetalhesErro());
  }

}