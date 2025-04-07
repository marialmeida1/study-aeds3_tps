package tp01.src.view;

import tp01.src.models.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ViewEpisode {
    private static final Scanner console = new Scanner(System.in);

    public void exibirMenu() {
        System.out.println("\n===============================");
        System.out.println("          PUCFlix 1.0");
        System.out.println("===============================");
        System.out.println("> Início > Episódio");
        System.out.println("-------------------------------");
        System.out.println("1 - Incluir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Alterar");
        System.out.println("4 - Excluir");
        System.out.println("0 - Retornar ao menu anterior");
        System.out.println("===============================");
        System.out.print("Opção: ");
    }

    public void mostraEpisodio(Episode episodio) {
        if (episodio != null) {
            System.out.println("\n\n===============================");
            System.out.println("      Detalhes do episodio:");
            System.out.println("===============================");
            System.out.printf("Nome:.............. %s%n", episodio.getName());
            System.out.printf("Temporada:......... %d%n", episodio.getSeason());
            System.out.printf("Data Lançamento:... %s%n",
                    episodio.getRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("Duração:........... %d%n minutos", episodio.getDuration());
            System.out.println("\n===============================");
        } else {
            System.out.println("Episódio não encontrado.");
        }
    }

    public String obterNome() {
        String nome;
        while (true) {
            System.out.print("Nome do Episódio: ");
            nome = console.nextLine();
            if (nome.isEmpty()) {
                return null; // Cancela a operação
            }
            if (nome.length() >= 0) {
                return nome;
            }
        }
    }

    public String obterNomeSerie() {
        String nome;
        while (true) {
            System.out.print("Nome da Série: ");
            nome = console.nextLine();
            if (nome.isEmpty()) {
                return null; // Cancela a operação
            }
            if (nome.length() >= 0) {
                return nome;
            }
        }
    }

    public int obterTemporada() {
        int temporada;
        while (true) {
            System.out.print("Temporada: ");
            String input = console.nextLine();
            if (input.isEmpty()) {
                return -1; // Retorna -1 para indicar que o usuário não quer alterar
            }
            try {
                temporada = Integer.parseInt(input);
                if (temporada > 0) {
                    return temporada;
                } else {
                    System.out.println("Temporada inválida! O valor deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um valor numérico para a temporada.");
            }
        }
    }

    public LocalDate obterDataLancamento() {
        while (true) {
            System.out.print("Data de lançamento (DD/MM/AAAA): ");
            String data = console.nextLine();
            if (data.isEmpty()) {
                return null; // Retorna null para indicar que o usuário não quer alterar
            }
            try {
                return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception e) {
                System.out.println("Data inválida! O formato correto é DD/MM/AAAA.");
            }
        }
    }

    public int obterDuracao() {
        int duracao;
        while (true) {
            System.out.print("Duração: ");
            String input = console.nextLine();
            if (input.isEmpty()) {
                return -1; // Retorna -1 para indicar que o usuário não quer alterar
            }
            try {
                duracao = Integer.parseInt(input);
                if (duracao > 0) {
                    return duracao;
                } else {
                    System.out.println("Duração inválida! O valor deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um valor numérico para a duração.");
            }
        }
    }

    public boolean confirmAction(int actionNum) {
        String mensagem;

        switch (actionNum) {
            case 1:
                System.out.println("-------------------------------");
                mensagem = "Confirma a inclusão? (S/N) ";
                break;
            case 2:
                System.out.println("-------------------------------");
                mensagem = "Confirma as alterações? (S/N) ";
                break;
            case 3:
                System.out.println("-------------------------------");
                mensagem = "Confirma a exclusão do episódio? (S/N) "; // Corrigido para "série"
                break;
            default:
                System.out.println("-------------------------------");
                mensagem = "Confirma a ação? (S/N) ";
                break;
        }

        while (true) {
            System.out.print(mensagem);
            String resposta = console.nextLine().trim().toUpperCase();

            if (resposta.equals("S")) {
                return true;
            } else if (resposta.equals("N")) {
                return false;
            } else {
                System.out.print("Resposta inválida! Digite 'S' para Sim ou 'N' para Não: ");
            }
        }
    }

}
