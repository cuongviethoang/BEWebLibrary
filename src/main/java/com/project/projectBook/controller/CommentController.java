package com.project.projectBook.controller;

import com.project.projectBook.exception.ErrorMessage;
import com.project.projectBook.exception.ResourceNotFoundException;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.Comment;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.CommentRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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



    // http://localhost:8082/api/book/{id}/comments
    @GetMapping("/book/{bookId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentOfBook(@PathVariable(value = "bookId") Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

        List<Comment> comments = commentRepository.findCommentByBookId(bookId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // http://localhost:8082/api/book/{id}/comment
    @CrossOrigin
    @PostMapping("/book/{bookId}/comment")
    public ResponseEntity<Comment> createComment(Authentication authentication,
            @PathVariable(value = "bookId") Long bookId,
            @RequestBody Comment commentRequest) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userID = userDetails.getId();
        Comment comment = bookRepository.findById(bookId).map(book -> {
            commentRequest.setBook(book);
            commentRequest.setUser(userRepository.findById(userID).get());
            commentRequest.setDate(LocalDate.now());
            LocalTime tm = LocalTime.now();
            commentRequest.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
            return commentRepository.save(commentRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

        return new ResponseEntity<>(comment, HttpStatus.OK);

    }

    // http://localhost:8082/api/book/{id}/comment/{id}
    @DeleteMapping("/book/{bookId}/comment/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(Authentication authentication,
           @PathVariable(value = "bookId") Long bookId,
           @PathVariable(value = "commentId") Long commentId) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + commentId));

        long userID = comment.getUser().getId();

        if(userID == userId) {
            commentRepository.deleteById(commentId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }
}
