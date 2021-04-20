package com.ccand99.demo.state

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.ccand99.demo.ui.theme.AppTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview

class TodoActivity: AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme() {
                Surface {
                    TodoActivityScreen(todoViewModel)
                }
            }
        }
    }
}

@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    //val items = listOf<TodoItem>() // in the next steps we'll complete this
    //observe
    //val items : List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())
    TodoScreen(
        items = todoViewModel.todoItems,
        currentlyEditing = todoViewModel.currentEditItem,
        onAddItem =  todoViewModel::addItem, // in the next steps we'll complete this
        onRemoveItem = todoViewModel::removeItem, // in the next steps we'll complete this
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange =todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone
    )
}

@Preview
@Composable
fun TodoPreview() {
}