import java.util.ArrayList;
import java.util.List;

public class CadastroCliente {
    private List<ClientePessoaFisica> clientes = new ArrayList<>();

    public void cadastrarCliente(ClientePessoaFisica cliente) {
        clientes.add(cliente);
    }
    public List<ClientePessoaFisica> listarClientes() {
        return clientes;
    }

}



