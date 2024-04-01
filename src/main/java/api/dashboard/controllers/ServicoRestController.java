package api.dashboard.controllers;

import api.dashboard.model.services.impl.ServicoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/servicos")
public class ServicoRestController {

  private ServicoServiceImpl service;

  public ServicoRestController(ServicoServiceImpl service) {
    this.service = service;
  }

}
