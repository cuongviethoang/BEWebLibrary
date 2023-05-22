package com.project.projectBook.controller;

import com.project.projectBook.dto.CommentDto;
import com.project.projectBook.exception.ErrorMessage;
import com.project.projectBook.exception.ResourceNotFoundException;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.Comment;
import com.project.projectBook.payload.response.MessageResponse;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.CommentRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.CommentService;
import com.project.projectBook.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    ModelMapper modelMapper;



    // http://localhost:8082/api/book/{id}/comments
    @GetMapping("/book/{bookId}/comments")
    public ResponseEntity<?> getAllCommentOfBook(@PathVariable(value = "bookId") Long bookId) {
        List<CommentDto> commentDtos = commentService.getAllCommentOfBook(bookId);
        return ResponseEntity.ok(commentDtos);
    }

    // http://localhost:8082/api/book/{id}/comment
    @CrossOrigin
    @PostMapping("/book/{bookId}/comment")
    public ResponseEntity<?> createComment(Authentication authentication,
            @PathVariable(value = "bookId") Long bookId,
            @RequestBody Comment comment) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        commentService.createCommentOfBook(userDetails.getId(),bookId, comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // http://localhost:8082/api/book/{id}/comment/{id}
    @DeleteMapping("/book/{bookId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(Authentication authentication,
           @PathVariable(value = "commentId") Long commentId) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        Comment comment = commentRepository.findById(commentId).get();
        Long userID = comment.getUser().getId();
        if(userID == userId) {
            commentRepository.deleteById(commentId);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Đây không phải bình luận của bạn"));
    }
}
