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
                    buscarSeriePorNome();
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

    public void buscarSeriePorNome() {
        System.out.println("\nBusca de série por name");
        System.out.print("\nNome: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return;

        try {
            Serie[] series = arqSeries.readNome(name);
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
                visao.mostraSerie(series[o - 1]);
            } else {
                System.out.println("Nenhuma série encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar as séries!");
            e.printStackTrace();

        }

    }

    public void incluirSerie() {
        System.out.println("\nInclusão de série");

        String name = visao.obterNome();
        if (name == null || name.isEmpty()) {
            System.out.println("Nome inválido. Inclusão cancelada.");
            return;
        }

        String synopsis = visao.obterSinopse();
        if (synopsis == null || synopsis.isEmpty()) {
            System.out.println("Sinopse inválida. Inclusão cancelada.");
            return;
        }

        int episodes = visao.obterQuantidadeEpisodios();
        if (episodes <= 0) {
            System.out.println("Quantidade de episódios inválida. Inclusão cancelada.");
            return;
        }

        short releaseYear = visao.obterAnoLancamento();
        if (releaseYear <= 0) {
            System.out.println("Ano de lançamento inválido. Inclusão cancelada.");
            return;
        }

        String streaming = visao.obterStreaming();
        if (streaming == null || streaming.isEmpty()) {
            System.out.println("Plataforma de streaming inválida. Inclusão cancelada.");
            return;
        }

        if (visao.confirmAction(1)) {
            try {
                Serie novaSerie = new Serie(name, synopsis, episodes, releaseYear, streaming);
                arqSeries.create(novaSerie);
                System.out.println("Série incluída com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir a série!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Inclusão cancelada.");
        }
    }

    private void alterarSerie() {
        System.out.println("\nAlteração de série");
        String name = visao.obterNome();
        if (name == null || name.isEmpty())
            return;

        try {
            Serie[] series = arqSeries.readNome(name);
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

                Serie serie = series[o - 1];
                visao.mostraSerie(serie);

                String novoNome = visao.obterNome();
                if (novoNome != null && !novoNome.isEmpty()) {
                    serie.setName(novoNome);
                }

                String novaSinopse = visao.obterSinopse();
                if (novaSinopse != null && !novaSinopse.isEmpty()) {
                    serie.setSynopsis(novaSinopse);
                }

                int novosEpisodios = visao.obterQuantidadeEpisodios();
                if (novosEpisodios > 0) {
                    serie.setEpisodes(novosEpisodios);
                }

                short novoAno = visao.obterAnoLancamento();
                if (novoAno > 0) {
                    serie.setReleaseYear(novoAno);
                }

                String novoStreaming = visao.obterStreaming();
                if (novoStreaming != null && !novoStreaming.isEmpty()) {
                    serie.setStreaming(novoStreaming);
                }

                if (visao.confirmAction(2)) {
                    boolean alterado = arqSeries.update(serie);
                    if (alterado) {
                        System.out.println("Série alterada com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar a série.");
                    }
                } else {
                    System.out.println("Alteração cancelada.");
                }
            } else {
                System.out.println("Nenhuma série encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar a série!");
            e.printStackTrace();
        }
    }

    private void excluirSerie() {
        System.out.println("\nExclusão de série");
        System.out.print("\nNome: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return;

        try {
            Serie[] series = arqSeries.readNome(name);
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

                Serie serie = series[o - 1];
                visao.mostraSerie(serie);

                if (visao.confirmAction(3)) {
                    boolean excluido = arqSeries.delete(serie.getId());
                    if (excluido) {
                        System.out.println("Série excluída com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir a série.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
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
                System.out.println("Nenhuma série encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir a série!");
            e.printStackTrace();
        }
    }
}
