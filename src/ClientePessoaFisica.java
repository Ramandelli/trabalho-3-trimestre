import java.util.ArrayList;
import java.util.List;

public class ClientePessoaFisica implements Cliente {
    private static List<ClientePessoaFisica> listaClientes = new ArrayList<>();

    private String nome;
    private String cpf;
    private ContaCorrente contaCorrente;
    private ContaPoupanca contaPoupanca;

    public ClientePessoaFisica(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public ContaPoupanca getContaPoupanca() {
        return contaPoupanca;
    }

    public void vincularContaCorrente(ContaCorrente conta) {
        this.contaCorrente = conta;
    }

    public void vincularContaPoupanca(ContaPoupanca conta) {
        this.contaPoupanca = conta;
    }

    public static ClientePessoaFisica encontrarClientePorCpf(String cpf) {
        for (ClientePessoaFisica cliente : listaClientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public static void adicionarCliente(ClientePessoaFisica cliente) {
        listaClientes.add(cliente);
    }
}
