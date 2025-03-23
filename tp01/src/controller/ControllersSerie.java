package tp01.src.controller;

import tp01.src.models.Cliente;
import tp01.src.data.ArquivoCliente;
import tp01.src.view.ViewSerie;

import java.time.LocalDate;
import java.util.Scanner;

public class ControllerSerie {

    private ArquivoCliente arqClientes;
    private ViewSerie visao;
    private static final Scanner console = new Scanner(System.in);

    public ControllerSerie() throws Exception {
        arqClientes = new ArquivoCliente();
        visao = new ViewSerie();
    }

    public void menu() throws Exception {
        int opcao;
        do {
            visao.exibirMenu();
            opcao = Integer.valueOf(console.nextLine());

            switch (opcao) {
                case 1:
                    incluirSerie();
                    break;
                case 2:
                    buscarSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    private void buscarSerie() {
        String cpf = visao.lerCpf();
        try {
            Cliente cliente = arqClientes.read(cpf);
            if (cliente != null) {
                visao.mostraSerie(cliente);
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar o cliente!");
            e.printStackTrace();
        }
    }

    private void incluirSerie() {
        String nome = visao.obterNome();
        if (nome.isEmpty()) return;

        String cpf = visao.obterCpf();
        float salario = visao.obterSalario();
        LocalDate dataNascimento = visao.obterDataNascimento();

        System.out.print("\nConfirma a inclusão do cliente? (S/N) ");
        if (visao.confirmarAlteracoes()) {
            try {
                Cliente cliente = new Cliente(nome, cpf, salario, dataNascimento);
                arqClientes.create(cliente);
                System.out.println("Cliente incluído com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao incluir o cliente!");
            }
        }
    }

    private void alterarSerie() throws Exception {
        String cpf = visao.lerCpf();
        Cliente cliente = arqClientes.read(cpf);
        if (cliente != null) {
            visao.mostraSerie(cliente);

            String novoNome = visao.obterNome();
            if (!novoNome.isEmpty()) cliente.nome = novoNome;

            String novoCpf = visao.obterCpf();
            if (!novoCpf.isEmpty()) cliente.cpf = novoCpf;

            float novoSalario = visao.obterSalario();
            cliente.salario = novoSalario;

            LocalDate novaDataNascimento = visao.obterDataNascimento();
            cliente.nascimento = novaDataNascimento;

            if (visao.confirmarAlteracoes()) {
                boolean alterado = arqClientes.update(cliente);
                if (alterado) {
                    System.out.println("Cliente alterado com sucesso.");
                } else {
                    System.out.println("Erro ao alterar o cliente.");
                }
            } else {
                System.out.println("Alterações canceladas.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void excluirSerie() throws Exception {
        String cpf = visao.lerCpf();
        Cliente cliente = arqClientes.read(cpf);
        if (cliente != null) {
            visao.mostraSerie(cliente);
            if (visao.confirmarExclusao()) {
                boolean excluido = arqClientes.delete(cpf);
                if (excluido) {
                    System.out.println("Cliente excluído com sucesso.");
                } else {
                    System.out.println("Erro ao excluir o cliente.");
                }
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
}
