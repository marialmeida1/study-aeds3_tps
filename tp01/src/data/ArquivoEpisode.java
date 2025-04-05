package tp01.src.data;
import java.util.ArrayList;

import tp01.src.models.Episodio;
import tp01.src.storeage.indexes.*;
import tp01.src.storeage.structures.*;

public class ArquivoEpisodio extends Arquivo<Episodio> {

    ArvoreBMais<ParNameID> indiceIndiretoNome; // Indirect index for 'nome'

    public ArquivoEpisodio() throws Exception {

        super("episodios", Episodio.class.getConstructor());

        indiceIndiretoNome = new ArvoreBMais<>(
                ParNameID.class.getConstructor(), 5, "tp01/files/episodios/indiceTitulo.db");
    }

    @Override
    public int create(Episodio s) throws Exception {
        int id = super.create(s);
        indiceIndiretoNome.create(new ParNameID(s.getName(), id));
        return id;
    }

    public Episodio[] readNome(String nome) throws Exception {

        if (nome.length() == 0)
            return null;

        ArrayList<ParNameID> pares = indiceIndiretoNome.read(new ParNameID(nome, -1));

        if (pares.size() > 0) {

            Episodio[] episodios = new Episodio[pares.size()];

            int i = 0;

            for (ParNameID par : pares) {

                episodios[i++] = read(par.getId());

            }

            return episodios;

        } else {
            return null;
        }

    }

    @Override
    public boolean delete(int id) throws Exception {
        Episodio s = super.read(id);
        if (s != null) {
            if (super.delete(id)) {
                return indiceIndiretoNome.delete(new ParNameID(s.getName(), id));
            }
        }
        return false;
    }

    @Override
    public boolean update(Episodio novaEpisodio) throws Exception {
        Episodio s = read(novaEpisodio.getId()); // na superclasse
        if (s != null) {
            if (super.update(novaEpisodio)) {
                if (!s.getName().equals(novaEpisodio.getName())) {
                    indiceIndiretoNome.delete(new ParNameID(s.getName(), s.getId()));
                    indiceIndiretoNome.create(new ParNameID(novaEpisodio.getName(), novaEpisodio.getId()));
                }
                return true;
            }
        }
        return false;
    }

}

