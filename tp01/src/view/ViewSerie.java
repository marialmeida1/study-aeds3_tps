package tp01.src.view;

import tp01.src.models.Serie;

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
        System.out.println("5 - Listas Episódios da Série");
        System.out.println("0 - Retornar ao menu anterior");
        System.out.print("\nOpção: ");
    }

    public void mostraSerie(Serie serie) {
        if (serie != null) {
            System.out.println("\nDetalhes da Série:");
            System.out.println("----------------------");
            System.out.printf("Nome.................: %s%n", serie.nome);
            System.out.printf("Sinopse..............: %s%n", serie.sinopse);
            System.out.printf("Episódios............: %d%n", serie.episodes);
            System.out.printf("Ano de Lançamento....: %d%n", serie.releaseYear);
            System.out.printf("Streaming............: %s%n", serie.streaming);
            System.out.println("----------------------");
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    public String obterNome() {
        System.out.print("\nNome da Série: ");
        return console.nextLine();
    }

    public String obterSinopse() {
        System.out.print("Sinopse: ");
        return console.nextLine();
    }

    public int obterQuantidadeEpisodios() {
        System.out.print("Quantidade de Episódios: ");
        return Integer.parseInt(console.nextLine());
    }

    public short obterAnoLancamento() {
        System.out.print("Ano de Lançamento: ");
        return Short.parseShort(console.nextLine());
    }

    public String obterStreaming() {
        System.out.print("Streaming: ");
        return console.nextLine();
    }

    public boolean confirmarAlteracoes() {
        System.out.print("\nConfirma as alterações? (S/N) ");
        String resposta = console.nextLine().trim();
        return resposta.equalsIgnoreCase("S");
    }

    public boolean confirmarExclusao() {
        System.out.print("\nConfirma a exclusão da série? (S/N) ");
        String resposta = console.nextLine().trim();
        return resposta.equalsIgnoreCase("S");
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
