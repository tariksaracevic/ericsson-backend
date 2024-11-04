package com.erricson.application.service;

import com.erricson.application.model.Board;
import com.erricson.application.model.TaskList;
import com.erricson.application.repository.BoardRepository;
import com.erricson.application.repository.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository, BoardRepository boardRepository) {
        this.taskListRepository = taskListRepository;
        this.boardRepository = boardRepository;
    }

    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    public Optional<TaskList> getTaskListById(Long id) {
        return taskListRepository.findById(id);
    }

    public TaskList createTaskList(Long boardId, TaskList taskList) {
        // Find the Board by boardId
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardId));

        // Set the Board on the TaskList
        taskList.setBoard(board);

        // Save the TaskList
        return taskListRepository.save(taskList);
    }

    public TaskList updateTaskList(Long id, TaskList updatedTaskList) {
        return taskListRepository.findById(id)
                .map(taskList -> {
                    taskList.setName(updatedTaskList.getName());
                    return taskListRepository.save(taskList);
                }).orElseThrow(() -> new RuntimeException("TaskList not found with id " + id));
    }

    public void deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
    }

    public List<TaskList> getAllByBoard(Long boardId) {
        return taskListRepository.findAllByBoard_Id(boardId);
    }
}