package tp01.src.data;

import java.util.ArrayList;
import java.util.Arrays;

import tp01.src.models.Serie;
import tp01.src.storeage.*;

public class ArquivoSerie extends Arquivo<Serie> {

    ArvoreBMais<ParStringID> indiceIndiretoNome; // Indirect index for 'nome'

    public ArquivoSerie() throws Exception {
        // Initialize the parent class with "series" and the Serie constructor
        super("series", Serie.class.getConstructor());

        indiceIndiretoNome = new ArvoreBMais<>(ParStringID.class.getConstructor(), 5,
                "tp01/files/series/indiceNome.c.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        System.out.println("Adicionando ao índice: " + s.getNome() + " -> " + id);

        try {
            indiceIndiretoNome.create(new ParStringID(s.getNome(), id));
        } catch (Exception e) {
            System.err.println("Erro ao adicionar ao índice: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace completo para análise
        }

        return id;
    }

    public Serie[] read(String nome) throws Exception {
        if (nome == null || nome.isEmpty())
            return null; // Evita nome vazio ou nulo

        ArrayList<ParStringID> ptis = indiceIndiretoNome.read(new ParStringID(nome, -1));

        if (ptis == null || ptis.isEmpty())
            return null; // Verifica se o índice retornou resultados

        Serie[] series = new Serie[ptis.size()];
        int i = 0;

        for (ParStringID pti : ptis) {
            Serie s = read(pti.getId());
            if (s != null) {
                series[i++] = s; // Apenas adiciona se não for null
            }
        }

        if (i == 0)
            return null; // Nenhuma série foi encontrada
        return Arrays.copyOf(series, i); // Retorna apenas as séries válidas
    }

    public boolean delete(String nome) throws Exception {
        ArrayList<ParStringID> lista = indiceIndiretoNome.read(new ParStringID(nome));
        if (!lista.isEmpty()) {
            ParStringID resultado = lista.get(0);
            if (delete(resultado.getId()))
                return indiceIndiretoNome.delete(resultado);
        }
        return false;
    }

    /*
     * @Override
     * public boolean update(Serie novaSerie) throws Exception {
     * Serie serieVelha = read(novaSerie.getNome());
     * if (super.update(novaSerie)) {
     * if (!novaSerie.nome.equals(serieVelha.nome)) {
     * indiceIndiretoNome.delete(new ParStringID(serieVelha.nome));
     * indiceIndiretoNome.create(new ParStringID(novaSerie.nome,
     * novaSerie.getId()));
     * }
     * return true;
     * }
     * return false;
     * }
     */
}
