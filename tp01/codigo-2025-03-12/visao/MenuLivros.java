package visao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import entidades.Livro;
import modelo.ArquivoLivros;

public class MenuLivros {
    
    ArquivoLivros arqLivros;
    private static Scanner console = new Scanner(System.in);

    public MenuLivros() throws Exception {
        arqLivros = new ArquivoLivros();
    }

    public void menu() {

        int opcao;
        do {

            System.out.println("\n\nPUCBook 1.0");
            System.out.println( "-----------");
            System.out.println("> Início > Livros");
            System.out.println("\n1 - Buscar por ISBN");
            System.out.println("2 - Buscar por Título");
            System.out.println("3 - Incluir");
            System.out.println("4 - Alterar");
            System.out.println("5 - Excluir");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarLivroISBN();
                    break;
                case 2:
                    buscarLivrosTitulo();
                    break;
                case 3:
                    incluirLivro();
                    break;
                case 4:
                    alterarLivro();
                    break;
                case 5:
                    excluirLivro();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void buscarLivroISBN() {
        System.out.println("\nBusca de livro por ISBN");
        String isbn;
        boolean dadosCorretos = false;

        do {
            System.out.print("\nISBN (13 dígitos): ");
            isbn = console.nextLine();  // Lê o ISBN digitado pelo usuário

            if(isbn.isEmpty())
                return; 

            // Validação do ISBN (13 dígitos e composto apenas por números)
            if (Livro.isValidISBN13(isbn)) 
                dadosCorretos = true;  // ISBN válido
            else
                System.out.println("ISBN inválido. O ISBN deve conter exatamente 13 dígitos numéricos, sem pontos e traços.");
        } while (!dadosCorretos);

        try {
            Livro livro = arqLivros.readISBN(isbn);  // Chama o método de leitura da classe Arquivo
            if (livro != null) {
                mostraLivro(livro);  // Exibe os detalhes do livro encontrado
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar o livro!");
            e.printStackTrace();
        }
    }   

    public void buscarLivrosTitulo() {
        System.out.println("\nBusca de livro por título");
        System.out.print("\nTítulo: ");
        String titulo = console.nextLine();  // Lê o título digitado pelo usuário

        if(titulo.isEmpty())
            return; 

        try {
            Livro[] livros = arqLivros.readTitulo(titulo);  // Chama o método de leitura da classe Arquivo
            if (livros.length>0) {
                int n=1;
                for(Livro l : livros) {
                    System.out.println((n++)+": "+l.getTitulo());
                }
                System.out.print("Escolha o livro: ");
                int o;
                do { 
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
                mostraLivro(livros[o-1]);  // Exibe os detalhes do livro encontrado
            } else {
                System.out.println("Nenhum livro encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar os livros!");
            e.printStackTrace();
        }
    }   


    public void incluirLivro() {
        System.out.println("\nInclusão de livro");
        String isbn = "";
        String titulo = "";
        String autor = "";
        int edicao = 0;
        LocalDate dataLancamento = null;
        float preco = 0;
        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dadosCorretos = false;
        do {
            System.out.print("ISBN (13 dígitos sem pontos ou traço. Deixe vazio para cancelar.): ");
            isbn = console.nextLine();
            if(isbn.length()==0)
                return;            
            if(Livro.isValidISBN13(isbn))
                dadosCorretos = true;
            else
                System.err.println("O ISBN deve ter exatamente 13 dígitos.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Título (min. de 4 letras): ");
            titulo = console.nextLine();
            if(titulo.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O título do livro deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Autor (min. de 4 letras): ");
            autor = console.nextLine();
            if(autor.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O nome do autor deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Edição: ");
            if (console.hasNextInt()) {
                edicao = console.nextInt();
                if(edicao < 128)
                    dadosCorretos = true;
            }
            if(!dadosCorretos)
                System.err.println("Edição inválida! Por favor, insira um número válido entre 1 e 127.");
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Data de lançamento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            try {
                dataLancamento = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Preço: ");
            if (console.hasNextFloat()) {
                preco = console.nextFloat();
                dadosCorretos = true;
            } else {
                System.err.println("Preço inválido! Por favor, insira um número válido.");
            }
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);

        System.out.print("\nConfirma a inclusão da livro? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Livro c = new Livro(isbn, titulo, autor, edicao, dataLancamento, preco);
                arqLivros.create(c);
                System.out.println("Livro incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o livro!");
            }
        }
    }

    public void alterarLivro() {
        System.out.println("\nAlteração de livro");
        String isbn;
        boolean dadosCorretos;

        dadosCorretos = false;
        do {
            System.out.print("\nISBN (13 dígitos): ");
            isbn = console.nextLine();  // Lê o ISBN digitado pelo usuário

            if(isbn.isEmpty())
                return; 

            // Validação do ISBN (13 dígitos e composto apenas por números)
            if (Livro.isValidISBN13(isbn)) 
                dadosCorretos = true;  // ISBN válido
            else 
                System.out.println("ISBN inválido. O ISBN deve conter exatamente 13 dígitos numéricos, sem pontos e traços.");
        } while (!dadosCorretos);


        try {
            // Tenta ler o livro com o ID fornecido
            Livro livro = arqLivros.readISBN(isbn);
            if (livro != null) {
                mostraLivro(livro);  // Exibe os dados do livro para confirmação

                // Alteração de ISBN
                String novoIsbn;
                dadosCorretos = false;
                do {
                    System.out.print("Novo ISBN (deixe em branco para manter o anterior): ");
                    novoIsbn = console.nextLine();
                    if(!novoIsbn.isEmpty()) {
                        if(Livro.isValidISBN13(novoIsbn)) {
                            livro.setIsbn(novoIsbn);  // Atualiza o ISBN se fornecido
                            dadosCorretos = true;
                        } else 
                            System.err.println("O ISBN deve ter exatamente 13 dígitos.");
                    } else 
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de titulo
                String novoTitulo;
                dadosCorretos = false;
                do {
                    System.out.print("Novo título (deixe em branco para manter o anterior): ");
                    novoTitulo = console.nextLine();
                    if(!novoTitulo.isEmpty()) {
                        if(novoTitulo.length()>=4) {
                            livro.setTitulo(novoTitulo);  // Atualiza o título se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O título do livro deve ter no mínimo 4 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de autor
                String novoAutor;
                dadosCorretos = false;
                do {
                    System.out.print("Novo autor (deixe em branco para manter o anterior): ");
                    novoAutor = console.nextLine();
                    if(!novoAutor.isEmpty()) {
                        if(novoAutor.length()>=4) {
                            livro.setAutor(novoAutor);  // Atualiza o título se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O nome do autor deve ter no mínimo 4 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração da edição
                String novaEdicao;
                dadosCorretos = false;
                do {
                    System.out.print("Nova edição (deixe em branco para manter a anterior): ");
                    novaEdicao = console.nextLine();
                    if(!novaEdicao.isEmpty()) {
                        try {
                            int edicao = Integer.parseInt(novaEdicao);
                            if(edicao>0 && edicao<128) {
                                livro.setEdicao((byte)edicao);  // Atualiza a edição, se fornecida
                                dadosCorretos = true;
                            } else
                                 System.err.println("A edição deve ser um número entre 1 e 127.");
                        } catch(NumberFormatException e) {
                            System.err.println("Número de edição inválido! Por favor, insira um número válido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de preço
                String novoPreco;
                dadosCorretos = false;
                do {
                    System.out.print("Novo preço (deixe em branco para manter o anterior): ");
                    novoPreco = console.nextLine();
                    if(!novoPreco.isEmpty()) {
                        try {
                            livro.setPreco(Float.parseFloat(novoPreco));  // Atualiza o preço, se fornecido
                            dadosCorretos = true;
                        } catch(NumberFormatException e) {
                            System.err.println("Preço inválido! Por favor, insira um número válido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);


                // Alteração de data de lançamento
                String novaData;
                dadosCorretos = false;
                do {
                    System.out.print("Nova data de lançamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                    novaData = console.nextLine();
                    if (!novaData.isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            livro.setDataLancamento(LocalDate.parse(novaData, formatter));  // Atualiza a data de lançamento se fornecida
                        } catch (Exception e) {
                            System.err.println("Data inválida. Valor mantido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arqLivros.update(livro);
                    if (alterado) {
                        System.out.println("Livro alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o livro.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
                 console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o livro!");
            e.printStackTrace();
        }
        
    }


    public void excluirLivro() {
        System.out.println("\nExclusão de livro");
        String isbn;
        boolean dadosCorretos = false;

        do {
            System.out.print("\nISBN (13 dígitos): ");
            isbn = console.nextLine();  // Lê o ISBN digitado pelo usuário

            if(isbn.isEmpty())
                return; 

            // Validação do ISBN (13 dígitos e composto apenas por números)
            if (Livro.isValidISBN13(isbn)) 
                dadosCorretos = true;  // ISBN válido
            else 
                System.out.println("ISBN inválido. O ISBN deve conter exatamente 13 dígitos numéricos, sem pontos e traços.");
        } while (!dadosCorretos);

        try {
            // Tenta ler o livro com o ID fornecido
            Livro livro = arqLivros.readISBN(isbn);
            if (livro != null) {
                System.out.println("Livro encontrado:");
                mostraLivro(livro);  // Exibe os dados do livro para confirmação

                System.out.print("\nConfirma a exclusão do livro? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqLivros.delete(isbn);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Livro excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o livro.");
                    }
                    
                } else {
                    System.out.println("Exclusão cancelada.");
                }
                console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o livro!");
            e.printStackTrace();
        }
    }

    public void mostraLivro(Livro livro) {
        if (livro != null) {
            System.out.println("----------------------");
            System.out.printf("ISBN......: %s%n", livro.getISBN());
            System.out.printf("Título....: %s%n", livro.getTitulo());
            System.out.printf("Autor.....: %s%n", livro.getAutor());
            System.out.printf("Edição....: %s%n", livro.getEdicao());
            System.out.printf("Lançamento: %s%n", livro.getDataLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("Preço.....: R$ %.2f%n", livro.getPreco());
            System.out.println("----------------------");
        }
    }

    public void povoar() throws Exception {
        arqLivros.create(new Livro("9788595159907", "Algoritmos: Teoria e Prática", "Thomas H. Cormen", 4, LocalDate.of(2024,2,6), 416.52F));
        arqLivros.create(new Livro("9788575225639", "Entendendo Algoritmos", "Aditya Y. Bhargava", 1, LocalDate.of(2017, 4, 24),  51.30F));
        arqLivros.create(new Livro("9788573933758", "Estruturas de Dados e Algoritmos em Java", "Lafore", 2, LocalDate.of(2005,1,1), 119.42F));
        arqLivros.create(new Livro("9788543004792", "Java: Como programar", "Paul Deitel", 10, LocalDate.of(2016,6,24), 365.25F));
        arqLivros.create(new Livro("9788575228418", "Python Para Análise de Dados", "Wes McKinney", 3, LocalDate.of(2023, 3, 16), 122.55F));
        arqLivros.create(new Livro("9788550804620", "Java Efetivo: As melhores práticas para a plataforma Java", "Joshua Bloch", 3, LocalDate.of(2019,5,28), 96.98F));
        arqLivros.create(new Livro("9788522128143", "Algoritmos e Lógica de Programação", "Marco A. Furlano de Souza", 3, LocalDate.of(2019,1,10), 65.75F));
    }

}
