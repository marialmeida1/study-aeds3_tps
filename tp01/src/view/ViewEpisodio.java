package tp01.src.view;

import tp01.src.models.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ViewEpisodio {
    private static final Scanner console = new Scanner(System.in);

    public void exibirMenu() {
        System.out.println("\n\nPUCFlix 1.0");
        System.out.println("-------");
        System.out.println("> Início > Episódio");
        System.out.println("\n1 - Incluir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Alterar");
        System.out.println("4 - Excluir");
        System.out.println("0 - Retornar ao menu anterior");
        System.out.print("\nOpção: ");
    }

    public int lerSerie() {
        int fkSerie;
        while (true) {
            System.out.print("\nID da Série: ");
            fkSerie = console.nextInt();
            if (fkSerie > 0) {
                console.nextLine();
                return fkSerie;
            } else {
                System.out.println("ID da Série inválido! O valor deve ser maior que zero.");
            }
        }
    }

    public int lerEpisodio() {
        int idEpisodio;
        while (true) {
            System.out.print("\nID do Episódio: ");
            idEpisodio = console.nextInt();
            if (idEpisodio > 0) {
                console.nextLine();
                return idEpisodio;
            } else {
                System.out.println("ID do Episódio inválido! O valor deve ser maior que zero.");
            }
        }
    }

    public void mostraEpisodio(Episodio episodio) {
        if (episodio != null) {
            System.out.println("\nDetalhes do episodio:");
            System.out.println("----------------------");
            System.out.printf("Nome:................ %s%n", episodio.nome);
            System.out.printf("Temporada:........... %d%n", episodio.temporada);
            System.out.printf("Data Lançamento:..... %s%n",
                    episodio.lancamento.format(DateTimeFormatter.ofPattern("yyyy")));
            System.out.printf("Duração:............. %d%n", episodio.duracao);
            System.out.println("----------------------");
        } else {
            System.out.println("Episódio não encontrado.");
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

    public int obterTemporada() {
        int temporada;
        while (true) {
            System.out.print("Temporada: ");
            if (console.hasNextInt()) {
                temporada = console.nextInt();
                if (temporada > 0) {
                    console.nextLine();
                    return temporada;
                } else {
                    System.out.println("Temporada inválida! O valor deve ser maior que zero.");
                }
            } else {
                System.out.println("Entrada inválida! Digite um valor numérico para a temporada.");
                console.nextLine();
            }
        }
    }

    public LocalDate obterDataLancamento() {
        LocalDate dataLancamento;
        while (true) {
            System.out.print("Data de lançamento (DD/MM/AAAA): ");
            String data = console.nextLine();
            try {
                dataLancamento = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return dataLancamento;
            } catch (Exception e) {
                System.out.println("Data inválida! O formato correto é DD/MM/AAAA.");
            }
        }
    }


    public int obterDuracao() {
        int duracao;
        while (true) {
            System.out.print("Duração: ");
            if (console.hasNextInt()) {
                duracao = console.nextInt();
                if (duracao > 0) {
                    console.nextLine();
                    return duracao;
                } else {
                    System.out.println("Duração inválida! O valor deve ser maior que zero.");
                }
            } else {
                System.out.println("Entrada inválida! Digite um valor numérico para a duração.");
                console.nextLine();
            }
        }
    }

    public boolean confirmarAlteracoes() {
        String resposta;
        while (true) {
            System.out.print("\nConfirma as alterações? (S/N) ");
            resposta = console.nextLine().trim();

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
        while (true) {
            System.out.print("\nConfirma a exclusão do episódio? (S/N) ");
            resposta = console.nextLine().trim();

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
