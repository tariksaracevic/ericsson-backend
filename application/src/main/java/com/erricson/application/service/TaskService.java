package com.erricson.application.service;

import com.erricson.application.exceptions.ResourceNotFoundException;
import com.erricson.application.model.TaskList;
import com.erricson.application.model.Task;
import com.erricson.application.repository.TaskListRepository;
import com.erricson.application.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Long taskListId, Task task) {
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid taskList ID: " + taskListId));

        task.setTaskList(taskList);

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAllByTaskList_Id(Long taskListId) {
        return taskRepository.findAllByTaskList_Id(taskListId);
    }

    @Transactional
    public Task updateTaskColumn(Long taskId, TaskList newTaskList) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        task.setTaskList(newTaskList);

        return taskRepository.save(task);
    }
}