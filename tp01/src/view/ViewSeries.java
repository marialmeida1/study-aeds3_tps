package tp01.src.view;

import tp01.src.models.Serie;

import java.util.Scanner;

public class ViewSerie {
    private static final Scanner console = new Scanner(System.in);

    public void exibirMenu() {
        System.out.println("\n\n===============================");
        System.out.println("          PUCFlix 1.0");
        System.out.println("===============================");
        System.out.println("> Início > Séries");
        System.out.println("-------------------------------");
        System.out.println("1 - Incluir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Alterar");
        System.out.println("4 - Excluir");
        System.out.println("5 - Listas Episódios da Série");
        System.out.println("0 - Retornar ao menu anterior");
        System.out.println("===============================");
        System.out.print("Opção: ");
    }

    public void mostraSerie(Serie serie) {
        if (serie != null) {
            System.out.println("\n\n===============================");
            System.out.println("       Detalhes da Série:");
            System.out.println("===============================");
            System.out.printf("Nome.................: %s%n", serie.getName());
            System.out.printf("Sinopse..............: %s%n", serie.getSynopsis());
            System.out.printf("Episódios............: %d%n", serie.getEpisodes());
            System.out.printf("Ano de Lançamento....: %d%n", serie.getReleaseYear());
            System.out.printf("Streaming............: %s%n", serie.getStreaming());
            System.out.println("===============================");
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    public String obterNome() {
        System.out.print("Nome da Série: ");
        return console.nextLine();
    }

    public String obterSinopse() {
        System.out.print("Sinopse: ");
        return console.nextLine();
    }

    public int obterQuantidadeEpisodios() {
        System.out.print("Quantidade de Episódios: ");
        String entrada = console.nextLine();

        if (entrada.isEmpty()) {
            return -1; // Retorna um valor que indica que o usuário não digitou nada
        }

        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Digite um número válido.");
            return obterQuantidadeEpisodios(); // Pede a entrada novamente em caso de erro
        }
    }

    public short obterAnoLancamento() {
        System.out.print("Ano de Lançamento: ");
        String entrada = console.nextLine();

        if (entrada.isEmpty()) {
            return -1; // Retorna um valor indicando que o usuário não digitou nada
        }

        try {
            return Short.parseShort(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Digite um ano válido.");
            return obterAnoLancamento(); // Pede a entrada novamente em caso de erro
        }
    }

    public String obterStreaming() {
        System.out.print("Streaming: ");
        return console.nextLine();
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
                mensagem = "Confirma a exclusão da série? (S/N) "; // Corrigido para "série"
                break;
            default:
                System.out.println("-------------------------------");
                mensagem = "Confirma a ação? (S/N) ";
                break;
        }

        while (true) {
            System.out.print(mensagem);
            String resposta = console.nextLine().trim().toUpperCase();

            if (resposta.equalsIgnoreCase("S")) {
                return true;
            } else if (resposta.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Resposta inválida! Por favor, digite 'S' para Sim ou 'N' para Não.");
            }
        }
    }

    public int lerSerie() {
        int idSerie;
        while (true) {
            System.out.print("ID do Série: ");
            idSerie = console.nextInt();
            if (idSerie > 0) {
                console.nextLine();
                return idSerie;
            } else {
                System.out.println("ID do Série inválido! O valor deve ser maior que zero.");
            }
        }
    }
}
