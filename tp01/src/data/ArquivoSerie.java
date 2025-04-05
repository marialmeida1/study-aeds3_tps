package tp01.src.data;
import java.util.ArrayList;

import tp01.src.models.Serie;
import tp01.src.storeage.*;

public class ArquivoSerie extends Arquivo<Serie> {
    Arquivo<Serie> arqLivros;
    ArvoreBMais<ParStringID> indiceTitulo;

    public ArquivoSerie() throws Exception {
        super("livros", Serie.class.getConstructor());
        indiceTitulo = new ArvoreBMais<>(
            ParStringID.class.getConstructor(), 
            5, 
            "./dados/series/indiceTitulo.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceTitulo.create(new ParStringID(s.getName(), id));
        return id;
    }

    public Serie[] read(String titulo) throws Exception {
        if(titulo.length()==0)
            return null;
        ArrayList<ParStringID> ptis = indiceTitulo.read(new ParStringID(titulo, -1));
        if(ptis.size()>0) {
            Serie[] series = new Serie[ptis.size()];
            int i=0;
            for(ParStringID pti: ptis) 
                series[i++] = read(pti.getId());
            return series;
        }
        else 
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie s = read(id);   // na superclasse
        if(s!=null) {
            if(super.delete(id))
                return indiceTitulo.delete(new ParStringID(s.getName(), id));
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie s = read(novaSerie.getId());    // na superclasse
        if(s!=null) {
            if(super.update(novaSerie)) {
                if(!s.getName().equals(novaSerie.getName())) {
                    indiceTitulo.delete(new ParStringID(s.getName(), s.getId()));
                    indiceTitulo.create(new ParStringID(novaSerie.getName(), novaSerie.getId()));
                }
                return true;
            }
        }
        return false;
    }

}

