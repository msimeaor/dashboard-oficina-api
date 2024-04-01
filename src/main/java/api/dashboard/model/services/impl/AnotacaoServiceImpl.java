package api.dashboard.model.services.impl;

import api.dashboard.model.repositories.AnotacaoRepository;
import api.dashboard.model.services.AnotacaoService;
import org.springframework.stereotype.Service;

@Service
public class AnotacaoServiceImpl implements AnotacaoService {

  private AnotacaoRepository repository;

  public AnotacaoServiceImpl(AnotacaoRepository repository) {
    this.repository = repository;
  }

}
