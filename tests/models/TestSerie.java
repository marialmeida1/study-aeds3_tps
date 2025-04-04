package tests.models;

import tp01.src.models.Serie;

public class TestSerie {
    public static void main(String[] args) {
        try {
            // Test constructors
            Serie serie = new Serie("Breaking Bad", "A chemistry teacher turns to making drugs.", 62, (short) 2008, "Netflix", 1);
            System.out.println("Created Serie:");
            System.out.println(serie);

            // Test toByteArray and fromByteArray
            byte[] byteArray = serie.toByteArray();
            Serie deserializedSerie = new Serie();
            deserializedSerie.fromByteArray(byteArray);

            System.out.println("\nDeserialized Serie:");
            System.out.println(deserializedSerie);

            // Verify equality
            System.out.println("\nEquality Test: " + serie.equals(deserializedSerie));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
