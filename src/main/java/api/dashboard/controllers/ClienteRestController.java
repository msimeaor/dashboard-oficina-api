package api.dashboard.controllers;

import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.services.impl.ClienteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/clientes")
public class ClienteRestController {

  private ClienteServiceImpl service;

  public ClienteRestController(ClienteServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/buscas/getEstatisticasClientes")
  public ResponseEntity<EstatisticasDTO> getEstatisticasClientes() {

    return service.getEstatisticasClientes();
  }


}
