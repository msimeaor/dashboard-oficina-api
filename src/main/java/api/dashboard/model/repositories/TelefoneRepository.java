package api.dashboard.model.repositories;

import api.dashboard.model.entities.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

  Optional<Telefone> findByNumero(String numero);

}
