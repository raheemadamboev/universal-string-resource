package xyz.teamgravity.universalstringresource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        private const val MIN_LENGTH = 3
    }

    private val _event = Channel<MainEvent>()
    val event: Flow<MainEvent> = _event.receiveAsFlow()

    var name: String by mutableStateOf("")
        private set

    fun onNameChange(value: String) {
        name = value
    }

    fun validateInputs() {
        viewModelScope.launch {
            if (name.length < MIN_LENGTH) {
                _event.send(MainEvent.ValidationError(UniversalText.Resource(id = R.string.error_min_length, MIN_LENGTH)))
            }
        }
    }

    sealed class MainEvent {
        data class ValidationError(val message: UniversalText) : MainEvent()
    }
}