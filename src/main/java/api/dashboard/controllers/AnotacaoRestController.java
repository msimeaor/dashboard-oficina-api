package api.dashboard.controllers;

import api.dashboard.model.services.impl.AnotacaoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/anotacoes")
public class AnotacaoRestController {

  private AnotacaoServiceImpl service;

  public AnotacaoRestController(AnotacaoServiceImpl service) {
    this.service = service;
  }

}
