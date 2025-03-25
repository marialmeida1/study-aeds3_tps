package tp01.src.data;

import tp01.src.models.Cliente;
import tp01.src.storage.*;
import tp01.src.util.*;

public class ArquivoCliente extends Arquivo<Cliente> {

    Arquivo<Cliente> arqClientes;
    HashExtensivel<ParCPFID> indiceIndiretoCPF;

    public ArquivoCliente() throws Exception {
        // Chama o construtor da classe pai (Arquivo) para inicializar com "clientes" e
        // o construtor de Cliente
        super("clientes", Cliente.class.getConstructor());

        // Criação do índice indireto com HashExtensível, ajustando os caminhos para a
        // pasta tp01/files
        indiceIndiretoCPF = new HashExtensivel<>(
                ParCPFID.class.getConstructor(),
                4,
                "tp01/files/clientes/indiceCPF.d.db", // Diretório para o índice de CPF
                "tp01/files/clientes/indiceCPF.c.db" // Cestos para o índice de CPF
        );
    }

    @Override
    public int create(Cliente c) throws Exception {
        int id = super.create(c);
        indiceIndiretoCPF.create(new ParCPFID(c.getCpf(), id));
        return id;
    }

    public Cliente read(String cpf) throws Exception {
        ParCPFID pci = indiceIndiretoCPF.read(ParCPFID.hash(cpf));
        if (pci == null)
            return null;
        return read(pci.getId());
    }

    public boolean delete(String cpf) throws Exception {
        ParCPFID pci = indiceIndiretoCPF.read(ParCPFID.hash(cpf));
        if (pci != null)
            if (delete(pci.getId()))
                return indiceIndiretoCPF.delete(ParCPFID.hash(cpf));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Cliente c = super.read(id);
        if (c != null) {
            if (super.delete(id))
                return indiceIndiretoCPF.delete(ParCPFID.hash(c.getCpf()));
        }
        return false;
    }

    @Override
    public boolean update(Cliente novoCliente) throws Exception {
        Cliente clienteVelho = read(novoCliente.getCpf());
        if (super.update(novoCliente)) {
            if (novoCliente.getCpf().compareTo(clienteVelho.getCpf()) != 0) {
                indiceIndiretoCPF.delete(ParCPFID.hash(clienteVelho.getCpf()));
                indiceIndiretoCPF.create(new ParCPFID(novoCliente.getCpf(), novoCliente.getId()));
            }
            return true;
        }
        return false;
    }
}
