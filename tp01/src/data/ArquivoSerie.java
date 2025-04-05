package tp01.src.data;
import java.util.ArrayList;

import tp01.src.models.Serie;
import tp01.src.storeage.indexes.*;
import tp01.src.storeage.structures.*;

public class ArquivoSerie extends Arquivo<Serie> {

    ArvoreBMais<ParNameID> indiceIndiretoNome; // Indirect index for 'nome'

    public ArquivoSerie() throws Exception {

        super("series", Serie.class.getConstructor());

        indiceIndiretoNome = new ArvoreBMais<>(
                ParNameID.class.getConstructor(), 5, "tp01/files/series/indiceTitulo.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceIndiretoNome.create(new ParNameID(s.getName(), id));
        return id;
    }

    public Serie[] readNome(String nome) throws Exception {

        if (nome.length() == 0)
            return null;

        ArrayList<ParNameID> pares = indiceIndiretoNome.read(new ParNameID(nome, -1));

        if (pares.size() > 0) {

            Serie[] series = new Serie[pares.size()];

            int i = 0;

            for (ParNameID par : pares) {

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
                return indiceIndiretoNome.delete(new ParNameID(s.getName(), id));
            }
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie s = read(novaSerie.getId()); // na superclasse
        if (s != null) {
            if (super.update(novaSerie)) {
                if (!s.getName().equals(novaSerie.getName())) {
                    indiceIndiretoNome.delete(new ParNameID(s.getName(), s.getId()));
                    indiceIndiretoNome.create(new ParNameID(novaSerie.getName(), novaSerie.getId()));
                }
                return true;
            }
        }
        return false;
    }

}

