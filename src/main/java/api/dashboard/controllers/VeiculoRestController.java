package api.dashboard.controllers;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.VeiculoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/veiculos")
@Tag(name = "Veiculo Controller", description = "Controller com endpoints para gerenciamento de registros de veículos")
public class VeiculoRestController {

  private VeiculoServiceImpl service;

  public VeiculoRestController(VeiculoServiceImpl service) {
    this.service = service;
  }

  @Operation(summary = "Coletar estatisticas de veículos",
    description = "Coletar total de veículos cadastrados no sistema e a porcentagem de crescimento dos veículos " +
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
  @GetMapping("/buscas/getEstatisticasVeiculos")
  public ResponseEntity<EstatisticasDTO> getEstatisticasVeiculos() {
    return service.getEstatisticasVeiculos();
  }

  @GetMapping("/buscas/getEstatisticasVeiculosByMes")
  public ResponseEntity<EstatisticasDTO> getEstatisticasVeiculosByMes(
          @RequestParam(name = "mes", defaultValue = "1") Integer mes) {

    return service.getEstatisticasVeiculosByMes(mes);
  }

}
