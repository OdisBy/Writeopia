package br.com.leandroferreira.storyteller.viewmodel.move

import br.com.leandroferreira.storyteller.normalization.addinbetween.AddInBetween
import br.com.leandroferreira.storyteller.utils.StoryData
import org.junit.Assert.*
import org.junit.Test

class SpaceMoveHandlerTest {

    @Test
    fun `it should be possible to switch two images position`() {
        // Images are in odd numbers
        val input = AddInBetween.spaces()
                .insert(StoryData.imagesInLine())
                .associateBy { it.localPosition }
        val firstImage = input[1]

        val result =
            SpaceMoveHandler().handleMove(input.toMutableMap(), firstImage!!.id, 4)

        assertEquals(result[4]!!.id, firstImage.id)
    }
}