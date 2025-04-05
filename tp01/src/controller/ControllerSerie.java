package tp01.src.controller;

import tp01.src.models.Serie;
import tp01.src.data.ArquivoSerie;
import tp01.src.view.ViewSerie;
import java.util.Scanner;

public class ControllerSerie {

    private ArquivoSerie arqSeries;
    private ViewSerie visao;
    private static final Scanner console = new Scanner(System.in);

    public ControllerSerie() throws Exception {
        arqSeries = new ArquivoSerie();
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
                    /* alterarSerie(); */
                    break;
                case 4:
                    /* excluirSerie(); */
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void incluirSerie() {
        System.out.println("\nInclusão de livro");
        String nome = "";
        String synopsis = "";
        int episodes = 0;
        short releaseYear = 0;
        String streaming = "";

        boolean dadosCorretos = false;

        dadosCorretos = false;
        do {
            System.out.print("Nome: ");
            nome = console.nextLine();
            if (nome.length() == 0)
                return;
        } while (!dadosCorretos);

        dadosCorretos = false;

        do {
            System.out.print("Sinopse: ");
            synopsis = console.nextLine();
            if (synopsis.length() >= 0)
                dadosCorretos = true;
            else
                System.err.println("A sinopse não pode estar vazia.");
        } while (!dadosCorretos);

        dadosCorretos = false;

        dadosCorretos = false;

        do {
            System.out.print("Episódios: ");
            if (console.hasNextInt()) {
                episodes = console.nextInt();
                if (episodes > 0)
                    dadosCorretos = true;
            }
            if (!dadosCorretos)
                System.err.println("Episódios inválidos! Por favor, insira um número maior que 0.");
            console.nextLine();
        } while (!dadosCorretos);

        dadosCorretos = false;

        do {
            System.out.print("Data de Lançamento: ");
            if (console.hasNextShort()) {
                releaseYear = console.nextShort();
                if (releaseYear > 0)
                    dadosCorretos = true;
            }
            if (!dadosCorretos)
                System.err.println("Data inválida! Por favor, insira um número maior que 0.");
            console.nextLine();
        } while (!dadosCorretos);

        dadosCorretos = false;

        do {
            System.out.print("Streaming: ");
            streaming = console.nextLine();
            if (synopsis.length() >= 0)
                dadosCorretos = true;
            else
                System.err.println("A streaming não pode estar vazia.");
        } while (!dadosCorretos);

        dadosCorretos = false;

        System.out.print("\nConfirma a inclusão da série? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                Serie c = new Serie(nome, synopsis, episodes, releaseYear, streaming);
                arqSeries.create(c);
                System.out.println("Série incluído com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir a série!");
            }
        }
    }

    public void buscarSerie() {
        System.out.println("\nBusca de série por nome");
        System.out.print("\nNome: ");
        String nome = console.nextLine(); // Lê o título digitado pelo usuário

        if (nome.isEmpty())
            return;

        try {
            Serie[] series = arqSeries.read(nome); // Chama o método de leitura da classe Arquivo
            if (series.length > 0) {
                int n = 1;
                for (Serie s : series) {
                    System.out.println((n++) + ": " + s.getName());
                }
                System.out.print("Escolha a série: ");
                int o;
                do {
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch (NumberFormatException e) {
                        o = -1;
                    }
                    if (o <= 0 || o > n - 1)
                        System.out.println("Escolha um número entre 1 e " + (n - 1));
                } while (o <= 0 || o > n - 1);
                visao.mostraSerie(series[o - 1]); // Exibe os detalhes do livro encontrado
            } else {
                System.out.println("Nenhum livro encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar os livros!");
            e.printStackTrace();
        }
    }

    /*
     * private void alterarSerie() throws Exception {
     * String nome = visao.obterNome();
     * Serie serie = arqSeries.read(nome);
     * if (serie != null) {
     * visao.mostraSerie(serie);
     * 
     * String novoNome = visao.obterNome();
     * if (novoNome != null) serie.nome = novoNome;
     * 
     * String novaSinopse = visao.obterSinopse();
     * if (novaSinopse != null) serie.sinopse = novaSinopse;
     * 
     * int novosEpisodios = visao.obterQuantidadeEpisodios();
     * serie.episodes = novosEpisodios;
     * 
     * short novoAno = visao.obterAnoLancamento();
     * serie.releaseYear = novoAno;
     * 
     * String novoStreaming = visao.obterStreaming();
     * if (novoStreaming != null) serie.streaming = novoStreaming;
     * 
     * if (visao.confirmarAlteracoes()) {
     * boolean alterado = arqSeries.update(serie);
     * if (alterado) {
     * System.out.println("Série alterada com sucesso.");
     * } else {
     * System.out.println("Erro ao alterar a série.");
     * }
     * } else {
     * System.out.println("Alterações canceladas.");
     * }
     * } else {
     * System.out.println("Série não encontrada.");
     * }
     * }
     */

    /*
     * private void excluirSerie() throws Exception {
     * String nome = visao.obterNome();
     * Serie serie = arqSeries.read(nome);
     * if (serie != null) {
     * visao.mostraSerie(serie);
     * if (visao.confirmarExclusao()) {
     * boolean excluido = arqSeries.delete(nome);
     * if (excluido) {
     * System.out.println("Série excluída com sucesso.");
     * } else {
     * System.out.println("Erro ao excluir a série.");
     * }
     * } else {
     * System.out.println("Exclusão cancelada.");
     * }
     * } else {
     * System.out.println("Série não encontrada.");
     * }
     * }
     */
}
