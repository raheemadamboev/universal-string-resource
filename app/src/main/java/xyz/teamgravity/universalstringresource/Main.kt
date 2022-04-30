package xyz.teamgravity.universalstringresource

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

class Main : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewmodel = viewModel<MainViewModel>()
                val scaffold = rememberScaffoldState()
                val context = LocalContext.current

                LaunchedEffect(key1 = viewmodel.event) {
                    viewmodel.event.collectLatest { event ->
                        when (event) {
                            is MainViewModel.MainEvent.ValidationError -> {
                                scaffold.snackbarHostState.showSnackbar(message = event.message.asString(context))
                            }
                        }
                    }
                }

                Scaffold(scaffoldState = scaffold) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            value = viewmodel.name,
                            onValueChange = viewmodel::onNameChange
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = viewmodel::validateInputs,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(text = stringResource(id = R.string.validate))
                        }
                    }
                }
            }
        }
    }
}