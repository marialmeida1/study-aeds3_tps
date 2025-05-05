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
        if (idActor > 0 && idSerie > 0) { // Validate IDs to avoid unintended relationships
            // Check if the relationship already exists in actorSerie
            ArrayList<PairIDFK> existingActorRelations = actorSerie.read(new PairIDFK(idActor, -1));
            for (PairIDFK relation : existingActorRelations) {
                if (relation.getFk() == idSerie) {
                    System.out.println("Relação já existente: Ator ID " + idActor + " -> Série ID " + idSerie);
                    return; // Skip creating duplicate relationship
                }
            }

            // Check if the relationship already exists in serieActor
            ArrayList<PairIDFK> existingSerieRelations = serieActor.read(new PairIDFK(idSerie, -1));
            for (PairIDFK relation : existingSerieRelations) {
                if (relation.getFk() == idActor) {
                    System.out.println("Relação já existente: Série ID " + idSerie + " -> Ator ID " + idActor);
                    return; // Skip creating duplicate relationship
                }
            }

            // Create the relationship if it does not already exist
            actorSerie.create(new PairIDFK(idActor, idSerie)); // Store actor -> series relationship
            serieActor.create(new PairIDFK(idSerie, idActor)); // Store series -> actor relationship
            System.out.println("Relação criada: Ator ID " + idActor + " -> Série ID " + idSerie);
            System.out.println("Debug: Dados armazenados em actorSerie e serieActor:");
            System.out.println("actorSerie: " + actorSerie.read(new PairIDFK(idActor, -1))); // Debug actorSerie
            System.out.println("serieActor: " + serieActor.read(new PairIDFK(idSerie, -1))); // Debug serieActor
        } else {
            System.err.println("Erro: IDs inválidos fornecidos para criar relação. Ator ID: " + idActor + ", Série ID: " + idSerie);
        }
    }

    /**
     * Lê todas os atores associados a uma série.
     * 
     * @param idSerie ID da série.
     * @return lista de relações série -> ator.
     * @throws Exception caso ocorra erro durante a leitura.
     */
    public ArrayList<PairIDFK> readActorsBySerie(int idSerie) throws Exception {
        ArrayList<PairIDFK> relations = serieActor.read(new PairIDFK(idSerie, -1)); // Retrieve series -> actor relationships
        System.out.println("Relações encontradas para Série ID " + idSerie + ": " + (relations != null ? relations.size() : 0));
        for (PairIDFK relation : relations) {
            System.out.println("Relacionamento encontrado: Série ID " + idSerie + " -> Ator ID " + relation.getFk()); // Debug statement
        }
        return relations;
    }

    /**
     * Lê todas as séries associadas a um ator.
     * 
     * @param idActor ID do ator.
     * @return lista de relações ator -> série.
     * @throws Exception caso ocorra erro durante a leitura.
     */
    public ArrayList<PairIDFK> readSeriesByActor(int idActor) throws Exception {
        ArrayList<PairIDFK> relations = actorSerie.read(new PairIDFK(idActor, -1)); // Ensure the correct key is used
        System.out.println("Relações encontradas para Ator ID " + idActor + ": " + (relations != null ? relations.size() : 0)); // Debug statement
        for (PairIDFK relation : relations) {
            System.out.println("Relacionamento encontrado: Ator ID " + idActor + " -> Série ID " + relation.getFk()); // Debug statement
        }
        return relations;
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
