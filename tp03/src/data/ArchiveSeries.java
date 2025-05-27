package tp03.src.data;
import java.util.ArrayList;

import tp03.src.models.Series;
import tp03.src.storage.indexes.*;
import tp03.src.storage.structures.*;
import tp03.src.storage.structures.ListaInvertida.ElementoLista;
import tp03.src.storage.structures.ListaInvertida.ListaInvertida;

/**
 * Classe responsável pela manipulação dos dados de séries,
 * incluindo operações CRUD e indexação por nome.
 */
public class ArchiveSeries extends Archive<Series> {
    ListaInvertida listaInvertidaNome;

    /**
     * Construtor padrão que inicializa o arquivo e o índice indireto de nomes.
     * 
     * @throws Exception se ocorrer erro durante a criação do arquivo ou índice.
     */
    public ArchiveSeries() throws Exception {

        super("series", Series.class.getConstructor());

        listaInvertidaNome = new ListaInvertida(5,
                "tp03/files/series/blocos.listainv.db", // caminho do índice invertido
                "tp03/files/series/dicionario.listainv.db");    // opcional: mapeia termos
    }

    /**
     * Cria uma nova série, armazenando-a no arquivo e no índice de nomes.
     * 
     * @param s a série a ser criada.
     * @return o ID gerado para a série.
     * @throws Exception se ocorrer erro durante o armazenamento.
     */
    @Override
    public int create(Series s) throws Exception {
        // Check if a series with the same name already exists
        Series[] existingSeries = readNome(s.getName());
        if (existingSeries != null && existingSeries.length > 0) {
            throw new Exception("Série com o mesmo nome já existe.");
        }
        int id = super.create(s);
        ElementoLista elemento = new ElementoLista(id, 1.0f);
        listaInvertidaNome.create(s.getName(), elemento);
        return id;
    }

    /**
     * Lê todas as séries com o nome especificado.
     * 
     * @param nome o nome da série.
     * @return array de séries com o nome correspondente, ou {@code null} se não houver.
     * @throws Exception se ocorrer erro durante a leitura.
     */
    public Series[] readNome(String nome) throws Exception {

        if (nome.length() == 0)
            return null;

        ArrayList<PairNameID> pares = indiceIndiretoNome.read(new PairNameID(nome, -1));

        if (pares.size() > 0) {

            Series[] series = new Series[pares.size()];

            int i = 0;

            for (PairNameID par : pares) {

                series[i++] = read(par.getId());

            }

            return series;

        } else {
            return null;
        }

    }

    /**
     * Exclui uma série do arquivo e remove sua entrada do índice de nomes.
     * 
     * @param id o identificador da série a ser removida.
     * @return {@code true} se a série for removida com sucesso, {@code false} caso contrário.
     * @throws Exception se ocorrer erro durante a exclusão.
     */
    @Override
    public boolean delete(int id) throws Exception {
        Series s = super.read(id);
        if (s != null) {
            if (super.delete(id)) {
                return listaInvertidaNome.delete(s.getName(), id);
            }
        }
        return false;
    }

    /**
     * Atualiza os dados de uma série, ajustando o índice de nomes se o nome tiver mudado.
     * 
     * @param novaSerie o novo objeto contendo os dados atualizados da série.
     * @return {@code true} se a atualização for bem-sucedida, {@code false} caso contrário.
     * @throws Exception se ocorrer erro durante a atualização.
     */
    @Override
    public boolean update(Series novaSerie) throws Exception {
        Series s = read(novaSerie.getId()); // na superclasse
        if (s != null) {
            ElementoLista elemento = new ElementoLista(s.getId(), 1.0f);
            if (super.update(novaSerie)) {
                if (!s.getName().equals(novaSerie.getName())) {
                    listaInvertidaNome.delete(s.getName(), s.getId());
                    listaInvertidaNome.create(novaSerie.getName(), elemento);
                }
                return true;
            }
        }
        return false;
    }

}

