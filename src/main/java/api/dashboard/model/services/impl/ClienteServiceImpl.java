package api.dashboard.model.services.impl;

import api.dashboard.model.repositories.ClienteRepository;
import api.dashboard.model.services.ClienteService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

  private ClienteRepository repository;

  public ClienteServiceImpl(ClienteRepository repository) {
    this.repository = repository;
  }

}
