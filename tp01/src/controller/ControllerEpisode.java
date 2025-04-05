package tp01.src.controller;

import tp01.src.models.*;
import tp01.src.data.ArquivoEpisodio;
import tp01.src.view.ViewEpisodio;

import java.time.LocalDate;
import java.util.Scanner;

public class ControllerEpisodio {

    private ArquivoEpisodio arqEpisodios;
    private ViewEpisodio visao;
    private static final Scanner console = new Scanner(System.in);

    public ControllerEpisodio() throws Exception {
        arqEpisodios = new ArquivoEpisodio();
        visao = new ViewEpisodio();
    }

    public void menu() throws Exception {
        int opcao;
        do {
            visao.exibirMenu();
            opcao = Integer.valueOf(console.nextLine());

            switch (opcao) {
                case 1:
                    /*incluirEpisodio();*/
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

    private void buscarEpisodio() throws Exception {
        
        int fkSerie = visao.lerSerie();
        int idEpisodio = visao.lerEpisodio();

        try {
            Episodio episodio = arqEpisodios.read(idEpisodio, fkSerie);
            if (episodio != null) {
                visao.mostraEpisodio(episodio);
            } else {
                System.out.println("Episódio não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar o episódio!");
            e.printStackTrace();
        }
    }

    /*private void incluirEpisodio() throws Exception {

        // Lê o nome da série e retorna a série para pegar o id
        String nomeSerie = visao.obterNomeSerie();
        Serie[] series = arqSeries.readNome(nomeSerie);
        Serie serie = (series != null && series.length > 0) ? series[0] : null;

        if(serie == null) { // Verifica se encontrou
            System.out.println("Erro: Série não encontrada!");
            return;
        }

        // Dados básicos
        String nome = visao.obterNome();
        if (nome.isEmpty())
            return;

        int temporada = visao.obterTemporada();
        LocalDate dataLancamento = visao.obterDataLancamento();
        int duracao = visao.obterDuracao();

        // Confirmar a inclusão
        if (visao.confirmAction(1)) {
            try {
                Episodio episodio = new Episodio(serie.id, nome, temporada, dataLancamento, duracao);
                arqEpisodios.create(episodio);
                System.out.println("Episódio incluído com sucesso.");
            } catch (Exception e) {
                System.err.println("Erro ao incluir o episódio!");
            }
        }
    }*/

    private void alterarEpisodio() throws Exception {
        int fkSerie = visao.lerSerie();
        int idEpisodio = visao.lerEpisodio();

        Episodio episodio = arqEpisodios.read(idEpisodio, fkSerie);
        if (episodio != null) {
            visao.mostraEpisodio(episodio);

            String novoNome = visao.obterNome();
            if (!novoNome.isEmpty())
                episodio.nome = novoNome;

            int novaTemporada = visao.obterTemporada();
            episodio.temporada = novaTemporada;

            LocalDate novaDataLancamento = visao.obterDataLancamento();
            episodio.lancamento = novaDataLancamento;

            int novaDuracao = visao.obterDuracao();
            episodio.duracao = novaDuracao;

            if (visao.confirmAction(2)) {
                boolean alterado = arqEpisodios.update(episodio);
                if (alterado) {
                    System.out.println("Episódio alterado com sucesso.");
                } else {
                    System.err.println("Erro ao alterar o cliente.");
                }
            } else {
                System.out.println("Alterações canceladas.");
            }
        } else {
            System.out.println("Episódio não encontrado.");
        }
    }

    private void excluirEpisodio() throws Exception {
        int fkSerie = visao.lerSerie();
        int idEpisodio = visao.lerEpisodio();

        Episodio episodio = arqEpisodios.read(idEpisodio, fkSerie);
        if (episodio != null) {
            visao.mostraEpisodio(episodio);
            if (visao.confirmAction(3)) {
                boolean excluido = arqEpisodios.delete(idEpisodio, fkSerie);
                if (excluido) {
                    System.out.println("Episódio excluído com sucesso.");
                } else {
                    System.err.println("Erro ao excluir o episódio.");
                }
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } else {
            System.out.println("Episódio não encontrado.");
        }
    }
}
