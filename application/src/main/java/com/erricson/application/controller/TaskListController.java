package com.erricson.application.controller;

import com.erricson.application.model.TaskList;
import com.erricson.application.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskListController {

    private final TaskListService taskListService;

    @Autowired
    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping("/{board_id}/task-lists/{id}")
    public ResponseEntity<TaskList> getTaskListById(@PathVariable("board_id") Long boardId, @PathVariable Long id) {
        return taskListService.getTaskListById(id)
                .map(taskList -> ResponseEntity.ok().body(taskList))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{board_id}/task-lists")
    public TaskList createTaskList(@PathVariable("board_id") Long boardId, @RequestBody TaskList taskList) {
        return taskListService.createTaskList(boardId, taskList);
    }

    @PutMapping("/{board_id}/task-lists/{id}")
    public ResponseEntity<TaskList> updateTaskList(@PathVariable("board_id") Long boardId, @PathVariable Long id, @RequestBody TaskList updatedTaskList) {
        return new ResponseEntity<>(taskListService.updateTaskList(id, updatedTaskList), HttpStatus.OK);
    }

    @DeleteMapping("/{board_id}/task-lists/{id}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable("board_id") Long boardId, @PathVariable Long id) {
        taskListService.deleteTaskList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{board_id}/task-lists")
    public List<TaskList> getTaskListsByBoard(@PathVariable("board_id") Long boardId) {
        return taskListService.getAllByBoard(boardId);
    }
}

