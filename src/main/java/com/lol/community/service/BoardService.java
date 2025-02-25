package com.lol.community.service;

import com.lol.community.entity.Board;
import com.lol.community.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private static final String UPLOAD_DIR = "./uploads/";

    public Board createBoard(String title, String content, MultipartFile file) throws IOException {
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            Path filePath = Paths.get(UPLOAD_DIR, newFileName);
            file.transferTo(filePath.toFile());
            imageUrl = "/uploads/" + newFileName;
        }
        Board board = Board.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build();
        return boardRepository.save(board);
    }

    // 업데이트 메서드: 제목, 내용과 함께 파일(이미지)도 업데이트
    public Board updateBoard(Long id, String title, String content, MultipartFile file) throws IOException {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        board.setTitle(title);
        board.setContent(content);
        if (file != null && !file.isEmpty()) {
            // 새로운 파일 저장 처리
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            Path filePath = Paths.get(UPLOAD_DIR, newFileName);
            file.transferTo(filePath.toFile());
            board.setImageUrl("/uploads/" + newFileName);
        }
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
