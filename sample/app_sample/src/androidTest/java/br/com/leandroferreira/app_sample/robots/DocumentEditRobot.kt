package br.com.leandroferreira.app_sample.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.leandroferreira.editor.NAVIGATE_BACK_TEST_TAG
import br.com.leandroferreira.editor.NOTE_EDITION_SCREEN_TITLE_TEST_TAG
import com.github.leandroborgesferreira.storyteller.drawer.content.TITLE_DRAWER_TEST_TAG

class DocumentEditRobot(private val composeTestRule: ComposeTestRule) {

    fun verifyItIsInEdition() {
        composeTestRule.onNodeWithTag(NOTE_EDITION_SCREEN_TITLE_TEST_TAG).assertIsDisplayed()
    }

    fun writeTitle(text: String) {
        composeTestRule.onNodeWithTag(TITLE_DRAWER_TEST_TAG).performTextInput(text)
    }

    fun goBack() {
        composeTestRule.onNodeWithTag(NAVIGATE_BACK_TEST_TAG).performClick()
    }
}