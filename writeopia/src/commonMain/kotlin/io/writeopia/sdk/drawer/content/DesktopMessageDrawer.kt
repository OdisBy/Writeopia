package io.writeopia.sdk.drawer.content

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.writeopia.sdk.drawer.SimpleMessageDrawer
import io.writeopia.sdk.model.action.Action
import io.writeopia.sdk.model.draw.DrawInfo
import io.writeopia.sdk.models.story.StoryStep
import io.writeopia.sdk.text.edition.TextCommandHandler
import io.writeopia.sdk.utils.ui.defaultTextStyle

/**
 * Simple message drawer mostly intended to be used as a component for more complex drawers.
 * This class contains the logic of the basic message of the SDK. As many other drawers need some
 * text in it this Drawer can be used instead of duplicating this text logic.
 *
 * This is the intended version to be used with desktop and webapp, instead o the mobile version.
 */
class DesktopMessageDrawer(
    private val modifier: Modifier = Modifier,
    private val onKeyEvent: (KeyEvent, TextFieldValue, StoryStep, Int) -> Boolean = { _, _, _, _ -> false },
    private val textStyle: @Composable (StoryStep) -> TextStyle = { defaultTextStyle(it) },
    private val focusRequester: FocusRequester? = null,
    private val onTextEdit: (Action.StoryStateChange) -> Unit = { },
    private val commandHandler: TextCommandHandler = TextCommandHandler(emptyMap()),
    private val allowLineBreaks: Boolean = false,
    private val onLineBreak: (Action.LineBreak) -> Unit = {},
    override var onFocusChanged: (FocusState) -> Unit = {}
) : SimpleMessageDrawer {

    @Composable
    override fun Step(step: StoryStep, drawInfo: DrawInfo) {
        var inputText by remember {
            val text = step.text ?: ""
            mutableStateOf(TextFieldValue(text, TextRange(text.length)))
        }

        if (drawInfo.focusId == step.id) {
            LaunchedEffect(step.localId) {
                focusRequester?.requestFocus()
            }
        }

        if (drawInfo.editable) {
            BasicTextField(
                modifier = modifier
                    .padding(start = 16.dp)
                    .let { modifierLet ->
                        if (focusRequester != null) {
                            modifierLet.focusRequester(focusRequester)
                        } else {
                            modifierLet
                        }
                    }
                    .onKeyEvent { keyEvent ->
                        onKeyEvent(keyEvent, inputText, step, drawInfo.position)
                    }
                    .onFocusChanged(onFocusChanged),
                value = inputText,
                onValueChange = { value ->
                    val text = value.text
                    val newStep = step.copy(text = text)

                    inputText = if (text.contains("\n") && !allowLineBreaks) {
                        onLineBreak(Action.LineBreak(newStep, drawInfo.position))

                        val newText = text.split("\n", limit = 2)[0]
                        TextFieldValue(newText, TextRange(newText.length))
                    } else {
                        onTextEdit(Action.StoryStateChange(newStep, drawInfo.position))
                        commandHandler.handleCommand(text, newStep, drawInfo.position)

                        value
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                textStyle = textStyle(step),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            )
        } else {
            Text(
                text = step.text ?: "",
                modifier = modifier.padding(vertical = 5.dp),
            )
        }
    }
}
