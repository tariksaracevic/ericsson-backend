package com.erricson.application.controller;

import com.erricson.application.exceptions.ResourceNotFoundException;
import com.erricson.application.model.Task;
import com.erricson.application.model.TaskList;
import com.erricson.application.service.TaskListService;
import com.erricson.application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardId}/task-lists/{taskListId}/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    private final TaskService taskService;
    private final TaskListService taskListService;

    @Autowired
    public TaskController(TaskService taskService, TaskListService taskListService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable Long boardId,
            @PathVariable Long taskListId,
            @PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @PathVariable Long boardId,
            @PathVariable Long taskListId,
            @RequestBody Task task) {
        Task createdTask = taskService.createTask(taskListId, task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long boardId,
            @PathVariable Long taskListId,
            @PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<Task> getTasks(
            @PathVariable Long boardId,
            @PathVariable Long taskListId) {
        return taskService.getAllByTaskList_Id(taskListId);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTaskColumn(
            @PathVariable Long boardId,
            @PathVariable Long taskListId,
            @PathVariable Long taskId
    ) {
        TaskList taskList = taskListService.getTaskListById(taskListId)
                .orElseThrow(() -> new ResourceNotFoundException("TaskList not found with id " + taskListId));

        Task updatedTask = taskService.updateTaskColumn(taskId, taskList);

        return ResponseEntity.ok(updatedTask);
    }
}
