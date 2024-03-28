package api.dashboard.model.entities;

import api.dashboard.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Venda {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "total", precision = 7, scale = 2)
  private BigDecimal total;

  @Enumerated(EnumType.STRING)
  @Column(name = "forma_pagamento")
  private FormaPagamento formaPagamento;

  @Column(name = "quantidade_parcelas")
  private int qtdParcelas;

  @Column(name = "data")
  private LocalDate data;

  @ManyToOne
  private Cliente cliente;

  @OneToOne
  private Anotacao anotacao;

  @ManyToMany
  @JoinTable(name = "venda_servico",
          joinColumns = @JoinColumn(name = "venda_id"),
          inverseJoinColumns = @JoinColumn(name = "servico_id")
  )
  private Set<Servico> servicos;
}
