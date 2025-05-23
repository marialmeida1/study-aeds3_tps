package tp02.src.controller;

import tp02.src.models.Episode;
import tp02.src.models.Series;
import tp02.src.data.ArchiveEpisode;
import tp02.src.data.ArchiveSeries;
import tp02.src.view.ViewEpisode;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Controlador responsável por gerenciar operações relacionadas aos episódios.
 * Inclui funcionalidades de criação, busca, alteração e exclusão de episódios.
 */
public class ControllerEpisode {

    // Arquivos das Classes
    private ArchiveEpisode arqEpisodios;
    private ArchiveSeries arqSeries;
    private ViewEpisode visaoEpisodios;
    private static final Scanner console = new Scanner(System.in);

    /**
     * Construtor da classe. Inicializa os arquivos de dados e a visão.
     * 
     * @throws Exception caso ocorra erro na inicialização dos arquivos.
     */
    public ControllerEpisode() throws Exception {
        arqEpisodios = new ArchiveEpisode();
        arqSeries = new ArchiveSeries();
        visaoEpisodios = new ViewEpisode();
    }

    /**
     * Exibe o menu principal de opções e executa a operação escolhida pelo usuário.
     * 
     * @throws Exception caso ocorra erro durante a execução das operações.
     */
    public void menu() throws Exception {
        int opcao;
        do {
            visaoEpisodios.exibirMenu();
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

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
                    System.out.println("\nOpção inválida!");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                    break;
            }
        } while (opcao != 0);
    }

        /**
     * Coleta os dados do usuário e realiza a inclusão de um novo episódio.
         * @throws Exception 
     */
    public void incluirEpisodio() throws Exception {
        System.out.println("\n\n===================================");
        System.out.println("      Inclusão de Episódios");
        System.out.println("===================================");
        System.out.println("Início > Episódios > Incluir");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        System.out.print("Digite o nome da série: ");
        String nameSerie = console.nextLine();

        if (nameSerie == null || nameSerie.isEmpty()){
            System.out.println("Nome inválido!");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        Series[] series = arqSeries.readNome(nameSerie);

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

        Series serie = series[o - 1];
        int fkSerie = serie.getId();
        System.out.println("\nSérie: " + serie.getName());

        // Inserindo valores
        String name = visaoEpisodios.obterNome();
        if (name == null || name.isEmpty()) {
            System.out.println("Nome inválido. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        int season = visaoEpisodios.obterTemporada();
        if (season <= 0) {
            System.out.println("Temporada inválida. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        LocalDate release = visaoEpisodios.obterDataLancamento();
        if (release == null) {
            System.out.println("Data de lançamento inválida. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        int duration = visaoEpisodios.obterDuracao();
        if (duration <= 0) {
            System.out.println("Duração inválida. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        if (visaoEpisodios.confirmAction(1)) {
            try {
                Episode novoEpisode = new Episode(fkSerie, name, season, release, duration);
                arqEpisodios.create(novoEpisode);
                System.out.println("-----------------------------------");
                System.out.println("\nEpisódio incluído com sucesso.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            } catch (Exception e) {
                System.err.println("Erro do sistema. Não foi possível incluir o episódio!");
                e.printStackTrace();
            }
        } else {
            System.out.println("-----------------------------------");
            System.out.println("\nInclusão de Episódio cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
        }
    }

    /**
     * Realiza a busca de um episódio por nome, dentro de uma série específica.
     * 
     * @return o episódio encontrado ou {@code null} se não encontrado.
     * @throws Exception caso ocorra erro na busca.
     */
    public Episode buscarEpisodioPorNome() throws Exception {
        System.out.println("\n\n===================================");
        System.out.println("      Buscar Episódio por Nome");
        System.out.println("===================================");
        System.out.println("Início > Episódios > Buscar");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        System.out.print("Digite o nome da série: ");
        String nameSerie = console.nextLine();

        if (nameSerie == null || nameSerie.isEmpty()){
            System.out.println("Nome inválido!");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return null;
        }

        Series[] series = arqSeries.readNome(nameSerie);

        if (series == null || series.length == 0) { // Verifica se é nulo ou vazio
            System.out.println("-----------------------------------");
            System.out.println("\nNenhuma série encontrada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return null; // Sai do método para evitar exceções
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

        int fkSerie = series[o - 1].getId();
    
        System.out.print("\nDigite o nome do episódio: ");
        String name = console.nextLine();
    
        Episode[] episodios = arqEpisodios.readEpisodiosPorSerieENome(fkSerie, name);
        
        if (episodios == null || episodios.length == 0) {
            System.out.println("\nNome do episódio inválido.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return null;
        }
    
        n = 1;
        System.out.println("-----------------------------------");
        for (Episode e : episodios) {
            System.out.println((n++) + ": " + e.getName());
        }
    
        System.out.println("-----------------------------------");
        System.out.print("Escolha o Episódio: ");
    
        do {
            try {
                o = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                o = -1;
            }
            if (o <= 0 || o > n - 1)
                System.out.print("Escolha um número entre 1 e " + (n - 1) + ": ");
        } while (o <= 0 || o > n - 1);
    
        return episodios[o - 1]; // Retorna o episódio escolhido
    }

    /**
     * Busca e exibe um episódio selecionado pelo usuário.
     * 
     * @throws Exception caso ocorra erro na busca.
     */
    public void buscarEpisodio() throws Exception {
        try {
            Episode episodio = buscarEpisodioPorNome();
            if (episodio != null) {
                visaoEpisodios.mostraEpisodio(episodio);
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível\nbuscar o episódio!");
            e.printStackTrace();
        }
    }    

    /**
     * Altera os dados de um episódio já existente.
     */
    private void alterarEpisodio() {
        System.out.println("\n\n===================================");
        System.out.println("      Alteração de Episódio");
        System.out.println("===================================");
        System.out.println("Início > Episódios > Alterar");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    
        try {
            Episode episode = buscarEpisodioPorNome();
            if (episode == null) {
                System.out.println("-----------------------------------");
                System.out.println("Nenhum episódio encontrado.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }
    
            visaoEpisodios.mostraEpisodio(episode);
    
            String novoNome = visaoEpisodios.obterNome();
            if (novoNome != null && !novoNome.isEmpty()) {
                episode.setName(novoNome);
            } else {
                novoNome = episode.getName();
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
            } else {
                novaData = episode.getRelease();
            }
    
            int novaDuracao = visaoEpisodios.obterDuracao(); // Correção aqui
            if (novaDuracao > 0) {
                episode.setDuration(novaDuracao);
            } else {
                novaDuracao = episode.getDuration();
            }
    
            if (visaoEpisodios.confirmAction(2)) {
                boolean alterado = arqEpisodios.update(episode);
                if (alterado) {
                    System.out.println("-----------------------------------");
                    System.out.println("Episódio alterado com sucesso.");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                } else {
                    System.err.println("Erro ao alterar o episódio.");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                }
            } else {
                System.out.println("-----------------------------------");
                System.out.println("Alteração cancelada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível\nbuscar o episódio!");
            e.printStackTrace();
        }
    }    

    /**
     * Exclui um episódio selecionado pelo usuário.
     */
    private void excluirEpisodio() {
        System.out.println("\n\n===================================");
        System.out.println("      Exclusão de Episódio");
        System.out.println("===================================");

        try {
            Episode episode = buscarEpisodioPorNome();
            if (episode == null) {
                System.out.println("-----------------------------------");
                System.out.println("Nenhum episódio encontrado.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }
    
            
            if (visaoEpisodios.confirmAction(3)) {
                boolean deletado = arqEpisodios.delete(episode.getId());
                if (deletado) {
                    System.out.println("-----------------------------------");
                    System.out.println("\nEpisódio excluído com sucesso.");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                } else {
                    System.err.println("Erro ao excluir o episódio.");
                }
            } else {
                System.out.println("-----------------------------------");
                System.out.println("\nExclusão cancelada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível\nbuscar o episódio!");
            e.printStackTrace();
        }
    }    

    /**
     * Realiza a busca de uma série pelo nome.
     * 
     * @return a série encontrada ou {@code null} se não encontrada.
     */
    public Series buscarSeriePorNome() {
        System.out.println("\n\n===================================");
        System.out.println("      Busca de série por nome");
        System.out.println("===================================");
        System.out.print("Nome: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return null;

        try {
            Series[] series = arqSeries.readNome(name);

            if (series == null || series.length == 0) { // Verifica se é nulo ou vazio
                System.out.println("-----------------------------------");
                System.out.println("\nNenhuma série encontrada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return null; // Sai do método para evitar exceções
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

            return series[o - 1];

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível\nbuscar as séries!");
            e.printStackTrace();
        }
        return null;
    }
}
