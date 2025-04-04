package tp01.src;

import java.util.Scanner;
import tp01.src.controller.*;

public class Main {

    public static void main(String[] args) {

        Scanner console;

        try {
            console = new Scanner(System.in);
            int opcao;
            do {

                System.out.println("\n\nPUCFlix 1.0");
                System.out.println("-------");
                System.out.println("> Início");
                System.out.println("1 - Séries");
                System.out.println("2 - Episódios");
                System.out.println("0 - Sair");

                System.out.print("\nOpção: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        (new ControllerSerie()).menu();
                        /* System.out. println("Menu Séries"); */
                        break;
                    case 2:
                        (new ControllerEpisodio()).menu();
                        /* System.out.println("Menu Episódios"); */
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}