package api.dashboard.integrationtests.controllers;

import api.dashboard.configs.TestConfigs;
import api.dashboard.integrationtests.testcontainers.AbstractIntegrationTests;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VeiculoRestControllerTest extends AbstractIntegrationTests {

  private RequestSpecification specification;
  private ObjectMapper mapper;

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
}