package api.dashboard.controllers;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.VendaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vendas")
public class VendaRestController {

  private VendaServiceImpl service;

  public VendaRestController(VendaServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/buscas/getEstatisticasVendas")
  public ResponseEntity<EstatisticasDTO> getEstatisticasVendas() {
    return service.getEstatisticasVendas();
  }

}
