package api.dashboard.model.services.impl;

import api.dashboard.model.repositories.VeiculoRepository;
import api.dashboard.model.services.VeiculoService;
import org.springframework.stereotype.Service;

@Service
public class VeiculoServiceImpl implements VeiculoService {

  private VeiculoRepository repository;

  public VeiculoServiceImpl(VeiculoRepository repository) {
    this.repository = repository;
  }

}
