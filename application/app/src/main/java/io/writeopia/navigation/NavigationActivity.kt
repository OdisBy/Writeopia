package io.writeopia.navigation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.writeopia.sdk.network.injector.ApiInjector
import io.writeopia.sdk.persistence.database.WriteopiaDatabase
import io.writeopia.AndroidLogger
import io.writeopia.account.navigation.accountMenuNavigation
import io.writeopia.account.viewmodel.AccountMenuViewModel
import io.writeopia.auth.core.di.AuthCoreInjection
import io.writeopia.auth.core.token.AmplifyTokenHandler
import io.writeopia.auth.di.AuthInjection
import io.writeopia.auth.navigation.authNavigation
import io.writeopia.auth.navigation.navigateToAuthMenu
import io.writeopia.editor.di.EditorInjector
import io.writeopia.editor.navigation.editorNavigation
import io.writeopia.note_menu.di.NotesMenuInjection
import io.writeopia.note_menu.navigation.notesMenuNavigation
import io.writeopia.theme.ApplicationComposeTheme
import io.writeopia.utils_module.Destinations

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationGraph(application = application)
        }
    }
}

@Composable
fun NavigationGraph(
    application: Application,
    navController: NavHostController = rememberNavController(),
    database: WriteopiaDatabase = WriteopiaDatabase.database(application, builder = {
        this.createFromAsset("WriteopiaDatabase.db")
    }),
    sharedPreferences: SharedPreferences = application.getSharedPreferences(
        "io.writeopia.preferences",
        Context.MODE_PRIVATE
    ),
    startDestination: String = Destinations.AUTH_MENU_INNER_NAVIGATION.id
) {

    val apiInjector =
        ApiInjector(apiLogger = AndroidLogger, bearerTokenHandler = AmplifyTokenHandler)
    val authCoreInjection = AuthCoreInjection(sharedPreferences)
    val authInjection = AuthInjection(authCoreInjection, database, apiInjector)
    val editorInjector = EditorInjector(database, authCoreInjection)
    val notesMenuInjection =
        NotesMenuInjection(database, sharedPreferences, authCoreInjection.provideAccountManager())

    ApplicationComposeTheme {
        NavHost(navController = navController, startDestination = startDestination) {
            authNavigation(navController, authInjection, navController::navigateToMainMenu)

            notesMenuNavigation(
                notesMenuInjection = notesMenuInjection,
                navigateToNote = navController::navigateToNote,
                navigateToAccount = navController::navigateToAccount,
                navigateToNewNote = navController::navigateToNewNote
            )

            editorNavigation(
                editorInjector = editorInjector,
                navigateToNoteMenu = navController::navigateToNoteMenu
            )

            accountMenuNavigation(
                accountMenuViewModel = AccountMenuViewModel(
                    authCoreInjection.provideAccountManager(),
                    authCoreInjection.provideAuthRepository()
                ),
                navController::navigateToAuthMenu
            )
        }
    }
}