package tp01.src.data;
import java.util.ArrayList;

import tp01.src.models.Episode;
import tp01.src.storeage.indexes.*;
import tp01.src.storeage.structures.*;

public class ArquivoEpisode extends Archive<Episode> {

    ArchiveTreeB<PairNameID> indiceIndiretoNome; // Indirect index for 'nome'
    ArchiveTreeB<Par> indiceIndiretoNome; // Indirect index for 'nome'

    public ArquivoEpisode() throws Exception {

        super("episodios", Episode.class.getConstructor());

        indiceIndiretoNome = new ArchiveTreeB<>(
                PairNameID.class.getConstructor(), 5, "tp01/files/episodios/indiceTitulo.db");
    }

    @Override
    public int create(Episode s) throws Exception {
        int id = super.create(s);
        indiceIndiretoNome.create(new PairNameID(s.getName(), id));
        return id;
    }

    public Episode[] readNome(String nome) throws Exception {

        if (nome.length() == 0)
            return null;

        ArrayList<PairNameID> pares = indiceIndiretoNome.read(new PairNameID(nome, -1));

        if (pares.size() > 0) {

            Episode[] episodios = new Episode[pares.size()];

            int i = 0;

            for (PairNameID par : pares) {

                episodios[i++] = read(par.getId());

            }

            return episodios;

        } else {
            return null;
        }

    }

    @Override
    public boolean delete(int id) throws Exception {
        Episode s = super.read(id);
        if (s != null) {
            if (super.delete(id)) {
                return indiceIndiretoNome.delete(new PairNameID(s.getName(), id));
            }
        }
        return false;
    }

    @Override
    public boolean update(Episode novaEpisodio) throws Exception {
        Episode s = read(novaEpisodio.getId()); // na superclasse
        if (s != null) {
            if (super.update(novaEpisodio)) {
                if (!s.getName().equals(novaEpisodio.getName())) {
                    indiceIndiretoNome.delete(new PairNameID(s.getName(), s.getId()));
                    indiceIndiretoNome.create(new PairNameID(novaEpisodio.getName(), novaEpisodio.getId()));
                }
                return true;
            }
        }
        return false;
    }

}

