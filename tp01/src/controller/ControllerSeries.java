package tp01.src.controller;

import tp01.src.data.ArquivoSeries;
import tp01.src.view.ViewSeries;
import tp01.src.models.*;
import tp01.src.data.ArquivoEpisode;

import java.util.Scanner;

public class ControllerSeries {

    private ArquivoSeries arqSeries;
    private ArquivoEpisode arqEpisodios; // Added ArquivoEpisode instance
    private ViewSeries visao;
    private static final Scanner console = new Scanner(System.in);

    public ControllerSeries() throws Exception {
        arqSeries = new ArquivoSeries();
        arqEpisodios = new ArquivoEpisode(); // Instantiate ArquivoEpisode
        visao = new ViewSeries();
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
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 5:
                    listarEpisodiosPorSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void listarEpisodiosPorSerie() {
        System.out.println("\n\n===============================");
        System.out.println("Listagem de episódios por série");
        System.out.println("===============================");
        System.out.print("Digite o nome da série: ");
        String nomeSerie = console.nextLine();

        if (nomeSerie.isEmpty()) {
            System.out.println("Nome da série inválido.");
            return;
        }

        try {
            Series[] series = arqSeries.readNome(nomeSerie); // Fetch series by name
            if (series == null || series.length == 0) {
                System.out.println("Nenhuma série encontrada com o nome fornecido.");
                return;
            }

            int n = 1;
            System.out.println("-------------------------------");
            for (Series s : series) {
                System.out.println((n++) + ": " + s.getName());
            }

            System.out.println("-------------------------------");
            System.out.print("Escolha a Série: ");

            int escolha;
            do {
                try {
                    escolha = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    escolha = -1;
                }
                if (escolha <= 0 || escolha > n - 1)
                    System.out.print("Escolha um número entre 1 e " + (n - 1) + ": ");
            } while (escolha <= 0 || escolha > n - 1);

            Series serie = series[escolha - 1]; // Use the selected series
            System.out.println("\n===============================");
            System.out.println("Série: " + serie.getName());

            int idSerie = serie.getId();
            Episode[] episodios = arqEpisodios.readFkSerie(idSerie); // Fetch episodes for the series

            if (episodios == null || episodios.length == 0) {
                System.out.println("Nenhum episódio encontrado para esta série.");
            } else {
                for (Episode episodio : episodios) {
                    System.out.println("-------------------------------");
                    System.out.println("Nome: " + episodio.getName());
                    System.out.println("Temporada: " + episodio.getSeason());
                    System.out.println("Duração: " + episodio.getDuration() + " minutos");
                    System.out.println("Data de Lançamento: " + episodio.getRelease());
                }
                System.out.println("===============================");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar episódios por série!");
            e.printStackTrace();
        }
    }

    public void listarEpisodiosPorTemporada() {
        System.out.println("\n\n===============================");
        System.out.println("Listagem de episódios por temporada da série");
        System.out.println("===============================");
        System.out.print("Digite o nome da série: ");
        String nomeSerie = console.nextLine();        
    
        if (nomeSerie.isEmpty()) {
            System.out.println("Nome da série inválido.");
            return;
        }

        try {
            Series[] series = arqSeries.readNome(nomeSerie); // Fetch series by name
            if (series == null || series.length == 0) {
                System.out.println("Nenhuma série encontrada com o nome fornecido.");
                return;
            }

            int n = 1;
            System.out.println("-------------------------------");
            for (Series s : series) {
                System.out.println((n++) + ": " + s.getName());
            }

            System.out.println("-------------------------------");
            System.out.print("Escolha a Série: ");

            int escolha;
            do {
                try {
                    escolha = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    escolha = -1;
                }
                if (escolha <= 0 || escolha > n - 1)
                    System.out.print("Escolha um número entre 1 e " + (n - 1) + ": ");
            } while (escolha <= 0 || escolha > n - 1);

            Series serie = series[escolha - 1]; // Use the selected series
            System.out.println("\n===============================");
            System.out.println("Série: " + serie.getName());

            int idSerie = serie.getId();
            visao.mostraSerie(serie);
            Episode[] episodios = arqEpisodios.readFkSerie(idSerie); // Fetch episodes for the series
            
            int temp = episodios[episodios.length - 1].getSeason();
            
            System.out.println("Temporadas:");
            for(int i = 1; i <=temp; i++){
                System.out.println(i);
            }
            System.out.println();

            System.out.print("Escolha a temporada: ");
            do {
                try {
                    escolha = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    escolha = -1;
                }
                if (escolha <= 0 || escolha > temp)
                    System.out.print("Escolha um número entre 1 e " + temp + ": ");
            } while (escolha <= 0 || escolha > temp);

            System.out.println("\n===============================");
            System.out.println("Série: " + serie.getName());

            // Filter episodes by the desired season
            System.out.println("\nEpisódios da temporada " + escolha + ":\n");
            boolean encontrouEpisodios = false;
            for (Episode ep : episodios) {
                if (ep.getSeason() == escolha) {
                    System.out.println("----------------------------");
                    System.out.println("Nome: " + ep.getName());
                    System.out.println("Temporada: " + ep.getSeason());
                    System.out.println("Duração: " + ep.getDuration() + " minutos");
                    System.out.println("Data de Lançamento: " + ep.getRelease());
                    encontrouEpisodios = true;
                }
                System.out.println("===============================");
            }            

            if (!encontrouEpisodios) 
                System.out.println("Nenhum episódio encontrado para a temporada " + temp + ".");
            
        } catch (Exception e) {
            System.out.println("Erro ao listar episódios da temporada!");
            e.printStackTrace();
        }
    }

    public void buscarSeriePorNome() {
        System.out.println("\n\n===============================");
        System.out.println("    Busca de série por nome");
        System.out.println("===============================");
        System.out.print("Nome: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return;

        try {
            Series[] series = arqSeries.readNome(name);

            if (series == null || series.length == 0) { // Verifica se é nulo ou vazio
                System.out.println("-------------------------------");
                System.out.println("Nenhuma série encontrada.");
                System.out.println("===============================");
                return; // Sai do método para evitar exceções
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
                    System.out.print("Escolha um número entre 1 e " + (n - 1) + ": ");
            } while (o <= 0 || o > n - 1);

            visao.mostraSerie(series[o - 1]);

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível buscar as séries!");
            e.printStackTrace();
        }

    }

    public void incluirSerie() {
        System.out.println("\n\n===============================");
        System.out.println("      Inclusão de série");
        System.out.println("===============================");

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
                Series novaSerie = new Series(name, synopsis, releaseYear, streaming);
                arqSeries.create(novaSerie);
                System.out.println("-------------------------------");
                System.out.println("Série incluída com sucesso.");
                System.out.println("===============================");
            } catch (Exception e) {
                System.err.println("Erro do sistema. Não foi possível incluir a série!");
                e.printStackTrace();
            }
        } else {
            System.out.println("-------------------------------");
            System.out.println("Inclusão cancelada.");
            System.out.println("===============================");
        }
    }

    private void alterarSerie() {
        System.out.println("\n\n===============================");
        System.out.println("      Alteração de Série");
        System.out.println("===============================");

        String name = visao.obterNome();
        if (name == null || name.isEmpty())
            return;

        try {
            Series[] series = arqSeries.readNome(name);
            if (series.length > 0) {
                int n = 1;

                System.out.println("-------------------------------");
                for (Series s : series) {
                    System.out.println((n++) + ": " + s.getName());
                }

                System.out.println("-------------------------------");
                System.out.print("Escolha a série: ");
                int o;
                do {
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch (NumberFormatException e) {
                        o = -1;
                    }
                    if (o <= 0 || o > n - 1)
                        System.out.print("Escolha um número entre 1 e " + (n - 1) + ": ");
                } while (o <= 0 || o > n - 1);

                Series serie = series[o - 1];
                visao.mostraSerie(serie);

                String novoNome = visao.obterNome();
                if (novoNome != null && !novoNome.isEmpty()) {
                    serie.setName(novoNome);
                } else {
                    novoNome = serie.getName(); // Mantém o nome antigo
                }

                String novaSinopse = visao.obterSinopse();
                if (novaSinopse != null && !novaSinopse.isEmpty()) {
                    serie.setSynopsis(novaSinopse);
                } else {
                    novaSinopse = serie.getSynopsis();
                }

                short novoAno = visao.obterAnoLancamento();
                if (novoAno > 0) {
                    serie.setReleaseYear(novoAno);
                } else {
                    novoAno = serie.getReleaseYear();
                }

                String novoStreaming = visao.obterStreaming();
                if (novoStreaming != null && !novoStreaming.isEmpty()) {
                    serie.setStreaming(novoStreaming);
                } else {
                    novoStreaming = serie.getStreaming();
                }

                if (visao.confirmAction(2)) {
                    boolean alterado = arqSeries.update(serie);
                    if (alterado) {
                        System.out.println("-------------------------------");
                        System.out.println("Série alterada com sucesso.");
                        System.out.println("===============================");
                    } else {
                        System.err.println("Erro ao alterar a série.");
                    }
                } else {
                    System.out.println("-------------------------------");
                    System.out.println("Alteração cancelada.");
                    System.out.println("===============================");
                }
            } else {
                System.out.println("-------------------------------");
                System.out.println("Nenhuma série encontrada.");
                System.out.println("===============================");
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível alterar a série!");
            e.printStackTrace();
        }
    }

    private void excluirSerie() {
        System.out.println("\n\n===============================");
        System.out.println("      Exclusão de Série");
        System.out.println("===============================");
        System.out.print("Digite o nome da série: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return;

        try {
            Series[] series = arqSeries.readNome(name);
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
                    System.out.print("Escolha um número entre 1 e " + series.length + ": ");
                }
            } while (escolha < 1 || escolha > series.length);

            Series serie = series[escolha - 1];
            visao.mostraSerie(serie);
            Episode[] epVinculados = arqEpisodios.readFkSerie(serie.getId());

            if (epVinculados != null || epVinculados.length != 0) {
                System.out.println("Erro! Não foi possível excluir essa série, pois há episódios vinculados a ela.");
                return;
            }

            if (visao.confirmAction(3)) {
                if (arqSeries.delete(serie.getId())) {
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
}
