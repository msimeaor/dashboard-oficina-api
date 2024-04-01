package api.dashboard.model.services.impl;

import api.dashboard.model.repositories.VendaRepository;
import api.dashboard.model.services.VendaService;
import org.springframework.stereotype.Service;

@Service
public class VendaServiceImpl implements VendaService {

  private VendaRepository repository;

  public VendaServiceImpl(VendaRepository repository) {
    this.repository = repository;
  }

}
