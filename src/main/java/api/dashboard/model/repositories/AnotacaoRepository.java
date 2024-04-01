package api.dashboard.model.repositories;

import api.dashboard.model.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnotacaoRepository extends JpaRepository<Venda, Long> { }
