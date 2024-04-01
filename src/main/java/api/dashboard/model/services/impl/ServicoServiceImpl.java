package api.dashboard.model.services.impl;

import api.dashboard.model.repositories.ServicoRepository;
import api.dashboard.model.services.ServicoService;
import org.springframework.stereotype.Service;

@Service
public class ServicoServiceImpl implements ServicoService {

  private ServicoRepository repository;

  public ServicoServiceImpl(ServicoRepository repository) {
    this.repository = repository;
  }

}
