package com.project.projectBook.controller;

import com.project.projectBook.dto.CommentDto;
import com.project.projectBook.exception.ErrorMessage;
import com.project.projectBook.exception.ResourceNotFoundException;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.Comment;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.CommentRepository;
import com.project.projectBook.repository.UserRepository;
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
    ModelMapper modelMapper;



    // http://localhost:8082/api/book/{id}/comments
    @GetMapping("/book/{bookId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentOfBook(@PathVariable(value = "bookId") Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));
        List<CommentDto> commentDtos = book.getComments().stream().map((comment) -> {
                    CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
                    commentDto.setUserId(comment.getUser().getId());
                    commentDto.setUsername(comment.getUser().getUsername());
                    return commentDto;
                }).collect(Collectors.toList());
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    // http://localhost:8082/api/book/{id}/comment
    @CrossOrigin
    @PostMapping("/book/{bookId}/comment")
    public ResponseEntity<CommentDto> createComment(Authentication authentication,
            @PathVariable(value = "bookId") Long bookId,
            @RequestBody CommentDto commentRequest) {

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            long userID = userDetails.getId();
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

            Comment comment = new Comment();
            comment.setUser(userRepository.findById(userID).get());
            comment.setDate(LocalDate.now());
            comment.setContent(commentRequest.getContent());
            comment.setBook(book);
            LocalTime tm = LocalTime.now();
            comment.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
            commentRepository.save(comment);

            commentRequest = modelMapper.map(comment, CommentDto.class);
            commentRequest.setUsername(userDetails.getUsername());
            return new ResponseEntity<>(commentRequest, HttpStatus.OK);
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
