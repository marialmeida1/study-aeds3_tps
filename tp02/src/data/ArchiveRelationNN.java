package tp02.src.data;

import java.util.ArrayList;

import tp02.src.storage.indexes.*;
import tp02.src.storage.structures.*;

/**
 * Classe responsável pelo controle de relações entre IDs,
 * como ator-série ou série-ator.
 * Trabalha apenas com o índice de relação (relacaoNN).
 */
public class ArchiveRelationNN {

    /** Relação de ator para série. */
    private ArchiveTreeB<PairIDFK> actorSerie;

    /** Relação de série para ator. */
    private ArchiveTreeB<PairIDFK> serieActor;

    /**
     * Construtor que inicializa os arquivos de controle de relações.
     * 
     * @throws Exception caso ocorra falha na criação dos arquivos ou índices.
     */
    public ArchiveRelationNN() throws Exception {
        actorSerie = new ArchiveTreeB<>(PairIDFK.class.getConstructor(), 5, "tp02/files/actor_serie.db");
        serieActor = new ArchiveTreeB<>(PairIDFK.class.getConstructor(), 5, "tp02/files/serie_actor.db");
    }

    /**
     * Cria uma relação entre ator e série e também entre série e ator.
     * 
     * @param idActor ID do ator.
     * @param idSerie ID da série.
     * @throws Exception caso ocorra erro durante a criação das relações.
     */
    public void createRelation(int idActor, int idSerie) throws Exception {
        actorSerie.create(new PairIDFK(idActor, idSerie));
        serieActor.create(new PairIDFK(idSerie, idActor));
    }

    /**
     * Lê todas as séries associadas a um ator.
     * 
     * @param idActor ID do ator.
     * @return lista de relações ator -> série.
     * @throws Exception caso ocorra erro durante a leitura.
     */
    public ArrayList<PairIDFK> readSeriesByActor(int idActor) throws Exception {
        return actorSerie.read(new PairIDFK(idActor));
    }

    /**
     * Lê todos os atores associados a uma série.
     * 
     * @param idSerie ID da série.
     * @return lista de relações série -> ator.
     * @throws Exception caso ocorra erro durante a leitura.
     */
    public ArrayList<PairIDFK> readActorsBySerie(int idSerie) throws Exception {
        return serieActor.read(new PairIDFK(idSerie));
    }

    /**
     * Deleta uma relação específica entre ator e série.
     * 
     * @param idActor ID do ator.
     * @param idSerie ID da série.
     * @return {@code true} se a exclusão for bem-sucedida.
     * @throws Exception caso ocorra erro durante a exclusão.
     */
    public boolean deleteRelation(int idActor, int idSerie) throws Exception {
        boolean deletedActorSerie = actorSerie.delete(new PairIDFK(idActor, idSerie));
        boolean deletedSerieActor = serieActor.delete(new PairIDFK(idSerie, idActor));
        return deletedActorSerie && deletedSerieActor;
    }
}
