package tp02.src.data;
import java.util.ArrayList;

import tp02.src.models.Series;
import tp02.src.storage.indexes.*;
import tp02.src.storage.structures.*;

/**
 * Classe responsável pela manipulação dos dados de séries,
 * incluindo operações CRUD e indexação por nome.
 */
public class ArchiveSeries extends Archive<Series> {

    /** Índice indireto baseado no nome da série. */
    ArchiveTreeB<PairNameID> indiceIndiretoNome;

    /**
     * Construtor padrão que inicializa o arquivo e o índice indireto de nomes.
     * 
     * @throws Exception se ocorrer erro durante a criação do arquivo ou índice.
     */
    public ArchiveSeries() throws Exception {

        super("series", Series.class.getConstructor());

        indiceIndiretoNome = new ArchiveTreeB<>(
                PairNameID.class.getConstructor(), 5, "tp02/files/series/indiceNome.db");
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
        indiceIndiretoNome.create(new PairNameID(s.getName(), id));
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
                return indiceIndiretoNome.delete(new PairNameID(s.getName(), id));
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
            if (super.update(novaSerie)) {
                if (!s.getName().equals(novaSerie.getName())) {
                    indiceIndiretoNome.delete(new PairNameID(s.getName(), s.getId()));
                    indiceIndiretoNome.create(new PairNameID(novaSerie.getName(), novaSerie.getId()));
                }
                return true;
            }
        }
        return false;
    }

}

