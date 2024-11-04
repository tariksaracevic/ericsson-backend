package com.erricson.application.service;

import com.erricson.application.model.Board;
import com.erricson.application.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Board updateBoard(Long id, Board updatedBoard) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setName(updatedBoard.getName());
                    return boardRepository.save(board);
                }).orElseThrow(() -> new RuntimeException("Board not found with id " + id));
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}