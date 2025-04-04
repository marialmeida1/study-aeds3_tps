package tp01.src.data;

import java.util.ArrayList;

import tp01.src.models.*;
import tp01.src.storeage.*;

public class ArquivoEpisodio extends Arquivo<Episodio> {

    Arquivo<Episodio> arqEpisodio;
    ArvoreBMais<ParIDFK> arvore;

    public ArquivoEpisodio() throws Exception {
        super("episodio", Episodio.class.getConstructor()); // Cria um episódio básico

        arvore = new ArvoreBMais<>(ParIDFK.class.getConstructor(), 5, "tp01/files/episodio/arvore.c.db");
    }

    @Override
    public int create(Episodio c) throws Exception {
        int id = super.create(c);
        arvore.create(new ParIDFK(c.id, c.fkSerie)); // Cria um par int int, com o id do episodio e id da serie
        return id;
    }

    public Episodio read(int c1, int c2) throws Exception {
        ArrayList<ParIDFK> lista = arvore.read(new ParIDFK(c1, c2)); // Acha uma lista de valores
        if (!lista.isEmpty()) { // Irá sempre retornar um valor
            ParIDFK resultado = lista.get(0); // Ou seja, pega o primeiro
            return read(resultado.getId()); // Retorna o episódio dele
        } else {
            return null; // Não retorna nada
        }
    }

    public boolean delete(int c1, int c2) throws Exception {
        ArrayList<ParIDFK> lista = arvore.read(new ParIDFK(c1, c2)); 
        if (!lista.isEmpty()){
            ParIDFK resultado = lista.get(0); 
            if (delete(resultado.getId())) 
                return arvore.delete(resultado);
        }
        return false; 
    }
}
