public class ContaPoupanca extends Conta {
    public ContaPoupanca(ClientePessoaFisica cliente, String agencia, double saldo) {
        super(cliente, agencia);
        this.saldo = saldo;
    }
}
