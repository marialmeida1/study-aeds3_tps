package tp02.src.controller;

import tp02.src.data.ArchiveActor;
import tp02.src.data.ArchiveRelationNN;
import tp02.src.data.ArchiveSeries;
import tp02.src.view.ViewActor;
import tp02.src.models.*;

import java.util.ArrayList;
import java.util.Scanner;

import tp02.src.storage.indexes.PairIDFK;

/**
 * Controlador principal para manipulação de ator.
 * Atua como intermediário entre a camada de visualização (ViewActor)
 * e a camada de dados (ArquivoActor e ArquivoEpisode).
 */
public class ControllerActor {

    private ArchiveActor arqAtor;
    private ArchiveRelationNN arqRelationNN;
    private ArchiveSeries arqSeries;
    private ViewActor visao;
    private static final Scanner console = new Scanner(System.in);

    /**
     * Construtor padrão que inicializa os arquivos e a interface de visualização.
     * 
     * @throws Exception caso ocorra erro ao acessar os arquivos.
     */
    public ControllerActor() throws Exception {
        arqAtor = new ArchiveActor();
        arqRelationNN = new ArchiveRelationNN();
        arqSeries = new ArchiveSeries(); // Initialize arqSeries
        visao = new ViewActor();
    }

    /**
     * Exibe o menu principal com as opções de operações sobre ators e episódios.
     * 
     * @throws Exception caso ocorra erro ao executar alguma operação.
     */
    public void menu() throws Exception {
        int opcao;
        do {
            visao.exibirMenu();
            opcao = Integer.valueOf(console.nextLine());

            switch (opcao) {
                case 1:
                    incluirAtor();
                    break;
                case 2:
                    buscarAtor();
                    break;
                case 3:
                    alterarAtor();
                    break;
                case 4:
                    excluirAtor();
                    break;
                case 5:
                    listSeriesByActor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                    break;
            }
        } while (opcao != 0);
    }

