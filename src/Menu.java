import java.util.List;
import java.util.Scanner;

public class Menu {
    private final CadastroCliente cadastroCliente = new CadastroCliente();
    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        int escolha;

        do {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar Cliente Pessoa Física");
            System.out.println("2. Cadastrar Conta Corrente");
            System.out.println("3. Cadastrar Conta Poupança");
            System.out.println("4. Listar Clientes");
            System.out.println("5. Realizar Depósito");
            System.out.println("6. Realizar Saque");
            System.out.println("7. Realizar Transferência");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após a leitura do número

            switch (escolha) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarContaCorrente();
                    break;
                case 3:
                    cadastrarContaPoupanca();
                    break;
                case 4:
                    listarClientes();
                    break;
                case 5:
                    realizarOperacao("depósito");
                    break;
                case 6:
                    realizarOperacao("saque");
                    break;
                case 7:
                    realizarTransferencia();
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (escolha != 8);
    }

    private void cadastrarCliente() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        String cpf;

        while (true) {
            System.out.print("Digite o CPF do cliente: ");
            cpf = scanner.nextLine();

            if (validarCpf(cpf)) {
                break;
            } else {
                System.out.println("CPF inválido. Digite novamente.");
            }
        }

        ClientePessoaFisica cliente = new ClientePessoaFisica(nome, cpf);
        cadastroCliente.cadastrarCliente(cliente);

        // Adicione o cliente à lista de clientes
        ClientePessoaFisica.adicionarCliente(cliente);

