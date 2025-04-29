package tp02.src.data;

import java.util.ArrayList;

import tp02.src.models.Episode;
import tp02.src.storage.indexes.*;
import tp02.src.storage.structures.*;

/**
 * Classe responsável pela manipulação dos episódios,
 * incluindo persistência, leitura e gerenciamento de índices.
 */
public class ArchiveEpisode extends Archive<Episode> {

    /** Índice indireto baseado no nome do episódio. */
    ArchiveTreeB<PairNameID> indiceIndiretoNome;

    /** Índice indireto relacionando o ID do episódio com o ID da série (chave estrangeira). */
    ArchiveTreeB<PairIDFK> relacaoNN;

    /**
     * Construtor padrão que inicializa os arquivos de episódios e os índices auxiliares.
     * 
     * @throws Exception caso ocorra falha na criação dos arquivos ou índices.
     */
    public ArchiveEpisode() throws Exception {

        super("episodios", Episode.class.getConstructor());

        indiceIndiretoNome = new ArchiveTreeB<>(
                PairNameID.class.getConstructor(), 5, "tp02/files/episodios/indiceTitulo.db");

        relacaoNN = new ArchiveTreeB<>(PairIDFK.class.getConstructor(), 5, "tp02/files/episodios/relacaoNN.db");
    }

    /**
     * Cria um novo episódio, armazenando-o e atualizando os índices.
     * 
     * @param e episódio a ser criado.
     * @return o ID gerado para o episódio.
     * @throws Exception caso ocorra erro durante o armazenamento ou indexação.
     */
    @Override
    public int create(Episode e) throws Exception {
        int id = super.create(e);
        indiceIndiretoNome.create(new PairNameID(e.getName(), id));
        relacaoNN.create(new PairIDFK(e.getId(), e.getFkSerie()));
        return id;
    }

    /**
     * Retorna todos os episódios associados a uma determinada série.
     * 
     * @param fkSeries chave estrangeira da série.
     * @return array de episódios vinculados à série ou {@code null} se não houver.
     * @throws Exception caso ocorra erro durante a leitura.
     */
    public Episode[] readFkSerie(int fkSeries) throws Exception { // Faz a busca somente dentro de epsódios
        ArrayList<PairIDFK> pares = relacaoNN.read(new PairIDFK(fkSeries));
        
        if (pares.size() > 0) {

            Episode[] episodios = new Episode[pares.size()];

            int i = 0;

            for (PairIDFK par : pares) {

                episodios[i++] = read(par.getId());

            }

            return episodios;

        } else {
            return null;
        }
    }

    /**
     * Retorna os episódios com o nome especificado.
     * 
     * @param nome nome do episódio.
     * @return array de episódios com o nome correspondente ou {@code null} se não houver.
     * @throws Exception caso ocorra erro durante a leitura.
     */
    public Episode[] readNome(String nome) throws Exception { // Faz a busca somente dentro de epsódios

        if (nome.length() == 0)
            return null;

        ArrayList<PairNameID> pares = indiceIndiretoNome.read(new PairNameID(nome, -1));

        if (pares.size() > 0) {

            Episode[] episodios = new Episode[pares.size()];

            int i = 0;

            for (PairNameID par : pares) {

                episodios[i++] = read(par.getId());

            }

            return episodios;

        } else {
            return null;
        }

    }

    /**
     * Retorna os episódios que pertencem a uma série e possuem determinado nome.
     * 
     * @param fkSerie chave estrangeira da série.
     * @param nome nome do episódio.
     * @return array de episódios filtrados ou {@code null} se não houver.
     * @throws Exception caso ocorra erro durante a leitura.
     */
    public Episode[] readEpisodiosPorSerieENome(int fkSerie, String nome) throws Exception {
        if (nome.length() == 0)
            return null;

        Episode[] episodiosDaSerie = readFkSerie(fkSerie);
        if (episodiosDaSerie == null || episodiosDaSerie.length == 0)
            return null;

        ArrayList<PairNameID> paresNome = indiceIndiretoNome.read(new PairNameID(nome, -1));

        ArrayList<Episode> episodiosFiltrados = new ArrayList<>();

        for (PairNameID parNome : paresNome) {
            for (Episode ep : episodiosDaSerie) {
                if (ep.getId() == parNome.getId()) {
                    episodiosFiltrados.add(ep);
                    break; 
                }
            }
        }

        return episodiosFiltrados.isEmpty() ? null : episodiosFiltrados.toArray(new Episode[0]);
    }


    /**
     * Exclui um episódio e atualiza os índices relacionados.
     * 
     * @param id identificador do episódio a ser removido.
     * @return {@code true} se a exclusão for bem-sucedida, {@code false} caso contrário.
     * @throws Exception caso ocorra erro durante a exclusão.
     */
    @Override
    public boolean delete(int id) throws Exception {
        Episode e = super.read(id);
        if (e != null) {
            if (super.delete(id)) {
                return indiceIndiretoNome.delete(new PairNameID(e.getName(), id)) && relacaoNN.delete(new PairIDFK(e.getId(), e.getFkSerie()));
            }
        }
        return false;
    }

    /**
     * Atualiza um episódio existente, ajustando os índices caso o nome tenha sido alterado.
     * 
     * @param novaEpisodio objeto com os novos dados do episódio.
     * @return {@code true} se a atualização for bem-sucedida, {@code false} caso contrário.
     * @throws Exception caso ocorra erro durante a atualização.
     */
    @Override
    public boolean update(Episode novaEpisodio) throws Exception {
        Episode e = read(novaEpisodio.getId()); // na superclasse
        if (e != null) {
            if (super.update(novaEpisodio)) {
                if (!e.getName().equals(novaEpisodio.getName())) {
                    indiceIndiretoNome.delete(new PairNameID(e.getName(), e.getId()));
                    relacaoNN.delete(new PairIDFK(e.getId(), e.getFkSerie()));
                    indiceIndiretoNome.create(new PairNameID(novaEpisodio.getName(), novaEpisodio.getId()));
                    relacaoNN.create(new PairIDFK(e.getId(), e.getFkSerie()));
                }
                return true;
            }
        }
        return false;
    }

}
