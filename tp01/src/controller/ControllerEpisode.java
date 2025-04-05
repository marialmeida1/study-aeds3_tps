package tp01.src.controller;

import tp01.src.models.Episode;
import tp01.src.models.Series;
import tp01.src.data.ArquivoEpisode;
import tp01.src.data.ArquivoSeries;
import tp01.src.view.ViewEpisode;

import java.time.LocalDate;
import java.util.Scanner;

public class ControllerEpisode {

    // Arquivos das Classes
    private ArquivoEpisode arqEpisodios;
    private ArquivoSeries arqSeries;

    private ViewEpisode visaoEpisodios;
    private static final Scanner console = new Scanner(System.in);

    public ControllerEpisode() throws Exception {
        arqEpisodios = new ArquivoEpisode();
        arqSeries = new ArquivoSeries();
        visaoEpisodios = new ViewEpisode();
    }

    public void menu() throws Exception {
        int opcao;
        do {
            visaoEpisodios.exibirMenu();
            opcao = Integer.valueOf(console.nextLine());

            switch (opcao) {
                case 1:
                    incluirEpisodio();
                    break;
                case 2:
                    buscarEpisodio();
                    break;
                case 3:
                    alterarEpisodio();
                    break;
                case 4:
                    excluirEpisodio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void buscarEpisodio() throws Exception {
        System.out.println("\n\n===============================");
        System.out.println("  Busca de episódio por nome");
        System.out.println("===============================");

        int fkSerie = buscarSeriePorNome().getId();
        Episode[] episodios = arqEpisodios.readFkSerie(fkSerie);

        try {
            if (episodios == null || episodios.length == 0) { // Verifica se é nulo ou vazio
                System.out.println("-------------------------------");
                System.out.println("Nenhum episódio encontrada.");
                System.out.println("===============================");
                return; // Sai do método para evitar exceções
            }

            int n = 1;
            System.out.println("-------------------------------");
            for (Episode s : episodios) {
                System.out.println((n++) + ": " + s.getName());
            }

            System.out.println("-------------------------------");
            System.out.print("Escolha o Episódio: ");

            int o;
            do {
                try {
                    o = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    o = -1;
                }
                if (o <= 0 || o > n - 1)
                    System.out.println("Escolha um número entre 1 e " + (n - 1));
            } while (o <= 0 || o > n - 1);

            visaoEpisodios.mostraEpisodio(episodios[o - 1]);

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível buscar as séries!");
            e.printStackTrace();
        }

    }

    public void incluirEpisodio() {
        System.out.println("\n\n===============================");
        System.out.println("      Inclusão de série");
        System.out.println("===============================");

        // Buscando qual série irá inserir
        int fkSerie = buscarSeriePorNome().getId();

        // Inserindo valores
        String name = visaoEpisodios.obterNome();
        if (name == null || name.isEmpty()) {
            System.out.println("Nome inválido. Inclusão cancelada.");
            return;
        }

        int season = visaoEpisodios.obterTemporada();
        if (season <= 0) {
            System.out.println("Temporada inválida. Inclusão cancelada.");
            return;
        }

        LocalDate release = visaoEpisodios.obterDataLancamento();

        int duration = visaoEpisodios.obterTemporada();
        if (duration <= 0) {
            System.out.println("Duração inválida. Inclusão cancelada.");
            return;
        }

        if (visaoEpisodios.confirmAction(1)) {
            try {
                Episode novoEpisode = new Episode(fkSerie, name, season, release, duration);
                arqEpisodios.create(novoEpisode);
                System.out.println("-------------------------------");
                System.out.println("Episódio incluída com sucesso.");
                System.out.println("===============================");
            } catch (Exception e) {
                System.err.println("Erro do sistema. Não foi possível incluir a série!");
                e.printStackTrace();
            }
        } else {
            System.out.println("-------------------------------");
            System.out.println("Episódio cancelada.");
            System.out.println("===============================");
        }
    }

    private void alterarEpisodio() {
        System.out.println("\n\n===============================");
        System.out.println("      Alteração de Série");
        System.out.println("===============================");

        int fkSerie = buscarSeriePorNome().getId();
        Episode[] episodios = arqEpisodios.readFkSerie(fkSerie);

        try {
            if (episodios.length > 0) {
                int n = 1;

                System.out.println("-------------------------------");
                for (Episode s : episodios) {
                    System.out.println((n++) + ": " + s.getName());
                }

                System.out.println("-------------------------------");
                System.out.print("Escolha o episodio: ");
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

                Episode episode = episodios[o - 1];
                visaoEpisodios.mostraEpisodio(episode);

                String novoNome = visaoEpisodios.obterNome();
                if (novoNome != null && !novoNome.isEmpty()) {
                    episode.setName(novoNome);
                } else {
                    novoNome = episode.getName(); // Mantém o nome antigo
                }

                int novaTemporada = visaoEpisodios.obterTemporada();
                if (novaTemporada > 0) {
                    episode.setSeason(novaTemporada);
                } else {
                    novaTemporada = episode.getSeason();
                }

                LocalDate novaData = visaoEpisodios.obterDataLancamento();
                if (novaData != null) {
                    episode.setRelease(novaData);
                }

                int novaDuracao = visaoEpisodios.obterTemporada();
                if (novaDuracao > 0) {
                    episode.setSeason(novaDuracao);
                } else {
                    novaDuracao = episode.getSeason();
                }

                if (visaoEpisodios.confirmAction(2)) {
                    boolean alterado = arqEpisodios.update(episode);
                    if (alterado) {
                        System.out.println("-------------------------------");
                        System.out.println("Episódio alterado com sucesso.");
                        System.out.println("===============================");
                    } else {
                        System.err.println("Erro ao alterar o episódio.");
                    }
                } else {
                    System.out.println("-------------------------------");
                    System.out.println("Alteração cancelada.");
                    System.out.println("===============================");
                }
            } else {
                System.out.println("-------------------------------");
                System.out.println("Nenhum episódio encontrada.");
                System.out.println("===============================");
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível alterar o episódio!");
            e.printStackTrace();
        }
    }

    private void excluirEpisodio() {
        System.out.println("\n\n===============================");
        System.out.println("      Exclusão de Série");
        System.out.println("===============================");
        System.out.print("Nome: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return;

        try {
            Series[] series = arqEpisodios.readNome(name);
            if (series.length == 0) {
                System.out.println("-------------------------------");
                System.out.println("Nenhuma série encontrada.");
                System.out.println("===============================");
                return;
            }

            System.out.println("-------------------------------");
            for (int i = 0; i < series.length; i++) {
                System.out.println((i + 1) + ": " + series[i].getName());
            }

            int escolha;
            do {
                System.out.println("-------------------------------");
                System.out.print("Escolha a série: ");
                try {
                    escolha = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    escolha = -1;
                }
                if (escolha < 1 || escolha > series.length) {
                    System.out.println("Escolha um número entre 1 e " + series.length);
                }
            } while (escolha < 1 || escolha > series.length);

            Series serie = series[escolha - 1];
            visaoEpisodios.mostraSerie(serie);

            if (visaoEpisodios.confirmAction(3)) {
                if (arqEpisodios.delete(serie.getId())) {
                    System.out.println("-------------------------------");
                    System.out.println("Série excluída com sucesso.");
                    System.out.println("===============================");
                } else {
                    System.err.println("Erro ao excluir a série.");
                }
            } else {
                System.out.println("-------------------------------");
                System.out.println("Exclusão cancelada.");
                System.out.println("===============================");

            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível excluir a série!");
            e.printStackTrace();
        }
    }

    // Tratamento da Relação
    public Series buscarSeriePorNome() {
        System.out.println("\n\n===============================");
        System.out.println("    Busca de série por nome");
        System.out.println("===============================");
        System.out.print("Nome: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return null;

        try {
            Series[] series = arqSeries.readNome(name);

            if (series == null || series.length == 0) { // Verifica se é nulo ou vazio
                System.out.println("-------------------------------");
                System.out.println("Nenhuma série encontrada.");
                System.out.println("===============================");
                return null; // Sai do método para evitar exceções
            }

            int n = 1;
            System.out.println("-------------------------------");
            for (Series s : series) {
                System.out.println((n++) + ": " + s.getName());
            }

            System.out.println("-------------------------------");
            System.out.print("Escolha a Série: ");

            int o;
            do {
                try {
                    o = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    o = -1;
                }
                if (o <= 0 || o > n - 1)
                    System.out.println("Escolha um número entre 1 e " + (n - 1));
            } while (o <= 0 || o > n - 1);

            return series[o - 1];

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível buscar as séries!");
            e.printStackTrace();
        }
        return null;
    }
}