        System.out.println("Cliente cadastrado com sucesso.");
    }

    private boolean validarCpf(String cpf) {
        // Remover caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verificar se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verificar se todos os dígitos são iguais (caso de CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Cálculo para verificar se os dígitos verificadores estão corretos
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador >= 10) {
            primeiroDigitoVerificador = 0;
        }

        if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigitoVerificador) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador >= 10) {
            segundoDigitoVerificador = 0;
        }

        return Character.getNumericValue(cpf.charAt(10)) == segundoDigitoVerificador;
    }


    private void cadastrarContaCorrente() {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        ClientePessoaFisica clienteEncontrado = ClientePessoaFisica.encontrarClientePorCpf(cpf);

        if (clienteEncontrado != null) {
            System.out.print("Digite o código da agência: ");
            String agencia = scanner.nextLine();
            double saldo = 0;

            ContaCorrente contaCorrente = new ContaCorrente(clienteEncontrado, agencia, saldo);
            clienteEncontrado.vincularContaCorrente(contaCorrente);

            System.out.println("Conta corrente cadastrada com sucesso.");
        } else {
            System.out.println("Cliente não encontrado. Certifique-se de que o cliente está cadastrado com o CPF correto.");
        }
    }

    private void cadastrarContaPoupanca() {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        ClientePessoaFisica clienteEncontrado = ClientePessoaFisica.encontrarClientePorCpf(cpf);

        if (clienteEncontrado != null) {
            System.out.print("Digite o código da agência: ");
            String agencia = scanner.nextLine();
            double saldo = 0;

            ContaPoupanca contaPoupanca = new ContaPoupanca(clienteEncontrado, agencia, saldo);
            clienteEncontrado.vincularContaPoupanca(contaPoupanca);

            System.out.println("Conta poupança cadastrada com sucesso.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void realizarOperacao(String operacao) {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        ClientePessoaFisica clienteEncontrado = ClientePessoaFisica.encontrarClientePorCpf(cpf);

        if (clienteEncontrado != null) {
            System.out.println("Selecione o tipo de conta (1 - Conta Corrente, 2 - Conta Poupança): ");
            System.out.print("Escolha: ");

            int escolhaConta = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            Conta contaSelecionada = null;

            if (escolhaConta == 1 && clienteEncontrado.getContaCorrente() != null) {
                contaSelecionada = clienteEncontrado.getContaCorrente();
            } else if (escolhaConta == 2 && clienteEncontrado.getContaPoupanca() != null) {
                contaSelecionada = clienteEncontrado.getContaPoupanca();
            }

            if (contaSelecionada != null) {
                System.out.print("Digite a agência: ");
                String agencia = scanner.nextLine();
                System.out.print("Digite o número da conta: ");
                String numeroConta = scanner.nextLine();

                double valorOperacao;
                do {
                    System.out.print("Digite o valor para " + operacao + ": ");
                    valorOperacao = scanner.nextDouble();
                } while (valorOperacao <= 0);

                System.out.print("Digite a senha da conta: ");
                int senha = scanner.nextInt();

                if (agencia.equals(contaSelecionada.getAgencia()) &&
                        numeroConta.equals(contaSelecionada.getNumeroConta()) &&
                        senha == contaSelecionada.getSenha()) {
                    if (operacao.equals("depósito")) {
                        contaSelecionada.depositar(valorOperacao);
                    } else if (operacao.equals("saque")) {
                        contaSelecionada.sacar(valorOperacao);
                    }
                } else {
                    System.out.println(operacao.substring(0, 1).toUpperCase() + operacao.substring(1) +
                            " não realizado. Verifique os dados informados.");
                }
            } else {
                System.out.println("Cliente não possui a conta selecionada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void realizarTransferencia() {
        System.out.print("Digite o CPF do cliente da conta de origem: ");
        String cpfOrigem = scanner.nextLine();

        ClientePessoaFisica clienteOrigem = ClientePessoaFisica.encontrarClientePorCpf(cpfOrigem);

        if (clienteOrigem != null) {
            System.out.println("Selecione o tipo de conta da origem (1 - Conta Corrente, 2 - Conta Poupança): ");
            int tipoContaOrigem = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            Conta contaOrigem = null;

            if (tipoContaOrigem == 1 && clienteOrigem.getContaCorrente() != null) {
                contaOrigem = clienteOrigem.getContaCorrente();
            } else if (tipoContaOrigem == 2 && clienteOrigem.getContaPoupanca() != null) {
                contaOrigem = clienteOrigem.getContaPoupanca();
            }

            if (contaOrigem != null) {
                System.out.print("Digite a agência da origem: ");
                String agenciaOrigem = scanner.nextLine();
                System.out.print("Digite o número da conta da origem: ");
                String numeroContaOrigem = scanner.nextLine();

                System.out.print("Digite o CPF do cliente da conta de destino: ");
                String cpfDestino = scanner.nextLine();

                ClientePessoaFisica clienteDestino = ClientePessoaFisica.encontrarClientePorCpf(cpfDestino);

                if (clienteDestino != null) {
                    System.out.println("Selecione o tipo de conta de destino (1 - Conta Corrente, 2 - Conta Poupança): ");
                    int tipoContaDestino = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    Conta contaDestino = null;

                    if (tipoContaDestino == 1 && clienteDestino.getContaCorrente() != null) {
                        contaDestino = clienteDestino.getContaCorrente();
                    } else if (tipoContaDestino == 2 && clienteDestino.getContaPoupanca() != null) {
                        contaDestino = clienteDestino.getContaPoupanca();
                    }

                    if (contaDestino != null) {
                        System.out.print("Digite a agência de destino: ");
                        String agenciaDestino = scanner.nextLine();
                        System.out.print("Digite o número da conta de destino: ");
                        String numeroContaDestino = scanner.nextLine();

                        double valorTransferencia;
                        do {
                            System.out.print("Digite o valor da transferência: ");
                            valorTransferencia = scanner.nextDouble();
                        } while (valorTransferencia <= 0);

                        System.out.print("Digite a senha da conta de origem: ");
                        int senha = scanner.nextInt();

                        if (agenciaOrigem.equals(contaOrigem.getAgencia()) &&
                                numeroContaOrigem.equals(contaOrigem.getNumeroConta()) &&
                                senha == contaOrigem.getSenha()) {
                            contaOrigem.transferir(contaDestino, valorTransferencia);
                        } else {
                            System.out.println("Conta de origem não encontrada ou inválida.");
                        }
                    } else {
                        System.out.println("Cliente de destino não possui a conta selecionada.");
                    }
                } else {
                    System.out.println("Cliente de destino não encontrado.");
                }
            } else {
                System.out.println("Cliente de origem não possui a conta selecionada.");
            }
        } else {
            System.out.println("Cliente de origem não encontrado.");
        }
    }




    private void listarClientes() {
        List<ClientePessoaFisica> clientes = cadastroCliente.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no momento.");
        } else {
            for (ClientePessoaFisica cliente : clientes) {
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("CPF: " + cliente.getCpf());

                if (cliente.getContaCorrente() != null) {
                    System.out.println("Conta Corrente: Agência - " + cliente.getContaCorrente().getAgencia() +
                            ", Número - " + cliente.getContaCorrente().getNumeroConta() +
                            ", Senha - " + cliente.getContaCorrente().getSenha() +
                            ", Saldo - " + cliente.getContaCorrente().getSaldo());
                }

                if (cliente.getContaPoupanca() != null) {
                    System.out.println("Conta Poupança: Agência - " + cliente.getContaPoupanca().getAgencia() +
                            ", Número - " + cliente.getContaPoupanca().getNumeroConta() +
                            ", Senha - " + cliente.getContaPoupanca().getSenha() +
                            ", Saldo - " + cliente.getContaPoupanca().getSaldo());
                }

                if (cliente.getContaCorrente() == null && cliente.getContaPoupanca() == null) {
                    System.out.println("Cliente não possui contas cadastradas.");
                }

                System.out.println("--------------------");
            }
        }
    }


    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}
