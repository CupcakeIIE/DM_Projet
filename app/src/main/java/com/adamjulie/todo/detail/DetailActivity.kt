package com.adamjulie.todo.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adamjulie.todo.detail.ui.theme.TodoAdamJulieTheme
import com.adamjulie.todo.list.Task
import java.util.UUID
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

//import androidx.compose.runtime.livedata.observeAsState

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAdamJulieTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Detail(
                        modifier = Modifier.padding(innerPadding),
                        onValidate = {
                            val intent = intent
                            intent.putExtra("task", it)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun Detail(onValidate: (Task) -> Unit, modifier: Modifier = Modifier) {
    var textDescription by remember { mutableStateOf("") }
    var textTitle by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp), Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Text Detail",
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier
        )
        OutlinedTextField(
            value = textTitle,
            onValueChange = { textTitle = it },
            label = { Text("Task Detail") },
            modifier = modifier,
        )
        OutlinedTextField(
            value = textDescription,
            onValueChange = { textDescription = it },
            label = { Text("Description") },
            modifier = modifier,
        )
        FilledTonalButton(onClick = {
            val newTask = Task(id = UUID.randomUUID().toString(), title = textTitle, description = textDescription)
            onValidate(newTask)
        }) {
            Text("Valider")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    TodoAdamJulieTheme {
        Detail(
            onValidate = {}
        )
    }
}