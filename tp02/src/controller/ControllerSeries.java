package tp02.src.controller;

import tp02.src.data.ArchiveSeries;
import tp02.src.view.ViewSeries;
import tp02.src.models.*;
import tp02.src.storage.indexes.PairIDFK;
import tp02.src.data.ArchiveActor;
import tp02.src.view.ViewActor;
import tp02.src.data.ArchiveEpisode;
import tp02.src.data.ArchiveRelationNN;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controlador principal para manipulação de séries e episódios.
 * Atua como intermediário entre a camada de visualização (ViewSeries)
 * e a camada de dados (ArquivoSeries e ArquivoEpisode).
 */
public class ControllerSeries {

    private ArchiveSeries arqSeries;
    private ArchiveEpisode arqEpisodios;
    private ArchiveRelationNN arqRelationNN;
    private ArchiveActor arqActor;
    private ViewSeries visao;
    private static final Scanner console = new Scanner(System.in);

    /**
     * Construtor padrão que inicializa os arquivos e a interface de visualização.
     * 
     * @throws Exception caso ocorra erro ao acessar os arquivos.
     */
    public ControllerSeries() throws Exception {
        arqSeries = new ArchiveSeries();
        arqEpisodios = new ArchiveEpisode(); // Instantiate ArquivoEpisode
        arqRelationNN = new ArchiveRelationNN();
        arqActor = new ArchiveActor(); // Initialize arqActor
        visao = new ViewSeries();
    }

