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
            ArrayList<PairIDFK> existingActorRelations = actorSerie.read(new PairIDFK(idActor, -1)); // Busca todas as séries associadas ao ator (fk = -1 indica qualquer série)
            for (PairIDFK relation : existingActorRelations) {
                if (relation.getFk() == idSerie) {
                    System.out.println("Relação já existente: Ator ID " + idActor + " -> Série ID " + idSerie);
                    return; // Skip creating duplicate relationship
                }
            }

            // Check if the relationship already exists in serieActor
            ArrayList<PairIDFK> existingSerieRelations = serieActor.read(new PairIDFK(idSerie, -1)); // Busca todas os atores associados à série (fk = -1 indica qualquer ator)
            for (PairIDFK relation : existingSerieRelations) {
                if (relation.getFk() == idActor) {
                    System.out.println("Relação já existente: Série ID " + idSerie + " -> Ator ID " + idActor);
                    return; // Skip creating duplicate relationship
                }
            }

            // Create the relationship if it does not already exist
            actorSerie.create(new PairIDFK(idActor, idSerie)); // Store actor -> series relationship
            serieActor.create(new PairIDFK(idSerie, idActor)); // Store series -> actor relationship
            System.out.println("\n######################################################");
            System.out.println("DEBUG");
            System.out.println("------------------------------------------------------");
            System.out.println("\nRelação criada: Ator ID " + idActor + " -> Série ID " + idSerie);
            System.out.println("\nDados armazenados em actorSerie e serieActor:");
            System.out.println("\n-> actorSerie: " + actorSerie.read(new PairIDFK(idActor, -1))); // Debug actorSerie
            System.out.println("-> serieActor: " + serieActor.read(new PairIDFK(idSerie, -1))); // Debug serieActor
            System.out.println("######################################################");
        } else {
            System.err.println("\nErro: IDs inválidos fornecidos para criar relação. Ator ID: " + idActor + ", Série ID: " + idSerie);
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
        
        if (relations == null) {
            return null;
        } else {
            System.out.println("Quantidade: " + relations.size() + "\n"); // Debug statement}
        }

        /* for (PairIDFK relation : relations) {
            System.out.println("Relacionamento: Série ID " + idSerie + " -> Ator ID " + relation.getFk()); // Debug statement
        } */
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

        if (relations == null || relations.size() <= 0) {
            return null;
        } else {
            System.out.println("Quantidade: " + relations.size() + "\n"); // Debug statement}
        }

        /* for (PairIDFK relation : relations) {
            System.out.println("Relacionamento: Ator ID " + idActor + " -> Série ID " + relation.getFk()); // Debug statement
        } */
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

    /**
     * Exclui todas as relações entre uma série e os atores associados a ela.
     *
     * @param idSerie O ID da série cujas relações com atores devem ser excluídas.
     * @return Um valor booleano que indica se todas as exclusões foram realizadas
     *         com sucesso (`true`) ou se houve falha em alguma exclusão (`false`).
     * @throws Exception Se ocorrer um erro durante a operação de leitura ou exclusão.
     */
    public boolean deleteAllRelations(int idSerie) throws Exception {
        boolean allDeleted = true;
        
        // Remove todas as relações da série com atores nas estruturas actorSerie e serieActor
        ArrayList<PairIDFK> existingSerieRelations = serieActor.read(new PairIDFK(idSerie, -1));
        if (existingSerieRelations != null && !existingSerieRelations.isEmpty()) {
            for (PairIDFK relation : existingSerieRelations) {
                allDeleted = allDeleted && actorSerie.delete(new PairIDFK(relation.getFk(), idSerie)); // Remove ator → série
                allDeleted = allDeleted && serieActor.delete(relation); // Remove série → ator
            }
        }

        return allDeleted; 
    }
}
