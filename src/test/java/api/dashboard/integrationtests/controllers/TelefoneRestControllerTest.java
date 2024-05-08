package api.dashboard.integrationtests.controllers;

import api.dashboard.configs.TestConfigs;
import api.dashboard.exceptions.ExceptionResponse;
import api.dashboard.integrationtests.testcontainers.AbstractIntegrationTests;
import api.dashboard.model.dtos.response.TelefoneResponseDTO;
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
class TelefoneRestControllerTest extends AbstractIntegrationTests {

  private static RequestSpecification specification;
  private static ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  @Test
  @Order(1)
  void getTelefoneById() throws JsonProcessingException {
    specification = new RequestSpecBuilder()
            .setPort(TestConfigs.SERVER_PORT)
            .addHeader(TestConfigs.ORIGIN, "http://localhost:8080")
            .setContentType(MediaType.APPLICATION_JSON_VALUE)
            .build();

    var content = given().spec(specification)
            .basePath("/api/telefones/buscas/getTelefoneById")
            .pathParam("id", 1)
            .when()
              .get("{id}")
            .then()
              .statusCode(200)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, TelefoneResponseDTO.class);

    assertEquals(TelefoneResponseDTO.class, response.getClass());
    assertEquals("918", response.getDdd());
    assertEquals("496104144", response.getNumero());
    // TODO Criar validação do link HATEOAS de cliente
  }

  @Test
  @Order(2)
  void getTelefoneByIdWithInvalidId() throws JsonProcessingException {
    var content = given().spec(specification)
            .basePath("/api/telefones/buscas/getTelefoneById")
            .pathParam("id", 51)
            .when()
              .get("{id}")
            .then()
              .statusCode(404)
            .extract()
              .body()
                .asString();

    var response = mapper.readValue(content, ExceptionResponse.class);

    assertEquals(ExceptionResponse.class, response.getClass());
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getCodigoHttpErro());
    assertEquals("Telefone não encontrado!", response.getMensagemErro());
    assertEquals("uri=/api/telefones/buscas/getTelefoneById/51", response.getDetalhesErro());
  }

}