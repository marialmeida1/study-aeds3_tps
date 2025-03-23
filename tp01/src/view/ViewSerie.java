package tp01.src.view;

import tp01.src.models.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ViewSerie {
    private static final Scanner console = new Scanner(System.in);

    public void exibirMenu() {
        System.out.println("\n\nPUCFlix 1.0");
        System.out.println("-------");
        System.out.println("> Início > Séries");
        System.out.println("\n1 - Incluir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Alterar");
        System.out.println("4 - Excluir");
        System.out.println("0 - Retornar ao menu anterior");
        System.out.print("\nOpção: ");
    }

    public String lerCpf() {
        String cpf;
        while (true) {
            System.out.print("\nCPF (11 dígitos): ");
            cpf = console.nextLine();
            if (cpf.length() == 11 && cpf.matches("\\d{11}")) {
                return cpf;
            } else {
                System.out.println("CPF inválido! O CPF deve ter exatamente 11 dígitos numéricos.");
            }
        }
    }

    public void mostraSerie(Cliente cliente) {
        if (cliente != null) {
            System.out.println("\nDetalhes do Cliente:");
            System.out.println("----------------------");
            System.out.printf("Nome......: %s%n", cliente.nome);
            System.out.printf("CPF.......: %s%n", cliente.cpf);
            System.out.printf("Salário...: R$ %.2f%n", cliente.salario);
            System.out.printf("Ano de Lançamento: %s%n",
                    cliente.nascimento.format(DateTimeFormatter.ofPattern("yyyy")));
            System.out.println("----------------------");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public String obterNome() {
        String nome;
        while (true) {
            System.out.print("\nNome (min. de 4 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if (nome.isEmpty()) {
                return null; // Cancela a operação
            }
            if (nome.length() >= 4) {
                return nome;
            } else {
                System.out.println("Nome inválido! O nome deve ter pelo menos 4 letras.");
            }
        }
    }

    public String obterCpf() {
        String cpf;
        while (true) {
            System.out.print("CPF (11 dígitos sem pontos ou traço): ");
            cpf = console.nextLine(); // Lê o CPF digitado pelo usuário

            if (cpf.isEmpty()) {
                System.out.println("CPF não pode estar vazio. Tente novamente.");
                continue; // Se o CPF estiver vazio, pede para o usuário tentar novamente
            }

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (cpf.length() == 11 && cpf.matches("\\d{11}")) {
                return cpf; // Retorna o CPF válido
            } else {
                System.out.println(
                        "CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos, sem pontos e traços.");
            }
        }
    }

    public float obterSalario() {
        float salario;
        while (true) {
            System.out.print("Salário: ");
            if (console.hasNextFloat()) {
                salario = console.nextFloat();
                if (salario > 0) {
                    console.nextLine(); // Limpa o buffer de entrada
                    return salario;
                } else {
                    System.out.println("Salário inválido! O valor deve ser maior que zero.");
                }
            } else {
                System.out.println("Entrada inválida! Digite um valor numérico para o salário.");
                console.nextLine(); // Limpa o buffer de entrada
            }
        }
    }

    public LocalDate obterDataNascimento() {
        LocalDate dataNascimento;
        while (true) {
            System.out.print("Data de nascimento (DD/MM/AAAA): ");
            String data = console.nextLine();
            try {
                dataNascimento = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return dataNascimento;
            } catch (Exception e) {
                System.out.println("Data inválida! O formato correto é DD/MM/AAAA.");
            }
        }
    }

    public boolean confirmarAlteracoes() {
        String resposta;
        // Loop até uma resposta válida ser fornecida
        while (true) {
            System.out.print("\nConfirma as alterações? (S/N) ");
            resposta = console.nextLine().trim(); // Remove espaços extras

            // Verifica se a resposta é válida
            if (resposta.equalsIgnoreCase("S")) {
                return true;
            } else if (resposta.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Resposta inválida! Por favor, digite 'S' para sim ou 'N' para não.");
            }
        }
    }

    public boolean confirmarExclusao() {
        String resposta;
        // Loop até uma resposta válida ser fornecida
        while (true) {
            System.out.print("\nConfirma a exclusão do cliente? (S/N) ");
            resposta = console.nextLine().trim(); // Remove espaços extras

            // Verifica se a resposta é válida
            if (resposta.equalsIgnoreCase("S")) {
                return true;
            } else if (resposta.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Resposta inválida! Por favor, digite 'S' para sim ou 'N' para não.");
            }
        }
    }
}
