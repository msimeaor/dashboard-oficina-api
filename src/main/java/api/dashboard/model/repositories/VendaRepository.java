package api.dashboard.model.repositories;

import api.dashboard.model.entities.Venda;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

  @Query("SELECT COUNT(v) FROM Venda v " +
          "WHERE v.dataCriacao >= :umMesAtras")
  Integer countVendasLast30DaysByDataCriacao(@PathParam("umMesAtras") LocalDate umMesAtras);

  @Query("SELECT COUNT(*) FROM Venda")
  Integer countTotalVendasByDataCriacao();

}
