package io.writeopia.note_menu.data.usecase

import io.writeopia.sdk.manager.DocumentRepository
import io.writeopia.sdk.models.document.Document
import io.writeopia.sdk.models.id.GenerateId

/**
 * UseCase responsible to perform CRUD operations in the Notes (Documents) of the app taking in to
 * consideration the configuration desired in the app.
 */
internal class NotesUseCase(
    private val documentRepository: DocumentRepository,
    private val notesConfig: NotesConfigurationRepository
) {

    suspend fun loadDocumentsForUser(userId: String): List<Document> =
        notesConfig.getOrderPreference()
            ?.let { orderBy -> documentRepository.loadDocumentsForUser(orderBy, userId) }!!

    suspend fun duplicateDocuments(ids: List<String>) {
        notesConfig.getOrderPreference()?.let { orderBy ->
            documentRepository.loadDocumentsById(ids, orderBy)
        }?.let { documents ->
            documents.map { document ->
                document.copy(
                    id = GenerateId.generate(),
                    content = document.content.mapValues { (_, storyStep) ->
                        storyStep.copy(id = GenerateId.generate())
                    })
            }
        }?.let { newDocuments ->
            newDocuments.forEach { document ->
                documentRepository.saveDocument(document)
            }
        }
    }

    suspend fun deleteNotes(ids: Set<String>) {
        documentRepository.deleteDocumentById(ids)
    }
}
