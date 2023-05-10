package br.com.leandroferreira.app_sample.screens.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leandroferreira.storyteller.manager.StoryTellerManager
import br.com.leandroferreira.storyteller.model.document.Document
import br.com.leandroferreira.storyteller.model.story.StoryState
import br.com.leandroferreira.storyteller.persistence.repository.DocumentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    val storyTellerManager: StoryTellerManager,
    private val documentRepository: DocumentRepository
) : ViewModel() {

    private val _editModeState = MutableStateFlow(true)
    val editModeState: StateFlow<Boolean> = _editModeState

    val story: StateFlow<StoryState> = storyTellerManager.normalizedStepsState

    private var _documentState: MutableStateFlow<Document?> = MutableStateFlow(null)

    fun toggleEdit() {
        _editModeState.value = !_editModeState.value
    }

    fun requestDocumentContent(documentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val document = documentRepository.loadDocumentBy(documentId)
            val content = document?.content
            _documentState.value = document

            if (content != null) {
                storyTellerManager.addStories(content)
            }
        }
    }

    fun updateState() {
        storyTellerManager.updateState()
    }

    fun saveNote() {
        updateState()

        _documentState.value?.let { document ->
            viewModelScope.launch {
                documentRepository.saveDocument(document.copy(content = story.value.stories))
            }
        }
    }

}