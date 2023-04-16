package br.com.storyteller.utils

import br.com.storyteller.model.StoryStep

object StoryData {

    fun imageStepsList(): List<StoryStep> = buildList {
        add(
            StoryStep(
                id = "1",
                type = "image",
                localPosition = 1
            )
        )
        add(
            StoryStep(
                id = "2",
                type = "image",
                localPosition = 2
            )
        )
        add(
            StoryStep(
                id = "3",
                type = "image",
                localPosition = 3
            )
        )
    }

    fun stepsList(): List<StoryStep> = buildList {
        add(
            StoryStep(
                id = "1",
                type = "image",
                localPosition = 1
            )
        )
        add(
            StoryStep(
                id = "2",
                type = "image",
                localPosition = 2
            )
        )
        add(
            StoryStep(
                id = "3",
                type = "image",
                localPosition = 3
            )
        )
        add(
            StoryStep(
                id = "4",
                type = "message",
                localPosition = 4
            )
        )
        add(
            StoryStep(
                id = "5",
                type = "message",
                localPosition = 5
            )
        )
    }
}