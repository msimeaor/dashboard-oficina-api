package api.dashboard.model.repositories;

import api.dashboard.model.entities.Veiculo;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

  @Query("SELECT COUNT(v) FROM Veiculo v " +
          "WHERE v.dataCriacao >= :umMesAtras")
  Integer countVeiculosLast30DaysByDataCriacao(@PathParam("umMesAtras") LocalDate umMesAtras);

  @Query("SELECT COUNT(*) FROM Veiculo")
  Integer countTotalVeiculosByDataCriacao();

}
