package io.writeopia.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.writeopia.auth.core.manager.AuthManager
import io.writeopia.auth.intronotes.IntroNotesUseCase
import io.writeopia.utils_module.ResultData
import io.writeopia.utils_module.toBoolean
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/* The NavigationActivity won't leak because it is the single activity of the whole project*/
internal class LoginViewModel(
    private val introNotesUseCase: IntroNotesUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _loginState: MutableStateFlow<ResultData<Boolean>> =
        MutableStateFlow(ResultData.Idle())
    val loginState = _loginState.asStateFlow()

    fun emailChanged(name: String) {
        _email.value = name
    }

    fun passwordChanged(name: String) {
        _password.value = name
    }

    fun onLoginRequest() {
        _loginState.value = ResultData.Loading()

        viewModelScope.launch {
            val result = authManager.signIn(_email.value, _password.value)

            if (result.toBoolean()) {
                introNotesUseCase.addIntroNotes()
            }

            _loginState.value = result
        }
    }
}