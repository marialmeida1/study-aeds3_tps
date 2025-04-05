package tp01.src.data;

import tp01.src.models.Serie;
import tp01.src.storeage.*;
import java.util.ArrayList;

public class ArquivoSerie extends Arquivo<Serie> {

    ArvoreBMais<ParTituloId> indiceIndiretoNome; // Indirect index for 'nome'

    public ArquivoSerie() throws Exception {

        super("series", Serie.class.getConstructor());

        indiceIndiretoNome = new ArvoreBMais<>(
                ParTituloId.class.getConstructor(), 5, "tp01/files/series/indiceTitulo.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceIndiretoNome.create(new ParTituloId(s.getNome(), id));
        return id;
    }

    public Serie[] readNome(String nome) throws Exception {

        if (nome.length() == 0)
            return null;

        ArrayList<ParTituloId> pares = indiceIndiretoNome.read(new ParTituloId(nome, -1));

        if (pares.size() > 0) {

            Serie[] series = new Serie[pares.size()];

            int i = 0;

            for (ParTituloId par : pares) {

                series[i++] = read(par.getId());

            }

            return series;

        } else {

            return null;
        }

    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie s = super.read(id);
        if (s != null) {
            if (super.delete(id)) {
                return indiceIndiretoNome.delete(new ParTituloId(s.getNome(), id));
            }
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie s = read(novaSerie.getId()); // na superclasse
        if (s != null) {
            if (super.update(novaSerie)) {
                if (!s.getNome().equals(novaSerie.getNome())) {
                    indiceIndiretoNome.delete(new ParTituloId(s.getNome(), s.getId()));
                    indiceIndiretoNome.create(new ParTituloId(novaSerie.getNome(), novaSerie.getId()));
                }
                return true;
            }
        }
        return false;
    }
}
