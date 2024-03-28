package api.dashboard.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Servico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "nome", length = 100, unique = true)
  private String nome;

  @Column(name = "valor_unitario", precision = 7, scale = 2)
  private BigDecimal valorUnitario;

  @ManyToMany(mappedBy = "servicos")
  private Set<Venda> vendas;
}
