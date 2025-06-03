package tp03.src.data;
import java.util.List;

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
    private ListaInvertida listaInvertida;
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
                PairNameID.class.getConstructor(), 5, "tp03/files/atores/indiceNome.db");
        listaInvertida = new ListaInvertida(10, "tp03/files/atores/listaInvertidaDicionario.db", "tp03/files/atores/listaInvertidaBlocos.db");
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
        indiceIndiretoNome.create(new PairNameID(a.getName(), id));
        listaInvertida.create(a.getName(), new ElementoLista(id, 1)); // Adiciona o ator na ListaInvertida
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

        // Busca os elementos na lista invertida
        ElementoLista[] elementos = listaInvertida.read(nome);

        // Se nenhum elemento foi encontrado, retorna null
        if (elementos == null || elementos.length == 0) {
            return null;
        }

        // Lê os atores correspondentes aos IDs encontrados
        Actor[] atores = new Actor[elementos.length];
        int i = 0;
        for (ElementoLista elemento : elementos) {
            atores[i++] = read(elemento.getId());
        }

        return atores;
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
                indiceIndiretoNome.delete(new PairNameID(a.getName(), id));
                listaInvertida.delete(a.getName(), id); // Remove o ator da ListaInvertida
                return true;
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
        Actor a = read(atorUpdate.getId());
        if (a != null) {
            if (super.update(atorUpdate)) {
                if (!a.getName().equals(atorUpdate.getName())) {
                    indiceIndiretoNome.delete(new PairNameID(a.getName(), a.getId()));
                    indiceIndiretoNome.create(new PairNameID(atorUpdate.getName(), atorUpdate.getId()));
                    listaInvertida.delete(a.getName(), a.getId()); // Remove o nome antigo da ListaInvertida
                    listaInvertida.create(atorUpdate.getName(), new ElementoLista(atorUpdate.getId(), 1)); // Adiciona o nome atualizado
                }
                return true;
            }
        }
        return false;
    }
}

