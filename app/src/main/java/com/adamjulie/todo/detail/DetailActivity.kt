package com.adamjulie.todo.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    Text(
        text = "Hello CUPCACA!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    TodoAdamJulieTheme {
        Detail()
    }
}