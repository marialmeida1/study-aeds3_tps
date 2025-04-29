package tp02.src.controller;

import tp02.src.data.ArchiveActor;
import tp02.src.view.ViewActor;
import tp02.src.models.*;

import java.util.Scanner;

/**
 * Controlador principal para manipulação de ator.
 * Atua como intermediário entre a camada de visualização (ViewActor)
 * e a camada de dados (ArquivoActor e ArquivoEpisode).
 */
public class ControllerActor {

    private ArchiveActor arqAtor;
    private ViewActor visao;
    private static final Scanner console = new Scanner(System.in);

    /**
     * Construtor padrão que inicializa os arquivos e a interface de visualização.
     * @throws Exception caso ocorra erro ao acessar os arquivos.
     */
    public ControllerActor() throws Exception {
        arqAtor = new ArchiveActor();
        visao = new ViewActor();
    }

    /**
     * Exibe o menu principal com as opções de operações sobre ators e episódios.
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
     * Permite buscar uma ator pelo nome e exibe suas informações detalhadas.
     */
    public void buscarAtor() {
        System.out.println("\n\n===============================");
        System.out.println("    Busca de ator por nome");
        System.out.println("===============================");
        System.out.print("Digite o nome da ator: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return;

        try {
            Actor[] atores = arqAtor.readNome(name);

            if (atores == null || atores.length == 0) { // Verifica se é nulo ou vazio
                System.out.println("-------------------------------");
                System.out.println("Nenhum ator encontrado.");
                System.out.println("===============================");
                System.out.println("\n>>> Pressione Enter para voltar.");
                console.nextLine();
                return; // Sai do método para evitar exceções
            }

            int n = 1;
            System.out.println("-------------------------------");
            for (Actor a : atores) {
                System.out.println((n++) + ": " + a.getName());
            }

            System.out.println("-------------------------------");
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

            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();

        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível buscar as ators!");
            e.printStackTrace();
        }

    }

    /**
     * Inclui uma nova ator no sistema, coletando os dados por meio da interface de visualização.
     */
    public void incluirAtor() {
        System.out.println("\n\n===============================");
        System.out.println("      Inclusão de ator");
        System.out.println("===============================");

        String name = visao.obterNome();
        if (name == null || name.isEmpty()) {
            System.out.println("Nome inválido. Inclusão cancelada.");
            return;
        }

        if (visao.confirmAction(1)) {
            try {
                Actor novaAtor = new Actor(name);
                arqAtor.create(novaAtor);
                System.out.println("-------------------------------");
                System.out.println("Ator incluído com sucesso.");
                System.out.println("===============================");

                visao.mostraAtor(novaAtor);
                System.out.println("\n>>> Pressione Enter para voltar.");
                console.nextLine();
            } catch (Exception e) {
                System.err.println("Erro do sistema. Não foi possível incluir o ator!");
                e.printStackTrace();
            }
        } else {
            System.out.println("-------------------------------");
            System.out.println("Inclusão cancelada.");
            System.out.println("===============================");

            System.out.println("\n>>> Pressione Enter para voltar.");
            console.nextLine();
        }
    }

    /**
     * Permite alterar os dados de uma ator previamente cadastrada.
     * Realiza verificação para manter valores antigos caso campos não sejam alterados.
     */
    private void alterarAtor() {
        System.out.println("\n\n===============================");
        System.out.println("      Alteração de Ator");
        System.out.println("===============================");

        String name = visao.obterNome();
        if (name == null || name.isEmpty())
            return;

        try {
            Actor[] atores = arqAtor.readNome(name);
            if (atores.length > 0) {
                int n = 1;

                System.out.println("-------------------------------");
                for (Actor s : atores) {
                    System.out.println((n++) + ": " + s.getName());
                }

                System.out.println("-------------------------------");
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

                Actor serie = atores[o - 1];
                visao.mostraAtor(serie);

                String novoNome = visao.obterNome();
                if (novoNome != null && !novoNome.isEmpty()) {
                    serie.setName(novoNome);
                } else {
                    novoNome = serie.getName(); // Mantém o nome antigo
                }

                if (visao.confirmAction(2)) {
                    boolean alterado = arqAtor.update(serie);
                    if (alterado) {
                        System.out.println("-------------------------------");
                        System.out.println("Ator alterado com sucesso.");
                        System.out.println("===============================");
                        System.out.println("\n>>> Pressione Enter para voltar.");
                        console.nextLine();
                    } else {
                        System.err.println("Erro ao alterar o ator.");
                    }
                } else {
                    System.out.println("-------------------------------");
                    System.out.println("Alteração cancelada.");
                    System.out.println("===============================");
                    System.out.println("\n>>> Pressione Enter para voltar.");
                    console.nextLine();
                }
            } else {
                System.out.println("-------------------------------");
                System.out.println("Nenhum ator encontrado.");
                System.out.println("===============================");
                System.out.println("\n>>> Pressione Enter para voltar.");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível alterar o ator!");
            e.printStackTrace();
        }
    }

    /**
     * Exclui uma ator do sistema, desde que não haja episódios vinculados a ela.
     */
    private void excluirAtor() {
        System.out.println("\n\n===============================");
        System.out.println("      Exclusão de Ator");
        System.out.println("===============================");
        System.out.print("Digite o nome do ator: ");
        String name = console.nextLine();

        if (name.isEmpty())
            return;

        try {
            Actor[] atores = arqAtor.readNome(name);
            if (atores.length == 0) {
                System.out.println("-------------------------------");
                System.out.println("Nenhum ator encontrado.");
                System.out.println("===============================");
                System.out.println("\n>>> Pressione Enter para voltar.");
                console.nextLine();
                return;
            }

            System.out.println("-------------------------------");
            for (int i = 0; i < atores.length; i++) {
                System.out.println((i + 1) + ": " + atores[i].getName());
            }

            int escolha;
            do {
                System.out.println("-------------------------------");
                System.out.print("Escolha a ator: ");
                try {
                    escolha = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    escolha = -1;
                }
                if (escolha < 1 || escolha > atores.length) {
                    System.out.print("Escolha um número entre 1 e " + atores.length + ": ");
                }
            } while (escolha < 1 || escolha > atores.length);

            Actor serie = atores[escolha - 1];
            visao.mostraAtor(serie);

            if (visao.confirmAction(3)) {
                if (arqAtor.delete(serie.getId())) {
                    System.out.println("-------------------------------");
                    System.out.println("Ator excluída com sucesso.");
                    System.out.println("===============================");
                    System.out.println("\n>>> Pressione Enter para voltar.");
                    console.nextLine();
                } else {
                    System.err.println("Erro ao excluir a ator.");
                }
            } else {
                System.out.println("-------------------------------");
                System.out.println("Exclusão cancelada.");
                System.out.println("===============================");
                System.out.println("\n>>> Pressione Enter para voltar.");
                console.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Erro do sistema. Não foi possível excluir a ator!");
            e.printStackTrace();
        }
    }
}