    /**
     * Exibe o menu principal com as opções de operações sobre séries e episódios.
     * 
     * @throws Exception caso ocorra erro ao executar alguma operação.
     */
    public void menu() throws Exception {
        int opcao;
        do {
            visao.exibirMenu();
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

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
                case 6:
                    listarEpisodiosPorTemporada();
                    break;
                case 7:
                    listActorsBySerie();
                    break;
                case 8:
                    vincularAtorASerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\nOpção inválida!");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                    break;
            }
        } while (opcao != 0);
    }

    /**
     * Inclui uma nova série no sistema, coletando os dados por meio da interface de
     * visualização.
     */
    public void incluirSerie() {
        System.out.println("\n\n===================================");
        System.out.println("         Inclusão de Série");
        System.out.println("===================================");
        System.out.println("Início > Séries > Incluir");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String name = visao.obterNome();
        if (name == null || name.isEmpty()) {
            System.out.println("Nome inválido. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        String synopsis = visao.obterSinopse();
        if (synopsis == null || synopsis.isEmpty()) {
            System.out.println("Sinopse inválida. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        short releaseYear = visao.obterAnoLancamento();
        if (releaseYear <= 0) {
            System.out.println("Ano de lançamento inválido. Inclusão cancelada.");
            System.out.println("\n===================================");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        String streaming = visao.obterStreaming();
        if (streaming == null || streaming.isEmpty()) {
            System.out.println("Plataforma de streaming inválida. Inclusão cancelada.");
            System.out.println(">>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        if (visao.confirmAction(1)) {
            System.out.println("-----------------------------------");
            try {
                Series novaSerie = new Series(name, synopsis, releaseYear, streaming);
                arqSeries.create(novaSerie);
                visao.mostraSerie(novaSerie);
                System.out.println("\nSérie incluída com sucesso.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            } catch (Exception e) {
                System.err.println("\nErro do sistema. Não foi possível\nincluir a série!");
                e.printStackTrace();
            }
        } else {
            System.out.println("-----------------------------------");
            System.out.println("\nInclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
        }
    }

    /**
     * Permite buscar uma série pelo nome e exibe suas informações detalhadas.
     */
    public void buscarSeriePorNome() {
        System.out.println("\n\n===================================");
        System.out.println("      Busca de Série por Nome");
        System.out.println("===================================");
        System.out.println("Início > Séries > Buscar");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        System.out.print("Digite o nome da série: ");
        String name = console.nextLine();

        if (name == null || name.isEmpty()){
            System.out.println("Nome inválido!");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        try {
            Series[] series = arqSeries.readNome(name);

            if (series == null || series.length == 0) { // Verifica se é nulo ou vazio
                System.out.println("-----------------------------------");
                System.out.println("\nNenhuma série encontrada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return; // Sai do método para evitar exceções
            }

            int n = 1;
            System.out.println("-----------------------------------");
            for (Series s : series) {
                System.out.println((n++) + ": " + s.getName());
            }

            System.out.println("-----------------------------------");
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

            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível\nbuscar as séries!");
            e.printStackTrace();
        }

    }

    /**
     * Permite alterar os dados de uma série previamente cadastrada.
     * Realiza verificação para manter valores antigos caso campos não sejam
     * alterados.
     */
    private void alterarSerie() {
        System.out.println("\n\n===================================");
        System.out.println("        Alteração de Série   ");
        System.out.println("===================================");
        System.out.println("Início > Séries > Alterar");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String name = visao.obterNome();
        if (name == null || name.isEmpty()){
            System.out.println("Nome inválido!");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        try {
            Series[] series = arqSeries.readNome(name);
            if (series.length > 0) {
                int n = 1;

                System.out.println("-----------------------------------");
                for (Series s : series) {
                    System.out.println((n++) + ": " + s.getName());
                }

                System.out.println("-----------------------------------");
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
                System.out.println("\n-> Editando:");
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
                        System.out.println("-----------------------------------");
                        visao.mostraSerie(serie);
                        System.out.println("\nSérie alterada com sucesso.");
                        System.out.println("\n>>> Pressione Enter para voltar <<<");
                        console.nextLine();
                    } else {
                        System.err.println("Erro ao alterar a série.");
                    }
                } else {
                    System.out.println("-----------------------------------");
                    System.out.println("\nAlteração cancelada.");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                }
            } else {
                System.out.println("-----------------------------------");
                System.out.println("\nNenhuma série encontrada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível\nalterar a série!");
            e.printStackTrace();
        }
    }

    /**
     * Exclui uma série do sistema, desde que não haja episódios vinculados a ela.
     */
    private void excluirSerie() {
        System.out.println("\n\n===================================");
        System.out.println("         Exclusão de Série");
        System.out.println("===================================");
        System.out.println("Início > Séries > Excluir");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("Digite o nome da série: ");
        String name = console.nextLine();

        if (name == null || name.isEmpty()){
            System.out.println("Nome inválido!");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        try {
            Series[] series = arqSeries.readNome(name);
            if (series == null || series.length == 0) {

                System.out.println("\nNenhuma série encontrada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            System.out.println("-----------------------------------");
            for (int i = 0; i < series.length; i++) {
                System.out.println((i + 1) + ": " + series[i].getName());
            }

            System.out.println("-----------------------------------");
            System.out.print("Escolha a série: ");
            int o;
            do {
                try {
                    o = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    o = -1;
                }
                if (o < 1 || o > series.length) {
                    System.out.print("Escolha um número entre 1 e " + series.length + ": ");
                }
            } while (o < 1 || o > series.length);

            Series serie = series[o - 1];
            visao.mostraSerie(serie);
            Episode[] epVinculados = arqEpisodios.readFkSerie(serie.getId());

            if (epVinculados != null && epVinculados.length != 0) {
                System.out.println("-----------------------------------");
                System.out.println("Erro! Não foi possível excluir essa\n" +
                                    "série, pois há episódios vinculados\n" + 
                                    "a ela.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            if (visao.confirmAction(3)) {
                System.out.println("-----------------------------------");
                if (!arqRelationNN.deleteAllRelations(serie.getId())) {
                    System.out.println("\nErro ao excluir as relações entre\nséries e atores. Exclusão cancelada.");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                    return;
                } else if (arqSeries.delete(serie.getId())) {
                    System.out.println("\nSérie excluída com sucesso.");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                } else {
                    System.err.println("\nErro ao excluir a série.");
                }
            } else {
                System.out.println("-----------------------------------");
                System.out.println("\nExclusão cancelada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível excluir a série!");
            e.printStackTrace();
        }
    }

    /**
     * Lista os episódios vinculados a uma série específica, buscada pelo nome.
     */
    public void listarEpisodiosPorSerie() {
        System.out.println("\n\n===================================");
        System.out.println("Listagem de episódios por série");
        System.out.println("===================================");
        System.out.println("Início > Séries > Listar Episódios");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("Digite o nome da série: ");
        String nomeSerie = console.nextLine();

        if (nomeSerie.isEmpty()) {
            System.out.println("\nNome da série inválido.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();

            return;
        }

        try {
            Series[] series = arqSeries.readNome(nomeSerie); // Fetch series by name
            if (series == null || series.length == 0) {
                System.out.println("\nNenhuma série encontrada com o nome fornecido.");
                return;
            }

            int n = 1;
            System.out.println("-----------------------------------");
            for (Series s : series) {
                System.out.println((n++) + ": " + s.getName());
            }

            System.out.println("-----------------------------------");
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
            System.out.println("\n===================================");
            System.out.println("Série: " + serie.getName());

            int idSerie = serie.getId();
            Episode[] episodios = arqEpisodios.readFkSerie(idSerie); // Fetch episodes for the series

            if (episodios == null || episodios.length == 0) {
                System.out.println("\nNenhum episódio encontrado para esta série.");
            } else {
                for (Episode episodio : episodios) {
                    System.out.println("-----------------------------------");
                    System.out.println("Nome: " + episodio.getName());
                    System.out.println("Temporada: " + episodio.getSeason());
                    System.out.println("Duração: " + episodio.getDuration() + " minutos");
                    System.out.println("Data de Lançamento: " + episodio.getRelease());
                }
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar episódios por série!");
            e.printStackTrace();
        }
    }

    /**
     * Lista episódios de uma temporada específica de uma série selecionada.
     */
    public void listarEpisodiosPorTemporada() {
        System.out.println("\n\n===================================");
        System.out.println("Listagem de episódios por temporada da série");
        System.out.println("===================================");
        System.out.println("Início > Séries > Listar Temporadas");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("Digite o nome da série: ");
        String nomeSerie = console.nextLine();

        if (nomeSerie.isEmpty()) {
            System.out.println("\nNome da série inválido.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();

            return;
        }

        try {
            Series[] series = arqSeries.readNome(nomeSerie); // Fetch series by name
            if (series == null || series.length == 0) {
                System.out.println("\nNenhuma série encontrada com o nome fornecido.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();

                return;
            }

            int n = 1;
            System.out.println("-----------------------------------");
            for (Series s : series) {
                System.out.println((n++) + ": " + s.getName());
            }

            System.out.println("-----------------------------------");
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
            System.out.println("\n===================================");
            System.out.println("Série: " + serie.getName());

            int idSerie = serie.getId();
            visao.mostraSerie(serie);
            Episode[] episodios = arqEpisodios.readFkSerie(idSerie); // Fetch episodes for the series

            if (episodios == null) {
                System.out.println("\nNão há episódios nessa série.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();

                return;
            }

            int temp = 0;
            for (int i = 0; i < episodios.length; i++) {
                if (temp < episodios[i].getSeason())
                    temp = episodios[i].getSeason();
            }
            System.out.println("\n===================================");
            System.out.println("Temporadas:");
            System.out.println("-----------------------------------");
            for (int i = 1; i <= temp; i++) {
                System.out.println(i + "ª");
            }
            System.out.println("-----------------------------------");
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

            System.out.println("\n===================================");
            System.out.println("Série: " + serie.getName());
            System.out.println("===================================");

            // Filter episodes by the desired season
            System.out.println("Episódios da temporada " + escolha + ":");
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
            }
            System.out.println("===================================");

            if (!encontrouEpisodios)
                System.out.println("\nNenhum episódio encontrado para a temporada " + temp + ".");

            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();

        } catch (Exception e) {
            System.out.println("Erro ao listar episódios da temporada!");
            e.printStackTrace();
        }
    }

    private void listActorsBySerie() {
        System.out.print("\nDigite o nome da série: ");
        String nomeSerie = console.nextLine();

        if (nomeSerie.isEmpty()) {
            System.out.println("Nome da série inválido.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        try {
            Series[] series = arqSeries.readNome(nomeSerie); // Fetch series by name
            if (series == null || series.length == 0) {
                System.out.println("\nNenhuma série encontrada com o nome fornecido.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            int n = 1;
            System.out.println("-----------------------------------");
            for (Series s : series) {
                System.out.println((n++) + ": " + s.getName());
            }

            System.out.println("-----------------------------------");
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
            System.out.println("\n===================================");
            System.out.println("Elenco de " + serie.getName());
            System.out.println("===================================");

            if(serie.getName().length() < 9){
                System.out.println("Início > Séries > " + serie.getName() + " > Elenco");
            } else if (serie.getName().length() < 16) {
                System.out.println("Início > Séries > " + serie.getName() + " >\nElenco");
            } else if (serie.getName().length() < 18) {
                System.out.println("Início > Séries > " + serie.getName() + "\n> Elenco");
            } else {
                System.out.println("Início > Séries >\n" + serie.getName() + "\n> Elenco");
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            int idSerie = serie.getId();

            ArrayList<PairIDFK> relations = arqRelationNN.readActorsBySerie(idSerie); // Fetch actors for the series

            if (relations == null || relations.isEmpty()) {
                System.out.println("Nenhum ator encontrado para esta série.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            } else {
                for (PairIDFK relation : relations) {
                    int idActor = relation.getId(); // É ao contrário porque a busca em PairIDFK é feita por fk e não id
                    Actor actor = arqActor.read(idActor);
                    System.out.println("-----------------------------------");
                    if (actor != null) {
                        System.out.println("Nome: " + actor.getName());
                        System.out.println("ID: " + actor.getId());
                    } else {
                        System.out.println("Erro: Ator com ID " + idActor + " não encontrado."); // Debug statement
                    }
                }
                System.out.println("-----------------------------------");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar atores/atrizes da Série!");
            e.printStackTrace();
        }
    }

    private void vincularAtorASerie() {
        System.out.println("\n\n===================================");
        System.out.println("    Vinculação de Ator à Série");
        System.out.println("===================================");
        System.out.println("Início > Séries > Vincular Ator");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        System.out.print("Digite o nome da série: ");
        String nomeSerie = console.nextLine();

        if (nomeSerie.isEmpty()) {
            System.out.println("\nNome da série inválido.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        try {
            Series[] series = arqSeries.readNome(nomeSerie);
            if (series == null || series.length == 0) {
                System.out.println("\nNenhuma série encontrada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            int n = 1;
            System.out.println("-----------------------------------");
            for (Series s : series) {
                System.out.println((n++) + ": " + s.getName());
            }
            System.out.println("-----------------------------------");

            System.out.print("Escolha a Série: ");
            int escolha = Integer.parseInt(console.nextLine());
            Series serie = series[escolha - 1];

            ViewActor viewActor = new ViewActor();
            System.out.print("\nDigite o nome do ator/atriz: ");
            String atorNome = console.nextLine();

            if (atorNome == null || atorNome.isEmpty()) {
                System.out.println("\nNome do ator/atriz inválido.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            Actor[] atores = arqActor.readNome(atorNome);
            if (atores == null) {
                System.out.println("\nAtor/atriz não encontrado.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            n = 1;
            System.out.println("-----------------------------------");
            for (Actor a : atores) {
                System.out.println((n++) + ": " + a.getName());
            }

            System.out.println("-----------------------------------");
            System.out.print("Escolha o ator/atriz: ");

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

            viewActor.mostraAtor(atores[o - 1]);

            if (visao.confirmAction(1)) {
                System.out.println("-----------------------------------");
                String debug = arqRelationNN.createRelation(atores[o - 1].getId(), serie.getId());
                System.out.println("\nAtor vinculado à série com sucesso!");
                System.out.print("\nPressione \"D\" para ver o debug ou\n\"Enter\" para voltar: ");
                String resp = console.nextLine();

                if(resp.equalsIgnoreCase("d")){
                    System.out.println(debug);
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                }
            } else {
                System.out.println("-----------------------------------");
                System.out.println("\nInclusão cancelada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro ao vincular ator à série.");
            e.printStackTrace();
        }
    }
}
