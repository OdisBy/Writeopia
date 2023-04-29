package br.com.leandroferreira.storyteller.normalization.position

import br.com.leandroferreira.storyteller.model.StoryUnit

object PositionNormalization {

    fun normalizePosition(storySteps: Iterable<StoryUnit>): List<StoryUnit> =
        storySteps.mapIndexed { index, storyUnit  ->
            storyUnit.copyWithNewPosition(index)
        }
}
