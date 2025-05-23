package tp02.src;

import java.util.Scanner;
import tp02.src.controller.*;

/**
 * Classe principal do sistema PUCFlix.
 * 
 * Responsável por exibir o menu principal da aplicação e direcionar o usuário
 * para os módulos de Séries ou Episódios.
 */
public class Main {

    /**
     * Método principal da aplicação.
     * 
     * Exibe um menu de opções para o usuário e redireciona para os controladores de
     * séries ou episódios
     * conforme a escolha do usuário. O programa continua em execução até que a
     * opção de saída seja escolhida.
     *
     * @param args Argumentos passados por linha de comando (não utilizados).
     */
    public static void main(String[] args) {

        Scanner console;

        try {
            console = new Scanner(System.in);
            int opcao;
            do {

                System.out.println("\n\n===================================");
                System.out.println("            PUCFlix 1.0");
                System.out.println("===================================");
                System.out.println("Início");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                System.out.println("1 - Séries");
                System.out.println("2 - Episódios");
                System.out.println("3 - Ator");
                System.out.println("0 - Sair");
                System.out.println("===================================");
                System.out.print("Opção: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        (new ControllerSeries()).menu();
                        break;
                    case 2:
                        (new ControllerEpisode()).menu();
                        break;
                    case 3:
                        (new ControllerActor()).menu();
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}