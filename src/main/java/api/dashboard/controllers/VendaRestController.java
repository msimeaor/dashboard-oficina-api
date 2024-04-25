package api.dashboard.controllers;

import api.dashboard.exceptions.ExceptionResponse;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.dtos.response.ResumoCadastrosMesDTO;
import api.dashboard.model.services.impl.VendaServiceImpl;
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

import java.util.List;

@RestController
@RequestMapping("api/vendas")
@Tag(name = "Venda Controller", description = "Controller com endpoints para gerenciamento de registros de vendas")
public class VendaRestController {

  private VendaServiceImpl service;

  public VendaRestController(VendaServiceImpl service) {
    this.service = service;
  }

  @Operation(summary = "Coletar estatisticas de vendas",
    description = "Coletar total de vendas cadastradas no sistema e a porcentagem de crescimento das vendas " +
                  "cadastradas no ultimo mês em relação ao total cadastrada no sistema.",
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
  @GetMapping("/buscas/getEstatisticasVendas")
  public ResponseEntity<EstatisticasDTO> getEstatisticasVendas() {
    return service.getEstatisticasVendas();
  }

  @Operation(summary = "Coletar estatisticas dos clientes por mês",
    description = "Coletar total de vendas cadastradas no sistema filtrando por um mês e a porcentagem " +
            "de crescimento das vendas cadastradas no ultimo mês em relação ao total cadastrada no mês selecionado.",
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
      @ApiResponse(description = "Zero vendas cadastradas no mês selecionado", responseCode = "404",
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
  @GetMapping("/buscas/getEstatisticasVendasByMes")
  public ResponseEntity<EstatisticasDTO> getEstatisticasVendasByMes(
          @RequestParam(name = "mes", defaultValue = "1") Integer mes) {

    return service.getEstatisticasVendasByMes(mes);
  }

  @GetMapping("/buscas/getResumoVendasMensais")
  public ResponseEntity<List<ResumoCadastrosMesDTO>> getResumoVendasMensais() {
    return service.getResumoVendasMensais();
  }

}
