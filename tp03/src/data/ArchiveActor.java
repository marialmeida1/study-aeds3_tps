package tp03.src.data;
import java.util.ArrayList;

import tp03.src.models.Actor;
import tp03.src.storage.indexes.*;
import tp03.src.storage.structures.*;
import tp03.src.storage.structures.ListaInvertida.ElementoLista;
import tp03.src.storage.structures.ListaInvertida.ListaInvertida;

/**
 * Classe responsável pela manipulação dos dados de atores,
 * incluindo operações CRUD e indexação por nome.
 */
public class ArchiveActor extends Archive<Actor> {
    /** Índice invertido baseado no nome do ator. */
    ListaInvertida listaInvertidaNome;

    /**
     * Construtor padrão que inicializa o arquivo e o índice indireto de nomes.
     * 
     * @throws Exception se ocorrer erro durante a criação do arquivo ou índice.
     */
    public ArchiveActor() throws Exception {

        super("atores", Actor.class.getConstructor());

        listaInvertidaNome = new ListaInvertida(5,
                "tp03/files/atores/blocos.listainv.db", // caminho do índice invertido
                "tp03/files/atores/dicionario.listainv.db");    // opcional: mapeia termos
    }

    /**
     * Cria uma nova ator, armazenando-a no arquivo e no índice de nomes.
     * 
     * @param a ator a ser criada.
     * @return o ID gerado para a ator.
     * @throws Exception se ocorrer erro durante o armazenamento.
     */
    @Override
    public int create(Actor a) throws Exception {
        int id = super.create(a);
        
        ElementoLista elemento = new ElementoLista(id, 1.0f);
        listaInvertidaNome.create(a.getName(), elemento);
        return id;
    }

    /**
     * Lê todas as ators com o nome especificado.
     * 
     * @param nome o nome da ator.
     * @return array de ators com o nome correspondente, ou {@code null} se não houver.
     * @throws Exception se ocorrer erro durante a leitura.
     */
    public Actor[] readNome(String nome) throws Exception {

        if (nome.length() == 0)
            return null;

        ArrayList<PairNameID> pares = indiceIndiretoNome.read(new PairNameID(nome, -1));

        if (pares.size() > 0) {

            Actor[] atores = new Actor[pares.size()];

            int i = 0;

            for (PairNameID par : pares) {

                atores[i++] = read(par.getId());

            }

            return atores;

        } else {
            return null;
        }

    }

    /**
     * Exclui uma ator do arquivo e remove sua entrada do índice de nomes.
     * 
     * @param id o identificador da ator a ser removida.
     * @return {@code true} se a ator for removida com sucesso, {@code false} caso contrário.
     * @throws Exception se ocorrer erro durante a exclusão.
     */
    @Override
    public boolean delete(int id) throws Exception {
        Actor a = super.read(id);
        if (a != null) {
            if (super.delete(id)) {
                return listaInvertidaNome.delete(a.getName(), id);
            }
        }
        return false;
    }

    /**
     * Atualiza os dados de uma ator, ajustando o índice de nomes se o nome tiver mudado.
     * 
     * @param atorUpdate o novo objeto contendo os dados atualizados da ator.
     * @return {@code true} se a atualização for bem-sucedida, {@code false} caso contrário.
     * @throws Exception se ocorrer erro durante a atualização.
     */
    @Override
    public boolean update(Actor atorUpdate) throws Exception {
        Actor a = read(atorUpdate.getId()); // na superclasse
        if (a != null) {
            ElementoLista elemento = new ElementoLista(a.getId(), 1.0f);
            if (super.update(atorUpdate)) {
                if (!a.getName().equals(atorUpdate.getName())) {
                    listaInvertidaNome.delete(a.getName(), a.getId());
                    listaInvertidaNome.create(atorUpdate.getName(), elemento);
                }
                return true;
            }
        }
        return false;
    }

}

