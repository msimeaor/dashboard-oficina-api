package api.dashboard.controllers;

import api.dashboard.model.services.impl.ClienteServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/clientes")
public class ClienteRestController {

  private ClienteServiceImpl service;

  public ClienteRestController(ClienteServiceImpl service) {
    this.service = service;
  }

}
