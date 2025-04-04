package tests.data;

import tp01.src.models.Serie;
import tp01.src.data.ArquivoSerie;

public class TestArquivoSerie {
    public static void main(String[] args) {
        try {
            ArquivoSerie arquivoSerie = new ArquivoSerie();

            // Test create
            Serie serie = new Serie("Breaking Bad", "A chemistry teacher turns to making drugs.", 62, (short) 2008, "Netflix", 1);
            int id = arquivoSerie.create(serie);
            System.out.println("Created Serie with ID: " + id);

            // Test read by ID
            Serie readSerie = arquivoSerie.read(id);
            System.out.println("\nRead Serie by ID:");
            System.out.println(readSerie);

            // Test read by name
            Serie readByName = arquivoSerie.read("Breaking Bad");
            System.out.println("\nRead Serie by Name:");
            System.out.println(readByName);

            // Test update
            serie.sinopse = "A teacher turns to crime.";
            boolean updated = arquivoSerie.update(serie);
            System.out.println("\nUpdated Serie: " + updated);

            // Test delete
            boolean deleted = arquivoSerie.delete(id);
            System.out.println("\nDeleted Serie: " + deleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
