package com.adamjulie.todo.detail

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adamjulie.todo.detail.ui.theme.TodoAdamJulieTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAdamJulieTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Detail(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Detail(modifier: Modifier = Modifier) {
    Column (modifier = Modifier.padding(16.dp), Arrangement.spacedBy(16.dp)){
        Text(
            text = "Task Detail",
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier
        )
        Text(
            text = "Title",
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier
        )
        Text(
            text = "Description",
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier
        )
        FilledTonalButton(onClick = {})  {
            Text("Valider")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    TodoAdamJulieTheme {
        Detail()
    }
}