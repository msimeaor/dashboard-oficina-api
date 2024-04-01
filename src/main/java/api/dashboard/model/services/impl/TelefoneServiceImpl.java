package api.dashboard.model.services.impl;

import api.dashboard.model.repositories.TelefoneRepository;
import api.dashboard.model.services.TelefoneService;
import org.springframework.stereotype.Service;

@Service
public class TelefoneServiceImpl implements TelefoneService {

  private TelefoneRepository repository;

  public TelefoneServiceImpl(TelefoneRepository repository) {
    this.repository = repository;
  }

}
