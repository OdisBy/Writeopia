package io.writeopia.sdk

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.writeopia.sdk.draganddrop.target.DraggableScreen
import io.writeopia.sdk.model.draw.DrawInfo
import io.writeopia.sdk.drawer.StoryStepDrawer
import io.writeopia.sdk.model.story.DrawState

@Composable
fun WriteopiaEditorBox(
    modifier: Modifier = Modifier,
    editable: Boolean = true,
    drawers: Map<Int, StoryStepDrawer>,
    storyState: DrawState = DrawState(emptyMap())
) {
    DraggableScreen(modifier = modifier) {
        Column(
            modifier = modifier,
            content = {
                storyState.stories.values.toList().forEachIndexed { index, drawStory ->
                    drawers[drawStory.storyStep.type.number]?.Step(
                        step = drawStory.storyStep,
                        drawInfo = DrawInfo(
                            editable = editable,
                            focusId = storyState.focusId,
                            position = index,
                            extraData = mapOf("listSize" to storyState.stories.size),
                            selectMode = drawStory.isSelected
                        )
                    )
                }
            }
        )
    }
}
