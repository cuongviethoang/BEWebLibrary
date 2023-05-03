package com.project.projectBook.controller;

import com.project.projectBook.exception.ResourceNotFoundException;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.React;
import com.project.projectBook.payload.response.MessageResponse;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.ReactRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ReactController {

    @Autowired
    ReactRepository reactRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    // http://localhost:8082/api/book/{id}/reacts
    @GetMapping("/book/{bookId}/reacts")
    public ResponseEntity<List<React>> getAllReactOfBook(@PathVariable(value = "bookId") Long bookId) {
        if(!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Not found Book with id = " + bookId);
        }

        List<React> reacts = reactRepository.findReactByBookId(bookId);

        return new ResponseEntity<>(reacts, HttpStatus.OK);
    }

    // http://localhost:8082/api/book/{id}/react
    @CrossOrigin
    @PostMapping("/book/{bookId}/react")
    public ResponseEntity<React> createReact(Authentication authentication,
           @PathVariable(value = "bookId") Long bookId,
           @RequestBody React reactRequest) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        long userId = userDetails.getId();

        React react1 = bookRepository.findById(bookId).map(book -> {
            reactRequest.setBook(book);
            reactRequest.setUser(userRepository.findById(userId).get());
            reactRequest.setDate(LocalDate.now());
            LocalTime tm = LocalTime.now();
            reactRequest.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));

            return reactRepository.save(reactRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

        return new ResponseEntity<>(react1, HttpStatus.OK);
    }

    // http://localhost:8082/api//{id}/react/{id}
    @DeleteMapping("/book/{bookId}/react/{reactId}")
    public ResponseEntity<?> deteleReactOfBook(Authentication authentication,
           @PathVariable(value = "bookId") Long bookId,
           @PathVariable(value = "reactId") Long reactId) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        long userId = userDetails.getId();

        React react = reactRepository.findById(reactId).get();

        long userID = react.getUser().getId();

        if(userID == userId) {
            reactRepository.deleteById(reactId);
            return ResponseEntity.ok(new MessageResponse("Success"));
        }

        return ResponseEntity.ok(new MessageResponse("you are not the person who wrote this comment"));
    }
}
