package tp01.src.data;
import java.util.ArrayList;

import tp01.src.models.Series;
import tp01.src.storage.indexes.*;
import tp01.src.storage.structures.*;

public class ArquivoSeries extends Archive<Series> {

    ArchiveTreeB<PairNameID> indiceIndiretoNome; // Indirect index for 'nome'

    public ArquivoSeries() throws Exception {

        super("series", Series.class.getConstructor());

        indiceIndiretoNome = new ArchiveTreeB<>(
                PairNameID.class.getConstructor(), 5, "tp01/files/series/indiceTitulo.db");
    }

    @Override
    public int create(Series s) throws Exception {
        int id = super.create(s);
        indiceIndiretoNome.create(new PairNameID(s.getName(), id));
        return id;
    }

    public Series[] readNome(String nome) throws Exception {

        if (nome.length() == 0)
            return null;

        ArrayList<PairNameID> pares = indiceIndiretoNome.read(new PairNameID(nome, -1));

        if (pares.size() > 0) {

            Series[] series = new Series[pares.size()];

            int i = 0;

            for (PairNameID par : pares) {

                series[i++] = read(par.getId());

            }

            return series;

        } else {
            return null;
        }

    }

    @Override
    public boolean delete(int id) throws Exception {
        Series s = super.read(id);
        if (s != null) {
            if (super.delete(id)) {
                return indiceIndiretoNome.delete(new PairNameID(s.getName(), id));
            }
        }
        return false;
    }

    @Override
    public boolean update(Series novaSerie) throws Exception {
        Series s = read(novaSerie.getId()); // na superclasse
        if (s != null) {
            if (super.update(novaSerie)) {
                if (!s.getName().equals(novaSerie.getName())) {
                    indiceIndiretoNome.delete(new PairNameID(s.getName(), s.getId()));
                    indiceIndiretoNome.create(new PairNameID(novaSerie.getName(), novaSerie.getId()));
                }
                return true;
            }
        }
        return false;
    }

}

