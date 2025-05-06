package tp02.src.view;

import tp02.src.models.Actor;

import java.util.Scanner;

/**
 * Classe responsável pela interação com o usuário para operações relacionadas a atores.
 * 
 * Permite exibir menus, capturar dados de entrada e confirmar ações relacionadas a atores.
 */
public class ViewActor {
    private static final Scanner console = new Scanner(System.in);

    /**
     * Exibe o menu principal de opções para manipulação de atores.
     */
    public void exibirMenu() {
        System.out.println("\n\n===================================");
        System.out.println("            PUCFlix 1.0");
        System.out.println("===================================");
        System.out.println("Início > Atores");
        System.out.println("-----------------------------------");
        System.out.println("1 - Incluir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Alterar");
        System.out.println("4 - Excluir");
        System.out.println("5 - Listar Séries de um ator/atriz");
        System.out.println("0 - Retornar ao menu anterior");
        System.out.println("===================================");
        System.out.print("Opção: ");
    }

    /**
     * Exibe os detalhes de uma ator na tela.
     *
     * @param ator Objeto da ator a ser exibido.
     */
    public void mostraAtor(Actor ator) {
        if (ator != null) {
            System.out.println("\n\n===================================");
            System.out.println("      Detalhes do Ator/Atriz:");
            System.out.println("===================================");
            System.out.printf("Nome...... %s%n", ator.getName());
            System.out.printf("ID........ %s%n", ator.getId());
            System.out.println("===================================");
        } else {
            System.out.println("Ator/atriz não encontrado(a).");
        }
    }

    /**
     * Solicita ao usuário o nome da ator.
     *
     * @return Nome da ator informado.
     */
    public String obterNome() {
        System.out.print("Nome do ator/atriz: ");
        return console.nextLine();
    }

    /**
     * Solicita a confirmação do usuário antes de realizar uma ação.
     *
     * @param actionNum Código da ação: 1 - Incluir, 2 - Alterar, 3 - Excluir, outros - genérico.
     * @return {@code true} se o usuário confirmar com 'S', {@code false} se responder 'N'.
     */
    public boolean confirmAction(int actionNum) {
        String mensagem;

        switch (actionNum) {
            case 1:
                System.out.println("-----------------------------------");
                mensagem = "Confirma a inclusão? (S/N) ";
                break;
            case 2:
                System.out.println("-----------------------------------");
                mensagem = "Confirma as alterações? (S/N) ";
                break;
            case 3:
                System.out.println("\n-----------------------------------");
                mensagem = "Confirma a exclusão de ator? (S/N) ";
                break;
            default:
                System.out.println("-----------------------------------");
                mensagem = "Confirma a ação? (S/N) ";
                break;
        }

        while (true) {
            System.out.print(mensagem);
            String resposta = console.nextLine().trim().toUpperCase();

            if (resposta.equalsIgnoreCase("S")) {
                return true;
            } else if (resposta.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Resposta inválida! Por favor, digite\n'S' para Sim ou 'N' para Não.");
            }
        }
    }
}
