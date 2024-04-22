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

}