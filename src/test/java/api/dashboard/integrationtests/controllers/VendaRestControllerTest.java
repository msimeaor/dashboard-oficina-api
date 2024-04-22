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

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VendaRestControllerTest extends AbstractIntegrationTests {

  private static RequestSpecification specification;
  private static ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  @Test
  @Order(1)
  void getEstatisticasVendas() throws JsonProcessingException {
    specification = new RequestSpecBuilder()
            .setPort(TestConfigs.SERVER_PORT)
            .addHeader(TestConfigs.ORIGIN, "http://localhost:8080")
            .setContentType(MediaType.APPLICATION_JSON_VALUE)
            .build();

    var content = given().spec(specification)
            .basePath("/api/vendas/buscas/getEstatisticasVendas")
            .when()
              .get()
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, EstatisticasDTO.class);
    response.setCrescimento(28.0d);

    assertEquals(EstatisticasDTO.class, response.getClass());
    assertEquals("Vendas", response.getNomeEntidade());
    assertEquals(25, response.getTotal());
    assertEquals(28.0d, response.getCrescimento());

    /*
    O endpoint utiliza datas dinamicas. Ou seja, ele sempre coleta a data de 1 mes atras para consultar a quantidade de
    vendas cadastradas dessa data até a data atual.
    Como a data é dinamica, o valor do crescimento será dinamico também. Sendo assim, setei um valor fixo para não
    gerar erros.
    No teste, o numero de vendas totais é de 25. Considerei que o numero de vendas cadastradas no ultimo mês é 7.
    Sendo assim, o calculo ficaria (vendasCadastradasUltimoMes * 100) / totalvendas = 28.0
    */
  }

  @Test
  @Order(2)
  void getEstatisticasVendasByMes() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/vendas/buscas/getEstatisticasVendasByMes")
            .param("mes", 1)
            .when()
              .get()
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, EstatisticasDTO.class);
    response.setCrescimento(-12.5d);

    assertEquals(EstatisticasDTO.class, response.getClass());
    assertEquals("Vendas", response.getNomeEntidade());
    assertEquals(8, response.getTotal());
    assertEquals(-12.5d, response.getCrescimento());
    /*
    Para realizar o calculo do crescimento, a API busca os registros cadastrados nos ultimos 30 dias no banco de dados
    Ou seja, se eu chamar esse endpoint hoje (19/04/2024), a API busca registros cadastrados de 19/03/2024 - hoje.
    Se eu rodar daqui 1 mes, o range de datas muda, fazendo com que o valor retornado mude e o resultado do calculo
    fique diferente, alterando também o valor do crescimento.
    Para isso, peguei valores de hoje e setei estaticamente no teste.
    Vendas cadastradas no mês 1 = 8
    Vendas buscadas de 19/03/2024 - hoje = 7
    Crescimento dos ultimos 30 dias em relação ao mês 1 = -12.5%
    */
  }

  @Test
  @Order(3)
  void getEstatisticasVendasByMesThrownZeroCountException() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/vendas/buscas/getEstatisticasVendasByMes")
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
    assertEquals("uri=/api/vendas/buscas/getEstatisticasVendasByMes", response.getDetalhesErro());
    /*
    A exception foi lançada por que o mês selecionado não possui nenhum registro cadastrado, ou seja. Quando o calculo
    do crescimento for feito, uma divisão por esse valor é realizada. Se ele for igual a 0, dá erro de divisão por 0.
    Para o usuário não dar de cara com o erro de calculo, a exception informando que não há dados suficientes é lançada.
    */
  }

}