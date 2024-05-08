package api.dashboard.controllers;

import api.dashboard.exceptions.ExceptionResponse;
import api.dashboard.model.dtos.response.TelefoneResponseDTO;
import api.dashboard.model.services.impl.TelefoneServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/telefones")
@Tag(name = "Telefone Controller", description = "Controller com endpoints para gerenciamento de registros de telefones")
public class TelefoneRestController {

  private TelefoneServiceImpl service;

  public TelefoneRestController(TelefoneServiceImpl service) {
    this.service = service;
  }

  @Operation(summary = "Buscar telefone por ID",
    description = "Buscar um registro de telefone no banco de dados pelo seu ID",
    tags = {"Busca"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TelefoneResponseDTO.class)
          )
        }
      ),
      @ApiResponse(description = "Telefone não encontrado", responseCode = "404",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ExceptionResponse.class)
          )
        }
      ),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Forbiden", responseCode = "403", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  @GetMapping("/buscas/getTelefoneById/{id}")
  public ResponseEntity<TelefoneResponseDTO> getTelefoneById(@PathVariable("id") Long id) {
    return service.getTelefoneById(id);
  }

}
