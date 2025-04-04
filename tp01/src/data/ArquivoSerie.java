package tp01.src.data;

import tp01.src.models.Serie;
import tp01.src.storeage.*;

public class ArquivoSerie extends Arquivo<Serie> {

    HashExtensivel<ParStringID> indiceIndiretoNome; // Indirect index for 'nome'

    public ArquivoSerie() throws Exception {
        // Initialize the parent class with "series" and the Serie constructor
        super("series", Serie.class.getConstructor());

        // Create the indirect index for 'nome'
        indiceIndiretoNome = new HashExtensivel<>(
                ParStringID.class.getConstructor(),
                4,
                "tp01/files/series/indiceNome.d.db", // Directory for the 'nome' index
                "tp01/files/series/indiceNome.c.db"  // Buckets for the 'nome' index
        );
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceIndiretoNome.create(new ParStringID(s.nome, id)); // Use 'nome' as the secondary key
        return id;
    }

    public Serie read(String nome) throws Exception {
        ParStringID psi = indiceIndiretoNome.read(ParStringID.hash(nome));
        if (psi == null)
            return null;
        return read(psi.getId());
    }

    public boolean delete(String nome) throws Exception {
        ParStringID psi = indiceIndiretoNome.read(ParStringID.hash(nome));
        if (psi != null)
            if (delete(psi.getId()))
                return indiceIndiretoNome.delete(ParStringID.hash(nome));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie s = super.read(id);
        if (s != null) {
            if (super.delete(id))
                return indiceIndiretoNome.delete(ParStringID.hash(s.nome));
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie serieVelha = read(novaSerie.nome);
        if (super.update(novaSerie)) {
            if (!novaSerie.nome.equals(serieVelha.nome)) {
                indiceIndiretoNome.delete(ParStringID.hash(serieVelha.nome));
                indiceIndiretoNome.create(new ParStringID(novaSerie.nome, novaSerie.getId()));
            }
            return true;
        }
        return false;
    }
}
