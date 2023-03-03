package com.example.todolist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.model.Todo
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodel.ToDoviewmodel
import com.example.todolist.viewmodel.TodoUIState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TodoApp()
                }
            }
        }
    }
}

@Composable
fun TodoApp(toDoviewmodel: ToDoviewmodel= viewModel()){
    ToDoScreen(uiState=toDoviewmodel.todoUIState)
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Todos") }
            )
        },
        content={
            ToDoScreen(uiState=toDoviewmodel.todoUIState)
        }
    )
}

@Composable
fun ToDoScreen(uiState: TodoUIState) {
    when(uiState){
        is TodoUIState.Loading -> LoadingScreen()
        is TodoUIState.Error -> ErrorScreen()
        is TodoUIState.Success ->ToDoList(uiState.todos)
    }
}
@Composable
fun LoadingScreen(){
    Text( "Loading...")
}

@Composable
fun ErrorScreen() {
    Text("Error retrieving data from the API.")
}

@Composable
fun ToDoList(todos: List<Todo>){
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ){
        items(todos) { todo ->
            Text(text = todo.title, modifier = Modifier.padding(top=4.dp, bottom=4.dp))
            Divider(color= Color.LightGray, thickness=1.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoListTheme {
        TodoApp()
    }
}