package io.writeopia.note_menu.ui.screen.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writeopia.note_menu.ui.dto.DocumentUi
import io.writeopia.note_menu.viewmodel.ChooseNoteViewModel
import io.writeopia.note_menu.viewmodel.NotesArrangement
import io.writeopia.appresourcers.R
import io.writeopia.utils_module.ResultData
import io.writeopia.sdk.model.draw.DrawInfo
import io.writeopia.sdk.drawer.StoryStepDrawer
import io.writeopia.sdk.drawer.preview.CheckItemPreviewDrawer
import io.writeopia.sdk.drawer.preview.HeaderPreviewDrawer
import io.writeopia.sdk.drawer.preview.TextPreviewDrawer
import io.writeopia.sdk.drawer.preview.UnOrderedListItemPreviewDrawer
import io.writeopia.sdk.models.story.StoryTypes
import io.writeopia.sdk.uicomponents.SwipeBox

@Composable
internal fun Notes(
    chooseNoteViewModel: ChooseNoteViewModel,
    navigateToNote: (String, String) -> Unit,
    selectionListener: (String, Boolean) -> Unit,
) {
    when (val documents =
        chooseNoteViewModel.documentsState.collectAsStateWithLifecycle().value
    ) {
        is ResultData.Complete -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                val data = documents.data

                if (data.isEmpty()) {
                    NoNotesScreen()
                } else {
                    val arrangement by chooseNoteViewModel.notesArrangement
                        .collectAsStateWithLifecycle()

                    when (arrangement) {
                        NotesArrangement.GRID -> {
                            LazyGridNotes(
                                documents.data,
                                selectionListener = selectionListener,
                                onDocumentClick = navigateToNote
                            )
                        }

                        NotesArrangement.LIST -> {
                            LazyColumnNotes(
                                documents.data,
                                selectionListener = selectionListener,
                                onDocumentClick = navigateToNote
                            )
                        }

                        else -> {
                            LazyGridNotes(
                                documents.data,
                                selectionListener = selectionListener,
                                onDocumentClick = navigateToNote
                            )
                        }
                    }
                }
            }
        }

        is ResultData.Error -> {
            LaunchedEffect(key1 = true) {
                documents.exception.printStackTrace()
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Error!!"// stringResource(R.string.error_loading_notes)
                )
            }
        }

        is ResultData.Loading, is ResultData.Idle -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun LazyGridNotes(
    documents: List<DocumentUi>,
    onDocumentClick: (String, String) -> Unit,
    selectionListener: (String, Boolean) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.padding(6.dp),
        columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        content = {
            items(
                documents,
                key = { document -> document.hashCode() }
            ) { document ->
                DocumentItem(document, onDocumentClick, selectionListener, previewDrawers())
            }
        }
    )
}

@Composable
private fun LazyColumnNotes(
    documents: List<DocumentUi>,
    onDocumentClick: (String, String) -> Unit,
    selectionListener: (String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        content = {
            items(documents, key = { document -> document.hashCode() }) { document ->
                DocumentItem(document, onDocumentClick, selectionListener, previewDrawers())
            }
        }
    )
}

@Composable
private fun DocumentItem(
    documentUi: DocumentUi,
    documentClick: (String, String) -> Unit,
    selectionListener: (String, Boolean) -> Unit,
    drawers: Map<Int, StoryStepDrawer>
) {
    SwipeBox(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .clickable {
                documentClick(documentUi.documentId, documentUi.title)
            }
            .semantics {
                testTag = "$DOCUMENT_ITEM_TEST_TAG${documentUi.title}"
            },
        isOnEditState = documentUi.selected,
        swipeListener = {
            state -> selectionListener(documentUi.documentId, state)
        },
        cornersShape = MaterialTheme.shapes.large,
        defaultColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column {
            documentUi.preview.forEachIndexed { i, storyStep ->
                drawers[storyStep.type.number]?.Step(
                    step = storyStep,
                    drawInfo = DrawInfo(editable = false, position = i)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun NoNotesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(8.dp).align(Alignment.Center),
            text = stringResource(R.string.you_dont_have_notes),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun previewDrawers(): Map<Int, StoryStepDrawer> {
    val h1TextStyle = @Composable {
        TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
    val h2TextStyle = @Composable {
        TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
    val h3TextStyle = @Composable {
        TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
    val h4TextStyle = @Composable {
        TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }

    val unOrderedListItemPreviewDrawer = UnOrderedListItemPreviewDrawer()

    return mapOf(
        StoryTypes.TITLE.type.number to HeaderPreviewDrawer(
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
            )
        ),
        StoryTypes.CHECK_ITEM.type.number to CheckItemPreviewDrawer(),
        StoryTypes.TEXT.type.number to TextPreviewDrawer(),
        StoryTypes.UNORDERED_LIST_ITEM.type.number to unOrderedListItemPreviewDrawer,
        StoryTypes.H1.type.number to TextPreviewDrawer(style = h1TextStyle),
        StoryTypes.H2.type.number to TextPreviewDrawer(style = h2TextStyle),
        StoryTypes.H3.type.number to TextPreviewDrawer(style = h3TextStyle),
        StoryTypes.H4.type.number to TextPreviewDrawer(style = h4TextStyle),
    )
}