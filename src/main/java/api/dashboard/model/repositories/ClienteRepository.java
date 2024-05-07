package api.dashboard.model.repositories;

import api.dashboard.model.entities.Cliente;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  @Query("SELECT COUNT(c) FROM Cliente c " +
          "WHERE c.dataCriacao >= :umMesAtras")
  Integer countClienteLast30DaysByDataCriacao(@PathParam("umMesAtras") LocalDate umMesAtras);

  @Query("SELECT COUNT(*) FROM Cliente")
  Integer countTotalClientesByDataCriacao();

  @Query("SELECT COUNT(c) FROM Cliente c " +
          "WHERE c.dataCriacao >= :dataInicial " +
          "AND c.dataCriacao <= :dataFinal")
  Integer countClienteBetween2Dates(@PathParam("dataInicio") LocalDate dataInicial,
                                    @PathParam("dataFinal") LocalDate dataFinal);

  Optional<Cliente> findByCpf(String cpf);
  Optional<Cliente> findByEmail(String email);

}
