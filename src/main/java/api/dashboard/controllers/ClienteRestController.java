package api.dashboard.controllers;

import api.dashboard.exceptions.ExceptionResponse;
import api.dashboard.model.dtos.request.ClienteRequestDTO;
import api.dashboard.model.dtos.response.ClienteResponseDTO;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.ClienteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/clientes")
@Tag(name = "Cliente Controller", description = "Controller com endpoints para gerenciamento de registros de clientes")
public class ClienteRestController {

  private ClienteServiceImpl service;

  public ClienteRestController(ClienteServiceImpl service) {
    this.service = service;
  }

  @Operation(summary = "Coletar estatisticas de clientes",
    description = "Coletar total de clientes cadastrados no sistema e a porcentagem de crescimento dos clientes " +
                  "cadastrados no ultimo mês em relação ao total cadastrado no sistema.",
    tags = {"Busca"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = EstatisticasDTO.class)
          )
        }
      ),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Forbiden", responseCode = "403", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  @GetMapping("/buscas/getEstatisticasClientes")
  public ResponseEntity<EstatisticasDTO> getEstatisticasClientes() {

    return service.getEstatisticasClientes();
  }

  @Operation(summary = "Coletar estatisticas dos clientes por mês",
    description = "Coletar total de clientes cadastrados no sistema filtrando por um mês e a porcentagem " +
                  "de crescimento dos clientes cadastrados no ultimo mês em relação ao total cadastrado no mês selecionado.",
    tags = {"Busca"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = EstatisticasDTO.class)
          )
        }
      ),
      @ApiResponse(description = "Zero clientes cadastrados no mês selecionado", responseCode = "404",
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
  @GetMapping("/buscas/getEstatisticasClientesByMes")
  public ResponseEntity<EstatisticasDTO> getEstatisticasClientesByMes(
          @RequestParam(name = "mes", defaultValue = "1") Integer mes
  ) {

    return service.getEstatisticasClientesByMes(mes);
  }

  @Operation(summary = "Cadastrar novo cliente",
    description = "Cadastrar novo cliente e numero de telefone do cliente no banco de dados.",
    tags = {"Cadastro"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "201",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClienteResponseDTO.class)
          )
        }
      ),
      @ApiResponse(description = "CPF, número de telefone ou email já existente no banco de dados", responseCode = "409",
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
  @PostMapping("/persistencias/cadastrarCliente")
  public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
    return service.cadastrarCliente(clienteRequestDTO);
  }

}
