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
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    private void buscarSerie() {
        String nome = visao.obterNome();
        try {
            Serie serie = arqSeries.read(nome);
            if (serie != null) {
                visao.mostraSerie(serie);
            } else {
                System.out.println("Série não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar a série!");
            e.printStackTrace();
        }
    }

    // Funcionando
    private void incluirSerie() {
        String nome = visao.obterNome();
        if (nome == null) return;

        String sinopse = visao.obterSinopse();
        int episodes = visao.obterQuantidadeEpisodios();
        short releaseYear = visao.obterAnoLancamento();
        String streaming = visao.obterStreaming();

        if (visao.confirmAction(1)) {
            try {
                Serie serie = new Serie(nome, sinopse, episodes, releaseYear, streaming, -1);
                arqSeries.create(serie);
                System.out.println("Série incluída com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao incluir a série!");
            }
        }
    }

    private void alterarSerie() throws Exception {
        String nome = visao.obterNome();
        Serie serie = arqSeries.read(nome);
        if (serie != null) {
            visao.mostraSerie(serie);

            String novoNome = visao.obterNome();
            if (novoNome != null) serie.nome = novoNome;

            String novaSinopse = visao.obterSinopse();
            if (novaSinopse != null) serie.sinopse = novaSinopse;

            int novosEpisodios = visao.obterQuantidadeEpisodios();
            serie.episodes = novosEpisodios;

            short novoAno = visao.obterAnoLancamento();
            serie.releaseYear = novoAno;

            String novoStreaming = visao.obterStreaming();
            if (novoStreaming != null) serie.streaming = novoStreaming;

            if (visao.confirmAction(2)) {
                boolean alterado = arqSeries.update(serie);
                if (alterado) {
                    System.out.println("Série alterada com sucesso.");
                } else {
                    System.out.println("Erro ao alterar a série.");
                }
            } else {
                System.out.println("Alterações canceladas.");
            }
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    private void excluirSerie() throws Exception {
        String nome = visao.obterNome();
        Serie serie = arqSeries.read(nome);
        if (serie != null) {
            visao.mostraSerie(serie);
            if (visao.confirmAction(3)) {
                boolean excluido = arqSeries.delete(nome);
                if (excluido) {
                    System.out.println("Série excluída com sucesso.");
                } else {
                    System.out.println("Erro ao excluir a série.");
                }
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } else {
            System.out.println("Série não encontrada.");
        }
    }
}
