package tp01.src.data;

import java.util.ArrayList;

import tp01.src.models.Episode;
import tp01.src.storage.indexes.*;
import tp01.src.storage.structures.*;

public class ArquivoEpisode extends Archive<Episode> {

    ArchiveTreeB<PairNameID> indiceIndiretoNome; // Indirect index for 'nome'
    ArchiveTreeB<PairIDFK> relacaoNN; // Indirect index for 'nome'

    public ArquivoEpisode() throws Exception {

        super("episodios", Episode.class.getConstructor());

        indiceIndiretoNome = new ArchiveTreeB<>(
                PairNameID.class.getConstructor(), 5, "tp01/files/episodios/indiceTitulo.db");

        relacaoNN = new ArchiveTreeB<>(PairIDFK.class.getConstructor(), 5, "tp01/files/episodios/relacaoNN.db");
    }

    @Override
    public int create(Episode e) throws Exception {
        int id = super.create(e);
        indiceIndiretoNome.create(new PairNameID(e.getName(), id));
        relacaoNN.create(new PairIDFK(e.getId(), e.getFkSerie()));
        return id;
    }

    public Episode[] readFkSerie(int fkSeries) throws Exception { // Faz a busca somente dentro de epsódios
        ArrayList<PairIDFK> pares = relacaoNN.read(new PairIDFK(fkSeries));
        
        if (pares.size() > 0) {

            Episode[] episodios = new Episode[pares.size()];

            int i = 0;

            for (PairIDFK par : pares) {

                episodios[i++] = read(par.getId());

            }

            return episodios;

        } else {
            return null;
        }

    }

    public Episode[] readNome(String nome) throws Exception { // Faz a busca somente dentro de epsódios

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

    public Episode[] readEpisodiosPorSerieENome(int fkSerie, String nome) throws Exception {
        if (nome.length() == 0)
            return null;

        Episode[] episodiosDaSerie = readFkSerie(fkSerie);
        if (episodiosDaSerie == null || episodiosDaSerie.length == 0)
            return null;

        ArrayList<PairNameID> paresNome = indiceIndiretoNome.read(new PairNameID(nome, -1));

        ArrayList<Episode> episodiosFiltrados = new ArrayList<>();

        for (PairNameID parNome : paresNome) {
            for (Episode ep : episodiosDaSerie) {
                if (ep.getId() == parNome.getId()) {
                    episodiosFiltrados.add(ep);
                    break; 
                }
            }
        }

        return episodiosFiltrados.isEmpty() ? null : episodiosFiltrados.toArray(new Episode[0]);
    }


    @Override
    public boolean delete(int id) throws Exception {
        Episode e = super.read(id);
        if (e != null) {
            if (super.delete(id)) {
                return indiceIndiretoNome.delete(new PairNameID(e.getName(), id)) && relacaoNN.delete(new PairIDFK(e.getId(), e.getFkSerie()));
            }
        }
        return false;
    }

    @Override
    public boolean update(Episode novaEpisodio) throws Exception {
        Episode e = read(novaEpisodio.getId()); // na superclasse
        if (e != null) {
            if (super.update(novaEpisodio)) {
                if (!e.getName().equals(novaEpisodio.getName())) {
                    indiceIndiretoNome.delete(new PairNameID(e.getName(), e.getId()));
                    relacaoNN.delete(new PairIDFK(e.getId(), e.getFkSerie()));
                    indiceIndiretoNome.create(new PairNameID(novaEpisodio.getName(), novaEpisodio.getId()));
                    relacaoNN.create(new PairIDFK(e.getId(), e.getFkSerie()));
                }
                return true;
            }
        }
        return false;
    }

}
