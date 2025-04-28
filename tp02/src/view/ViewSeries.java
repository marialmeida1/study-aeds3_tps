package tp01.src.view;

import tp01.src.models.Series;

import java.util.Scanner;

/**
 * Classe responsável pela interação com o usuário para operações relacionadas a séries.
 * 
 * Permite exibir menus, capturar dados de entrada e confirmar ações relacionadas a séries.
 */
public class ViewSeries {
    private static final Scanner console = new Scanner(System.in);

    /**
     * Exibe o menu principal de opções para manipulação de séries.
     */
    public void exibirMenu() {
        System.out.println("\n\n===============================");
        System.out.println("          PUCFlix 1.0");
        System.out.println("===============================");
        System.out.println("> Início > Séries");
        System.out.println("-------------------------------");
        System.out.println("1 - Incluir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Alterar");
        System.out.println("4 - Excluir");
        System.out.println("5 - Listar Episódios da série");
        System.out.println("6 - Listar Episódios por temporada da série");
        System.out.println("0 - Retornar ao menu anterior");
        System.out.println("===============================");
        System.out.print("Opção: ");
    }

    /**
     * Exibe os detalhes de uma série na tela.
     *
     * @param serie Objeto da série a ser exibido.
     */
    public void mostraSerie(Series serie) {
        if (serie != null) {
            System.out.println("\n\n===============================");
            System.out.println("       Detalhes da Série:");
            System.out.println("===============================");
            System.out.printf("Nome.................: %s%n", serie.getName());
            System.out.printf("Sinopse..............: %s%n", serie.getSynopsis());
            System.out.printf("Ano de Lançamento....: %d%n", serie.getReleaseYear());
            System.out.printf("Streaming............: %s%n", serie.getStreaming());
            System.out.println("===============================");
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    /**
     * Solicita ao usuário o nome da série.
     *
     * @return Nome da série informado.
     */
    public String obterNome() {
        System.out.print("Nome da Série: ");
        return console.nextLine();
    }

    /**
     * Solicita ao usuário a sinopse da série.
     *
     * @return Sinopse informada.
     */
    public String obterSinopse() {
        System.out.print("Sinopse: ");
        return console.nextLine();
    }

    /**
     * Solicita ao usuário o ano de lançamento da série.
     *
     * @return Ano de lançamento como short, ou -1 se a entrada estiver vazia.
     */
    public short obterAnoLancamento() {
        System.out.print("Ano de Lançamento: ");
        String entrada = console.nextLine();

        if (entrada.isEmpty()) {
            return -1; // Retorna um valor indicando que o usuário não digitou nada
        }

        try {
            return Short.parseShort(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Digite um ano válido.");
            return obterAnoLancamento(); // Pede a entrada novamente em caso de erro
        }
    }

    /**
     * Solicita ao usuário o serviço de streaming da série.
     *
     * @return Nome do serviço de streaming informado.
     */
    public String obterStreaming() {
        System.out.print("Streaming: ");
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
                System.out.println("-------------------------------");
                mensagem = "Confirma a inclusão? (S/N) ";
                break;
            case 2:
                System.out.println("-------------------------------");
                mensagem = "Confirma as alterações? (S/N) ";
                break;
            case 3:
                System.out.println("-------------------------------");
                mensagem = "Confirma a exclusão da série? (S/N) "; // Corrigido para "série"
                break;
            default:
                System.out.println("-------------------------------");
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
                System.out.println("Resposta inválida! Por favor, digite 'S' para Sim ou 'N' para Não.");
            }
        }
    }
}
