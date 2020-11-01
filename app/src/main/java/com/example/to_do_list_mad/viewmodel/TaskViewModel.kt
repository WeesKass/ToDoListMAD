package com.example.to_do_list_mad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.to_do_list_mad.model.Task
import com.example.to_do_list_mad.repository.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository
    val allTasks: LiveData<List<Task>>
    fun insert(task: Task?) {
        taskRepository.insert(task)
    }

    fun deleteAll() {
        taskRepository.deleteAll()
    }

    fun deleteTask(id: Int) {
        taskRepository.deleteTask(id)
    }

    fun update(id: Int, taskName: String?, priority: String?, date: String?) {
        taskRepository.update(id, taskName, priority, date)
    }

    init {
        taskRepository = TaskRepository(application)
        allTasks = taskRepository.allTasks
    }
}