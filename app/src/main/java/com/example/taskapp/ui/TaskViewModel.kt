package com.example.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.model.Task

class TaskViewModel : ViewModel() {


    private val _taskInsert = MutableLiveData <Task> ()
    val taskInsert : LiveData <Task> = _taskInsert

    private val _taskUpdate = MutableLiveData <Task> ()
    val taskUpdate : LiveData <Task> = _taskUpdate


    fun  setUpdateTask (task: Task) {
        _taskUpdate.value = task
    }



}