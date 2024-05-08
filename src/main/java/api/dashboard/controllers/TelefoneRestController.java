package api.dashboard.controllers;

import api.dashboard.model.dtos.response.TelefoneResponseDTO;
import api.dashboard.model.services.impl.TelefoneServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/telefones")
public class TelefoneRestController {

  private TelefoneServiceImpl service;

  public TelefoneRestController(TelefoneServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/buscas/getTelefoneById/{id}")
  public ResponseEntity<TelefoneResponseDTO> getTelefoneById(@PathVariable("id") Long id) {
    return service.getTelefoneById(id);
  }

}
