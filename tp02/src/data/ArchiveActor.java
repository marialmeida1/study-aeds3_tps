package tp02.src.data;
import java.util.ArrayList;

import tp02.src.models.Actor;
import tp02.src.storage.indexes.*;
import tp02.src.storage.structures.*;

/**
 * Classe responsável pela manipulação dos dados de atores,
 * incluindo operações CRUD e indexação por nome.
 */
public class ArchiveActor extends Archive<Actor> {

    /** Índice indireto baseado no nome da ator. */
    ArchiveTreeB<PairNameID> indiceIndiretoNome;

    /**
     * Construtor padrão que inicializa o arquivo e o índice indireto de nomes.
     * 
     * @throws Exception se ocorrer erro durante a criação do arquivo ou índice.
     */
    public ArchiveActor() throws Exception {

        super("atores", Actor.class.getConstructor());

        indiceIndiretoNome = new ArchiveTreeB<>(
                PairNameID.class.getConstructor(), 5, "tp02/files/atores/indiceNome.db");
    }

    /**
     * Cria uma nova ator, armazenando-a no arquivo e no índice de nomes.
     * 
     * @param s a ator a ser criada.
     * @return o ID gerado para a ator.
     * @throws Exception se ocorrer erro durante o armazenamento.
     */
    @Override
    public int create(Actor a) throws Exception {
        int id = super.create(a);
        indiceIndiretoNome.create(new PairNameID(a.getName(), id));
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
                return indiceIndiretoNome.delete(new PairNameID(a.getName(), id));
            }
        }
        return false;
    }

    /**
     * Atualiza os dados de uma ator, ajustando o índice de nomes se o nome tiver mudado.
     * 
     * @param novoAtor o novo objeto contendo os dados atualizados da ator.
     * @return {@code true} se a atualização for bem-sucedida, {@code false} caso contrário.
     * @throws Exception se ocorrer erro durante a atualização.
     */
    @Override
    public boolean update(Actor novoAtor) throws Exception {
        Actor a = read(novoAtor.getId()); // na superclasse
        if (a != null) {
            if (super.update(novoAtor)) {
                if (!a.getName().equals(novoAtor.getName())) {
                    indiceIndiretoNome.delete(new PairNameID(a.getName(), a.getId()));
                    indiceIndiretoNome.create(new PairNameID(novoAtor.getName(), novoAtor.getId()));
                }
                return true;
            }
        }
        return false;
    }

}

