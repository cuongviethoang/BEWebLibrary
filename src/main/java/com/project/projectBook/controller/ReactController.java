package com.project.projectBook.controller;

import com.project.projectBook.dto.ReactDto;
import com.project.projectBook.exception.ResourceNotFoundException;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.React;
import com.project.projectBook.payload.response.MessageResponse;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.ReactRepository;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    ModelMapper modelMapper;

    // http://localhost:8082/api/book/{id}/reacts
    @GetMapping("/book/{bookId}/reacts")
    public ResponseEntity<List<ReactDto>> getAllReactOfBook(@PathVariable(value = "bookId") Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = "+ bookId));

        List<ReactDto> reacts = book.getReacts().stream().map((react) -> {
            ReactDto reactDto = modelMapper.map(react, ReactDto.class);
            reactDto.setUsername(react.getUser().getUsername());
            return reactDto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(reacts, HttpStatus.OK);


    }

    // http://localhost:8082/api/book/{id}/react
    @CrossOrigin
    @PostMapping("/book/{bookId}/react")
    public ResponseEntity<ReactDto> createReact(Authentication authentication,
                                                @PathVariable(value = "bookId") Long bookId,
                                                @RequestBody ReactDto reactDto) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        long userId = userDetails.getId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

        React react = new React();
        react.setUser(userRepository.findById(userId).get());
        react.setBook(book);
        react.setVoted(reactDto.getVoted());
        react.setMessage(reactDto.getMessage());
        react.setDate(LocalDate.now());
        LocalTime tm = LocalTime.now();
        react.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));

        reactRepository.save(react);

        reactDto = modelMapper.map(react, ReactDto.class);
        reactDto.setUsername(userDetails.getUsername());

        return new ResponseEntity<>(reactDto, HttpStatus.OK);

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
