package api.dashboard.model.repositories;

import api.dashboard.model.entities.Cliente;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  @Query("SELECT COUNT(c) FROM Cliente c " +
          "WHERE c.dataCriacao >= :umMesAtras")
  Integer countClienteLast30DaysByDataCriacao(@PathParam("umMesAtras") LocalDate umMesAtras);

  @Query("SELECT COUNT(c) FROM Cliente c " +
          "WHERE c.dataCriacao >= :doisMesesAtras " +
          "AND c.dataCriacao <= :umMesAtras")
  Integer countClienteBetween2DatesByDataCriacao(@PathParam("umMesAtras") LocalDate doisMesesAtras,
                                                 @PathParam("doisMesesAtras") LocalDate umMesAtras);

  @Query("SELECT COUNT(*) FROM Cliente")
  Integer countTotalClientesByDataCriacao();
}
