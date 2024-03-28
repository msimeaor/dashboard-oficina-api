package api.dashboard.model.entities;

import api.dashboard.enums.Fabricante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Veiculo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "nome", length = 100)
  private String nome;

  @Enumerated(EnumType.STRING)
  @Column(name = "fabricante")
  private Fabricante fabricante;

  @Column(name = "ano_fabricacao", length = 4)
  private String anoFabricacao;

  @Column(name = "placa", length = 7, unique = true)
  private String placa;

  @Column(name = "quilometragem", length = 6)
  private String quilometragem;

  @Column(name = "litragem_motor", length = 3)
  private String litragemMotor;

  @ManyToMany(mappedBy = "veiculos")
  private Set<Cliente> clientes;

  @OneToOne
  private Anotacao anotacao;

}
