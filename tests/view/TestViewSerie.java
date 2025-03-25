package tests.view;

import tp01.src.models.Serie;
import tp01.src.view.ViewSerie;

public class TestViewSerie {
    public static void main(String[] args) {
        ViewSerie view = new ViewSerie();

        // Simulate user input
        String nome = view.obterNome();
        String sinopse = view.obterSinopse();
        int episodes = view.obterQuantidadeEpisodios();
        short releaseYear = view.obterAnoLancamento();
        String streaming = view.obterStreaming();

        // Display the collected data
        Serie serie = new Serie(nome, sinopse, episodes, releaseYear, streaming, -1);
        view.mostraSerie(serie);

        // Confirm alterations
        boolean confirmed = view.confirmarAlteracoes();
        System.out.println("\nAlterations confirmed: " + confirmed);
    }
}
