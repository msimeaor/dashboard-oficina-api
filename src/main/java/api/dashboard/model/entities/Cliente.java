package api.dashboard.model.entities;

import api.dashboard.enums.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "primeiro_nome", length = 50)
  private String primeiroNome;

  @Column(name = "sobrenome", length = 100)
  private String sobrenome;

  @Column(name = "cpf", length = 11, unique = true)
  private String cpf;

  @Column(name = "data_nascimento")
  private LocalDate dataNascimento;

  @Enumerated(EnumType.STRING)
  @Column(name = "genero")
  private Genero genero;

  @Column(name = "email", length = 100, unique = true)
  private String email;

  @OneToOne
  private Telefone telefone;

  // TODO Criar relacionamento com classe Venda
  // TODO Criar relacionamento com classe Veiculo

}
