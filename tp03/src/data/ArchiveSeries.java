package tp03.src.data;
import java.util.ArrayList;
import java.util.List;

import tp03.src.models.Series;
import tp03.src.storage.indexes.*;
import tp03.src.storage.structures.*;
import tp03.src.storage.structures.ListaInvertida.Buscador;
import tp03.src.storage.structures.ListaInvertida.ElementoLista;
import tp03.src.storage.structures.ListaInvertida.ListaInvertida;

/**
 * Classe responsável pela manipulação dos dados de séries,
 * incluindo operações CRUD e indexação por nome.
 */
public class ArchiveSeries extends Archive<Series> {
    private ListaInvertida listaInvertida;
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
                PairNameID.class.getConstructor(), 5, "tp03/files/series/indiceNome.db");
        listaInvertida = new ListaInvertida(10, "tp03/files/series/listaInvertidaDicionario.db", "tp03/files/series/listaInvertidaBlocos.db");
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
        // Verifica se já existe uma série com o mesmo nome
        Series[] existingSeries = readEntity(s.getName());
        if (existingSeries != null && existingSeries.length > 0) {
            throw new Exception("Série com o mesmo nome já existe.");
        }
        int id = super.create(s);
        indiceIndiretoNome.create(new PairNameID(s.getName(), id));
        listaInvertida.create(s.getName(), new ElementoLista(id, 1)); // Adiciona a série na ListaInvertida
        return id;
    }

    /**
     * Lê todas as séries com o nome especificado.
     * 
     * @param nome o nome da série.
     * @return array de séries com o nome correspondente, ou {@code null} se não houver.
     * @throws Exception se ocorrer erro durante a leitura.
     */

        /**
     * Lê todas as séries com o nome especificado.
     * 
     * @param nome o nome da série.
     * @return array de séries com o nome correspondente, ou {@code null} se não houver.
     * @throws Exception se ocorrer erro durante a leitura.
     */
    public Series[] readEntity(String nome) throws Exception {

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

    public Series[] readNome(String nome) throws Exception {

        if (nome.length() == 0)
            return null;

        // Instancia o Buscador com a Lista Invertida de séries
        Buscador buscador = new Buscador(listaInvertida, null, null);

        // Realiza a busca usando o Buscador
        List<Integer> idsEncontrados = buscador.buscarSeries(nome);

        System.out.println("IDs encontrados: " + idsEncontrados);
        
        // Se nenhum ID foi encontrado, retorna null
        if (idsEncontrados.isEmpty()) {
            return null;
        }

        // Lê as séries correspondentes aos IDs encontrados
        Series[] series = new Series[idsEncontrados.size()];
        int i = 0;
        for (Integer id : idsEncontrados) {
            series[i++] = read(id);
        }

        return series;
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
                indiceIndiretoNome.delete(new PairNameID(s.getName(), id));
                listaInvertida.delete(s.getName(), id); // Remove a série da ListaInvertida
                return true;
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
        Series s = read(novaSerie.getId());
        if (s != null) {
            if (super.update(novaSerie)) {
                if (!s.getName().equals(novaSerie.getName())) {
                    indiceIndiretoNome.delete(new PairNameID(s.getName(), s.getId()));
                    indiceIndiretoNome.create(new PairNameID(novaSerie.getName(), novaSerie.getId()));
                    listaInvertida.delete(s.getName(), s.getId()); // Remove o nome antigo da ListaInvertida
                    listaInvertida.create(novaSerie.getName(), new ElementoLista(novaSerie.getId(), 1)); // Adiciona o nome atualizado
                }
                return true;
            }
        }
        return false;
    }
}

