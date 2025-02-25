package com.lol.community.controller;

import com.lol.community.entity.Board;
import com.lol.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성 (이미지 포함)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Board> createBoard(@RequestParam String title,
                                             @RequestParam String content,
                                             @RequestParam(required = false) MultipartFile file) throws IOException {
        Board board = boardService.createBoard(title, content, file);
        return ResponseEntity.ok(board);
    }

    // 게시글 업데이트 (텍스트와 이미지 모두 수정 가능)
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Board> updateBoard(@PathVariable Long id,
                                             @RequestParam String title,
                                             @RequestParam String content,
                                             @RequestParam(required = false) MultipartFile file) throws IOException {
        Board updatedBoard = boardService.updateBoard(id, title, content, file);
        return ResponseEntity.ok(updatedBoard);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok("Board deleted successfully");
    }
}
