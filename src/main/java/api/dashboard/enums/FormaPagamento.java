package api.dashboard.enums;

public enum FormaPagamento {

  DINHEIRO("DINHEIRO"),
  PIX("PIX"),
  CREDITO("CREDITO"),
  DEBITO("DEBITO"),
  CHEQUE("CHEQUE");

  private String formaPagamento;

  FormaPagamento(String formaPagamento) {
    this.formaPagamento = formaPagamento;
  }

  public String getFormaPagamento() {
    return this.formaPagamento;
  }

  public FormaPagamento getFormaPagamentoByString(String forma) {
    for (FormaPagamento fp : FormaPagamento.values()) {
      if (fp.getFormaPagamento().equalsIgnoreCase(forma)) return fp;
    }

    throw new IllegalArgumentException("Forma de pagamento não encontrada!");
  }

}
