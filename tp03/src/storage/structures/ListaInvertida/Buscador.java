package tp03.src.storage.structures.ListaInvertida;

import java.util.*;

public class Buscador {

    private ListaInvertida listaSeries;
    private ListaInvertida listaEpisodios;
    private ListaInvertida listaAtores;

    public Buscador(ListaInvertida listaSeries, ListaInvertida listaEpisodios, ListaInvertida listaAtores) {
        this.listaSeries = listaSeries;
        this.listaEpisodios = listaEpisodios;
        this.listaAtores = listaAtores;
    }

    public List<Integer> buscarSeries(String termos) throws Exception {
        return buscar(termos, listaSeries);
    }

    public List<Integer> buscarEpisodios(String termos) throws Exception {
        return buscar(termos, listaEpisodios);
    }

    public List<Integer> buscarAtores(String termos) throws Exception {
        return buscar(termos, listaAtores);
    }

    private List<Integer> buscar(String termos, ListaInvertida lista) throws Exception {
        // Normalizar e processar os termos
        ListaInvertidaUtils utils = new ListaInvertidaUtils();
        String[] termosProcessados = utils.extractTerms(termos);
        Map<Integer, Float> relevancias = new HashMap<>();

        for (String termo : termosProcessados) {
            ElementoLista[] elementos = lista.read(termo);
            if (elementos != null) {
                for (ElementoLista elemento : elementos) {
                    int id = elemento.getId();
                    float tf = elemento.getFrequencia();
                    float idf = calcularIDF(lista, termo);
                    float tfidf = tf * idf;

                    relevancias.put(id, relevancias.getOrDefault(id, 0f) + tfidf);
                }
            }
        }

        // Ordenar os IDs por relevância
        List<Map.Entry<Integer, Float>> listaOrdenada = new ArrayList<>(relevancias.entrySet());
        listaOrdenada.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));

        // Retornar apenas os IDs ordenados
        List<Integer> idsOrdenados = new ArrayList<>();
        for (Map.Entry<Integer, Float> entry : listaOrdenada) {
            idsOrdenados.add(entry.getKey());
        }

        return idsOrdenados;
    }

    private float calcularIDF(ListaInvertida lista, String termo) throws Exception {
        int totalDocumentos = lista.numeroEntidades();
        ElementoLista[] elementos = lista.read(termo);
        int documentosComTermo = (elementos != null) ? elementos.length : 0;

        if (documentosComTermo == 0) {
            return 0;
        }

        return (float) Math.log((double) totalDocumentos / documentosComTermo);
    }
}