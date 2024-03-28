package api.dashboard.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Anotacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "titulo", length = 50)
  private String titulo;

  @Column(name = "descricao")
  private String descricao;

  @OneToOne(mappedBy = "anotacao", cascade = CascadeType.ALL)
  private Veiculo veiculo;

  // TODO Criar relacionamento com classe Venda

}
