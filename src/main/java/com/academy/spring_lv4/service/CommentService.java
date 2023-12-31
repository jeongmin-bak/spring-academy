package com.academy.spring_lv4.service;

import com.academy.spring_lv4.dto.comment.CommentRequestDto;
import com.academy.spring_lv4.entity.Comment;
import com.academy.spring_lv4.entity.Lecture;
import com.academy.spring_lv4.entity.User;
import com.academy.spring_lv4.repository.CommentRepository;
import com.academy.spring_lv4.repository.LectureRepository;
import com.academy.spring_lv4.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    public ResponseEntity createComment(Long lectureId, CommentRequestDto requestDto, Long userId) {
        // dto -> entity
        Comment comment = new Comment(requestDto);

        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다."));

        // 강의 정보
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new NullPointerException("강의가 존재하지 않습니다."));

        comment.setUserId(user);
        comment.setLectureId(lecture);

        // DB 저장
        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.OK).body("댓글을 작성하였습니다.");
    }

    @Transactional
    public ResponseEntity editComment(Long commentId, CommentRequestDto requestDto, Long userId) {
        // 댓글 찾기
        Comment comment = findByComment(commentId);

        if (userId != comment.getUserId().getId()) {
            throw new IllegalArgumentException("댓글을 작성한 유저가 아닙니다.");
        }

        comment.update(requestDto.getContents());
        return ResponseEntity.status(HttpStatus.OK).body("댓글을 수정하였습니다.");
    }

    public ResponseEntity deleteComment(Long commentId, Long userId) {
        // 댓글 찾기
        Comment comment = findByComment(commentId);

        if (userId != comment.getUserId().getId()) {
            throw new IllegalArgumentException("댓글을 작성한 유저가 아닙니다.");
        }

        commentRepository.delete(comment);
        return ResponseEntity.status(HttpStatus.OK).body("댓글을 삭제하였습니다.");
    }

    private Comment findByComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾지 못했습니다."));
    }
}
