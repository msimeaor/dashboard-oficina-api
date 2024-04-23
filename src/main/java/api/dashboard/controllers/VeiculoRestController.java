package api.dashboard.controllers;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.VeiculoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/veiculos")
public class VeiculoRestController {

  private VeiculoServiceImpl service;

  public VeiculoRestController(VeiculoServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/buscas/getEstatisticasVeiculos")
  public ResponseEntity<EstatisticasDTO> getEstatisticasVeiculos() {
    return service.getEstatisticasVeiculos();
  }

}
