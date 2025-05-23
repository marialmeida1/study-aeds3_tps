package tp01.src.controller;

import tp01.src.models.Episode;
import tp01.src.models.Series;
import tp01.src.data.ArquivoEpisode;
import tp01.src.data.ArquivoSeries;
import tp01.src.view.ViewEpisode;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Controlador responsável por gerenciar operações relacionadas aos episódios.
 * Inclui funcionalidades de criação, busca, alteração e exclusão de episódios.
 */
public class ControllerEpisode {

    // Arquivos das Classes
    private ArquivoEpisode arqEpisodios;
    private ArquivoSeries arqSeries;
    private ViewEpisode visaoEpisodios;
    private static final Scanner console = new Scanner(System.in);

    /**
     * Construtor da classe. Inicializa os arquivos de dados e a visão.
     * 
     * @throws Exception caso ocorra erro na inicialização dos arquivos.
     */
    public ControllerEpisode() throws Exception {
        arqEpisodios = new ArquivoEpisode();
        arqSeries = new ArquivoSeries();
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
                    System.out.println("\n>>> Pressione Enter para voltar.");
                    console.nextLine();
                    break;
            }
        } while (opcao != 0);
    }

    /**
     * Realiza a busca de um episódio por nome, dentro de uma série específica.
     * 
     * @return o episódio encontrado ou {@code null} se não encontrado.
     * @throws Exception caso ocorra erro na busca.
     */
    public Episode buscarEpisodioPorNome() throws Exception {
        System.out.println("\n\n===============================");
        System.out.println("       Busca de episódio");
        System.out.println("===============================");
    
        Series serie = buscarSeriePorNome();
        if (serie == null) return null;
    
        int fkSerie = serie.getId();
    
        System.out.println("===============================");
        System.out.println("    Buscar episódio por nome");
        System.out.println("-------------------------------");
        System.out.print("Nome: ");
        String name = console.nextLine();
    
        Episode[] episodios = arqEpisodios.readEpisodiosPorSerieENome(fkSerie, name);
        
        if (episodios == null || episodios.length == 0) {
            System.out.println("-------------------------------");
            System.out.println("Nenhum episódio encontrado.");
            System.out.println("===============================");
            return null;
        }
    
        int n = 1;
        System.out.println("-------------------------------");
        for (Episode e : episodios) {
            System.out.println((n++) + ": " + e.getName());
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
                System.out.println("\n>>> Pressione Enter para voltar.");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível buscar o episódio!");
            e.printStackTrace();
        }
    }    
    

    /**
     * Coleta os dados do usuário e realiza a inclusão de um novo episódio.
     */
    public void incluirEpisodio() {
        System.out.println("\n\n===============================");
        System.out.println("      Inclusão de Episódio");
        System.out.println("===============================");

        // Buscando qual série irá inserir
        Series serie = buscarSeriePorNome();

        if(serie == null){
            System.out.println("-------------------------------");
            System.out.println("Nenhuma série encontrada! Não é possível adicionar episódios.");
            System.out.println("===============================");
            
            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();

            return; // Sai do método para evitar exceções
        }

        int fkSerie = serie.getId();
        System.out.println("-------------------------------");
        System.out.println("Nome da série: " + serie.getName());

        // Inserindo valores
        String name = visaoEpisodios.obterNome();
        if (name == null || name.isEmpty()) {
            System.out.println("Nome inválido. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();
            return;
        }

        int season = visaoEpisodios.obterTemporada();
        if (season <= 0) {
            System.out.println("Temporada inválida. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();
            return;
        }

        LocalDate release = visaoEpisodios.obterDataLancamento();
        if (release == null) {
            System.out.println("Data de lançamento inválida. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();
            return;
        }

        int duration = visaoEpisodios.obterDuracao();
        if (duration <= 0) {
            System.out.println("Duração inválida. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();
            return;
        }

        if (visaoEpisodios.confirmAction(1)) {
            try {
                Episode novoEpisode = new Episode(fkSerie, name, season, release, duration);
                arqEpisodios.create(novoEpisode);
                System.out.println("-------------------------------");
                System.out.println("Episódio incluído com sucesso.");
                System.out.println("===============================");
            } catch (Exception e) {
                System.err.println("Erro do sistema. Não foi possível incluir o episódio!");
                e.printStackTrace();
            }
        } else {
            System.out.println("-------------------------------");
            System.out.println("Inclusão de Episódio cancelada.");
            System.out.println("===============================");
            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();
        }
    }

    /**
     * Altera os dados de um episódio já existente.
     */
    private void alterarEpisodio() {
        System.out.println("\n\n===============================");
        System.out.println("      Alteração de Episódio");
        System.out.println("===============================");
    
        try {
            Episode episode = buscarEpisodioPorNome();
            if (episode == null) {
                System.out.println("-------------------------------");
                System.out.println("Nenhum episódio encontrado.");
                System.out.println("===============================");
                return;
            }
    
            visaoEpisodios.mostraEpisodio(episode);
    
            String novoNome = visaoEpisodios.obterNome();
            if (novoNome != null && !novoNome.isEmpty()) {
                episode.setName(novoNome);
            }
    
            int novaTemporada = visaoEpisodios.obterTemporada();
            if (novaTemporada > 0) {
                episode.setSeason(novaTemporada);
            }
    
            LocalDate novaData = visaoEpisodios.obterDataLancamento();
            if (novaData != null) {
                episode.setRelease(novaData);
            }
    
            int novaDuracao = visaoEpisodios.obterDuracao(); // <-- Este método precisa existir
            if (novaDuracao > 0) {
                episode.setDuration(novaDuracao);
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
    
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível alterar o episódio!");
            e.printStackTrace();
        }
    }    

    /**
     * Exclui um episódio selecionado pelo usuário.
     */
    private void excluirEpisodio() {
        System.out.println("\n\n===============================");
        System.out.println("      Exclusão de Episódio");
        System.out.println("===============================");
    
        try {
            Episode episode = buscarEpisodioPorNome();
            if (episode == null) {
                System.out.println("-------------------------------");
                System.out.println("Nenhum episódio encontrado.");
                System.out.println("===============================");
                System.out.println("\n>>> Pressione Enter para voltar.");
                console.nextLine();
                return;
            }
    
            // Mostra o episódio encontrado
            visaoEpisodios.mostraEpisodio(episode);
    
            // Confirma exclusão
            if (visaoEpisodios.confirmAction(3)) {
                boolean deletado = arqEpisodios.delete(episode.getId());
                if (deletado) {
                    System.out.println("-------------------------------");
                    System.out.println("Episódio excluído com sucesso.");
                } else {
                    System.err.println("Erro ao excluir o episódio.");
                }
            } else {
                System.out.println("-------------------------------");
                System.out.println("Exclusão cancelada.");
            }
    
            System.out.println("===============================");
            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();
    
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível buscar o episódio!");
            e.printStackTrace();
        }
    }
      

    /**
     * Realiza a busca de uma série pelo nome.
     * 
     * @return a série encontrada ou {@code null} se não encontrada.
     */
    public Series buscarSeriePorNome() {
        System.out.println("    Buscar série por nome");
        System.out.println("-------------------------------");
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
                System.out.println("\n>>> Pressione Enter para voltar.");
                console.nextLine();
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
                    System.out.print("Escolha um número entre 1 e " + (n - 1) + ": ");
            } while (o <= 0 || o > n - 1);

            return series[o - 1];

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível buscar as séries!");
            e.printStackTrace();
        }
        return null;
    }
}
