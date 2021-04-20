package com.ccand99.demo.state


import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    //private state
    private var currentEditPosoition by mutableStateOf(-1)

    // state: todoItems
    var todoItems: List<TodoItem> by mutableStateOf(listOf())
    private set

    val currentEditItem: TodoItem?
    get() = todoItems.getOrNull(currentEditPosoition)

    // event: addItem
    fun addItem(item: TodoItem) {
        todoItems = todoItems + listOf(item)
    }

    // event: removeItem
    fun removeItem(item: TodoItem) {
        todoItems = todoItems.toMutableList().also { it.remove(item) }
        onEditDone()
    }

    // event: onEditItemSelected
    fun onEditItemSelected(item: TodoItem) {
        currentEditPosoition = todoItems.indexOf(item)
    }

    //event
    fun onEditDone() {
        currentEditPosoition = -1
    }

    //event
    fun onEditItemChange(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        todoItems = todoItems.toMutableList().also {
            it[currentEditPosoition] = item
        }
    }
}