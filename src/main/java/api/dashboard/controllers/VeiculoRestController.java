package api.dashboard.controllers;

import api.dashboard.model.services.impl.VeiculoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/veiculos")
public class VeiculoRestController {

  private VeiculoServiceImpl service;

  public VeiculoRestController(VeiculoServiceImpl service) {
    this.service = service;
  }

}