    /**
     * Inclui uma nova ator no sistema, coletando os dados por meio da interface
     * de visualização.
     */
    public void incluirAtor() {
        System.out.println("\n\n===================================");
        System.out.println("         Inclusão de ator");
        System.out.println("===================================");
        System.out.println("Início > Atores > Incluir");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String name = visao.obterNome();
        if (name == null || name.isEmpty()) {
            System.out.println("\nNome inválido. Inclusão cancelada.");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        if (visao.confirmAction(1)) {
            try {
                Actor novaAtor = new Actor(name);
                System.out.println("-----------------------------------");
                novaAtor.setId(arqAtor.create(novaAtor));
                visao.mostraAtor(novaAtor);
                System.out.println("\nAtor/atriz incluído com sucesso!");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            } catch (Exception e) {
                System.err.println("Erro do sistema. Não foi possível\nincluir o ator!");
                e.printStackTrace();
            }
        } else {
            System.out.println("-----------------------------------");
            System.out.println("Inclusão cancelada.");

            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
        }
    }

    /**
     * Permite buscar uma ator pelo nome e exibe suas informações detalhadas.
     */
    public void buscarAtor() {
        System.out.println("\n\n===================================");
        System.out.println("      Busca de ator por nome");
        System.out.println("===================================");
        System.out.println("Início > Atores > Buscar");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("Digite o nome do ator: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return;

        try {
            Actor[] atores = arqAtor.readNome(name);

            if (atores == null || atores.length == 0) { // Verifica se é nulo ou vazio
                System.out.println("-----------------------------------");
                System.out.println("\nNenhum ator encontrado.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return; // Sai do método para evitar exceções
            }

            int n = 1;
            System.out.println("-----------------------------------");
            for (Actor a : atores) {
                System.out.println((n++) + ": " + a.getName() + " (ID: " + a.getId() + ")"); // Display actor name and ID
            }

            System.out.println("-----------------------------------");
            System.out.print("Escolha o Ator: ");

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

            visao.mostraAtor(atores[o - 1]);

            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível\nbuscar as atores!");
            e.printStackTrace();
        }
    }

    /**
     * Permite alterar os dados de uma ator previamente cadastrada.
     * Realiza verificação para manter valores antigos caso campos não sejam
     * alterados.
     */
    private void alterarAtor() {
        System.out.println("\n\n===================================");
        System.out.println("      Alteração de Ator");
        System.out.println("===================================");
        System.out.println("Início > Atores > Alterar");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String name = visao.obterNome();
        if (name == null || name.isEmpty())
            return;

        try {
            Actor[] atores = arqAtor.readNome(name);
            if (atores.length > 0) {
                int n = 1;

                System.out.println("-----------------------------------");
                for (Actor s : atores) {
                    System.out.println((n++) + ": " + s.getName());
                }

                System.out.println("-----------------------------------");
                System.out.print("Escolha a ator: ");
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

                Actor atorUpdate = atores[o - 1];
                visao.mostraAtor(atorUpdate);
                
                System.out.println("\n-> Editando:");
                String novoNome = visao.obterNome();
                if (novoNome != null && !novoNome.isEmpty()) {
                    atorUpdate.setName(novoNome);
                } else {
                    novoNome = atorUpdate.getName(); // Mantém o nome antigo
                }

                if (visao.confirmAction(2)) {
                    boolean alterado = arqAtor.update(atorUpdate);
                    if (alterado) {
                        System.out.println("-----------------------------------");
                        System.out.println("Ator alterado com sucesso.");
                        System.out.println("\n>>> Pressione Enter para voltar <<<");
                        console.nextLine();
                    } else {
                        System.err.println("Erro ao alterar o ator.");
                    }
                } else {
                    System.out.println("-----------------------------------");
                    System.out.println("\nAlteração cancelada.");
                    System.out.println("\n>>> Pressione Enter para voltar <<<");
                    console.nextLine();
                }
            } else {
                System.out.println("-----------------------------------");
                System.out.println("\nNenhum ator encontrado.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível alterar o ator!");
            e.printStackTrace();
        }
    }

    /**
     * Exclui um Ator/Atriz do sistema, desde que não haja séries vinculados a
     * ele(a).
     */
    private void excluirAtor() {
        System.out.println("\n\n===================================");
        System.out.println("      Exclusão de Ator/Atriz");
        System.out.println("===================================");
        System.out.println("Início > Atores > Excluir");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("Digite o nome do ator/atriz: ");
        String name = console.nextLine();

        if (name == null || name.isEmpty()){
            System.out.println("\nNome inválido!");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        try {
            Actor[] atores = arqAtor.readNome(name);
            if (atores == null || atores.length == 0) {
                System.out.println("\nNenhum ator encontrado.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            System.out.println("-----------------------------------");
            for (int i = 0; i < atores.length; i++) 
                System.out.println((i + 1) + ": " + atores[i].getName());
            
            System.out.println("-----------------------------------");
            System.out.print("Escolha o Ator: ");
            int escolha = Integer.parseInt(console.nextLine());
            Actor ator = atores[escolha - 1];

            System.err.println();
            ArrayList<PairIDFK> relations = arqRelationNN.readSeriesByActor(ator.getId());
            if (relations != null && !relations.isEmpty()) {
                System.out.println("Erro! Não é possível excluir o ator,\npois ele está vinculado a uma ou mais\nséries.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            if (visao.confirmAction(3)) {
                System.out.println("-----------------------------------");
                if (arqAtor.delete(ator.getId())) {
                    System.out.println("\nAtor excluído com sucesso.");
                } else {
                    System.err.println("\nErro ao excluir o ator.");
                }

                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            } else {
                System.out.println("-----------------------------------");
                System.out.println("\nExclusão cancelada.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir o ator.");
            e.printStackTrace();
        }
    }

    private void listSeriesByActor() {
        /* System.out.println("\n\n===================================");
        System.out.println("      Busca de ator por nome");
        System.out.println("==================================="); */

        System.out.print("\nDigite o nome do ator/atriz: ");
        String nomeAtor = console.nextLine();

        if (nomeAtor.isEmpty()) {
            System.out.println("\nNome do ator/atriz inválido(a).");
            System.out.println("\n>>> Pressione Enter para voltar <<<");
            console.nextLine();
            return;
        }

        try {
            Actor[] actors = arqAtor.readNome(nomeAtor); // Fetch actor by name
            if (actors == null || actors.length == 0) {
                System.out.println("\nNenhum ator/atriz encontrado(a) com o\nnome fornecido.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            }

            int n = 1;
            System.out.println("-----------------------------------");
            for (Actor a : actors) {
                System.out.println((n++) + ": " + a.getName());
            }

            System.out.println("-----------------------------------");
            System.out.print("Escolha o ator/atriz: ");

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

            Actor actor = actors[escolha - 1]; // Use the selected actor
            System.out.println("\n===================================");            
            if(actor.getName().length() < 23){
                System.out.println(actor.getName() + ": Filmografia");
            } else {
                System.out.println(actor.getName() + ":\nFilmografia");
            }
            System.out.println("==================================="); 
            System.out.println("Início > Atores > Filmografia");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            int idActor = actor.getId();

            ArrayList<PairIDFK> relations = arqRelationNN.readSeriesByActor(idActor); // Fetch series for the actor

            if (relations == null || relations.isEmpty()) {
                System.out.println("Nenhuma série encontrada para este\nator/atriz.");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
                return;
            } else {
                for (PairIDFK relation : relations) {
                    int idSerie = relation.getFk(); // Fetch series ID
                    Series serie = arqSeries.read(idSerie);
                    System.out.println("-----------------------------------");
                    if (serie != null) {
                        System.out.println("Nome: " + serie.getName());
                        System.out.println("Sinopse: " + serie.getSynopsis());
                        System.out.println("Ano de lançamento: " + serie.getReleaseYear());
                        System.out.println("Streaming: " + serie.getStreaming());
                    } else {
                        System.out.println("Erro: Série com ID " + idSerie + " não encontrada."); // Debug statement
                        System.out.println("\n>>> Pressione Enter para voltar <<<");
                        console.nextLine();
                        return;
                    }
                }
                System.out.println("-----------------------------------");
                System.out.println("\n>>> Pressione Enter para voltar <<<");
                console.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar Séries que o(a)\nator/atriz fez!");
            e.printStackTrace();
        }
    }
}
