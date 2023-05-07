package br.com.leandroferreira.app_sample.screens.addstory

import android.content.Context
import br.com.leandroferreira.app_sample.data.syncHistory
import br.com.leandroferreira.storyteller.model.story.StoryUnit
import br.com.leandroferreira.storyteller.repository.StoriesRepository

class StoriesRepo(private val context: Context): StoriesRepository {

    override suspend fun history(): Map<Int, StoryUnit> = syncHistory(context)

}
