package api.dashboard.utilities.searches;

import api.dashboard.exceptions.NotFoundException;
import api.dashboard.model.entities.Telefone;
import api.dashboard.model.repositories.TelefoneRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AcessoDadosTelefones {

  private TelefoneRepository repository;

  public AcessoDadosTelefones(TelefoneRepository repository) {
    this.repository = repository;
  }

  public Telefone getTelefoneById(Long id) {
    Optional<Telefone> telefoneOptional = repository.findById(id);
    if (!telefoneOptional.isPresent())
      throw new NotFoundException("Telefone não encontrado!");

    return telefoneOptional.get();
  }

}
