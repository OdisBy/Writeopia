package br.com.leandroferreira.storyteller.repository

import br.com.leandroferreira.storyteller.model.StoryUnit

interface StoriesRepository {

    suspend fun history(): List<StoryUnit>
}