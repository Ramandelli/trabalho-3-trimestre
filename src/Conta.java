import java.util.Random;

public abstract class Conta {
    private ClientePessoaFisica cliente;
    private String agencia;
    private String numeroConta;
    private int senha;
    protected double saldo;

    public Conta(ClientePessoaFisica cliente, String agencia) {
        this.cliente = cliente;
        this.agencia = agencia;
        this.numeroConta = gerarNumeroConta();
        this.senha = gerarSenha();
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public int getSenha() {
        return senha;
    }

    private String gerarNumeroConta() {
        Random random = new Random();
        int numero = random.nextInt(900000) + 100000; // Gera número de 6 dígitos
        return Integer.toString(numero);
    }

    private int gerarSenha() {
        Random random = new Random();
        return random.nextInt(9000) + 1000; // Gera senha de 4 dígitos
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de depósito inválido. O valor deve ser maior que zero.");
        } else {
            saldo += valor;
            System.out.println("Depósito de R$" + valor + " realizado com sucesso.");
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$" + valor + " realizado com sucesso.");
            return true;
        }
        System.out.println("Saldo insuficiente para realizar o saque.");
        return false;
    }


    public boolean transferir(Conta destino, double valor) {
        if (valor <= 0) {
            System.out.println("Valor de transferência inválido. O valor deve ser maior que zero.");
            return false;
        } else if (saldo >= valor) {
            saldo -= valor;
            destino.depositar(valor);
            System.out.println("Transferência de R$" + valor + " realizada com sucesso.");
            return true;
        } else {
            System.out.println("Saldo insuficiente para realizar a transferência.");
            return false;
        }
    }
}

