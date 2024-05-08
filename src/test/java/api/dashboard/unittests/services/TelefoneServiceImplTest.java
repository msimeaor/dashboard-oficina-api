package api.dashboard.unittests.services;

import api.dashboard.exceptions.NotFoundException;
import api.dashboard.model.dtos.response.TelefoneResponseDTO;
import api.dashboard.model.entities.Telefone;
import api.dashboard.model.services.impl.TelefoneServiceImpl;
import api.dashboard.utilities.searches.AcessoDadosTelefones;
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
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TelefoneServiceImplTest {

  @InjectMocks
  private TelefoneServiceImpl service;
  @Mock
  private AcessoDadosTelefones acessoDadosTelefones;

  private Telefone telefone;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void whenGetTelefoneByIdThenReturnSuccess() {
    when(acessoDadosTelefones.getTelefoneById(anyLong())).thenReturn(telefone);

    var content = service.getTelefoneById(1L);

    assertEquals(HttpStatus.OK, content.getStatusCode());
    assertEquals(TelefoneResponseDTO.class, content.getBody().getClass());
    assertEquals("000", content.getBody().getDdd());
    assertEquals("000000000", content.getBody().getNumero());
  }

  @Test
  void whenGetTelefoneByIdWithInvalidIdThenThrowNotFoundException() {
    when(acessoDadosTelefones.getTelefoneById(anyLong())).thenThrow(
            new NotFoundException("Telefone não encontrado!"));

    try {
      service.getTelefoneById(51L);
    } catch (Exception ex) {
      assertEquals(NotFoundException.class, ex.getClass());
      assertEquals("Telefone não encontrado!", ex.getMessage());
    }
  }

  public void startEntities() {
    telefone = Telefone.builder()
            .id(1L)
            .ddd("000")
            .numero("000000000")
            .build();
  }

}