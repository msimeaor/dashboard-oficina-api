package api.dashboard.controllers;

import api.dashboard.model.services.impl.VendaServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vendas")
public class VendaRestController {

  private VendaServiceImpl service;

  public VendaRestController(VendaServiceImpl service) {
    this.service = service;
  }

}
