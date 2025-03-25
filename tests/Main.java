package tests;

import tests.models.TestSerie;
import tests.data.TestArquivoSerie;
import tests.controller.TestControllerSerie;
import tests.view.TestViewSerie;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int opcao;

        try {
            do {
                System.out.println("\n\nPUCFlix Test Suite");
                System.out.println("-------------------");
                System.out.println("1 - Test Serie Model");
                System.out.println("2 - Test ArquivoSerie");
                System.out.println("3 - Test ControllerSerie");
                System.out.println("4 - Test ViewSerie");
                System.out.println("0 - Exit");

                System.out.print("\nOption: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        TestSerie.main(null);
                        break;
                    case 2:
                        TestArquivoSerie.main(null);
                        break;
                    case 3:
                        TestControllerSerie.main(null);
                        break;
                    case 4:
                        TestViewSerie.main(null);
                        break;
                    case 0:
                        System.out.println("Exiting test suite...");
                        break;
                    default:
                        System.out.println("Invalid option!");
                        break;
                }
            } while (opcao != 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            console.close();
        }
    }
}
