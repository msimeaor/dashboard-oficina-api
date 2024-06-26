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
public class Telefone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "ddd", length = 3)
  private String ddd;

  @Column(name = "numero", length = 9, unique = true)
  private String numero;

  @OneToOne(mappedBy = "telefone", cascade = CascadeType.ALL)
  private Cliente cliente;

}
