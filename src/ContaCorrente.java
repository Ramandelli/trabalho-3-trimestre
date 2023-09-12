public class ContaCorrente extends Conta {
    public ContaCorrente(ClientePessoaFisica cliente, String agencia, double saldo) {
        super(cliente, agencia);
        this.saldo = saldo;
    }

}
