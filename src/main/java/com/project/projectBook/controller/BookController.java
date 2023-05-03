package com.project.projectBook.controller;

import com.project.projectBook.dto.BookDto;
import com.project.projectBook.exception.ResourceNotFoundException;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.User;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    // http://localhost:8082/api/books
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = new ArrayList<Book>();

        bookRepository.findAll().forEach(books::add);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // http://localhost:8082/api/book/{id}
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable(value = "id") Long id ) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // http://localhost:8082/api/book
    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book bk = bookRepository.save(new Book(book.getTitle(), book.getAuthor(),
                book.getReleaseDate(), book.getLength(), 0));

        return new ResponseEntity<>(bk, HttpStatus.OK);
    }

    //
    @PutMapping("/book/{id}")
    public ResponseEntity<Book> fixBook(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        Book bk = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found book with id = " + id));

        bk.setTitle(book.getTitle());
        bk.setAuthor(book.getAuthor());
        bk.setReleaseDate(book.getReleaseDate());
        bk.setLength(book.getLength());
        bk.setSold(bk.getSold());

        bookRepository.save(bk);

        return new ResponseEntity<>(bk, HttpStatus.OK);
    }

    // http://localhost:8082/api/book/sold/{id}
//    @PostMapping("book/sold/{id}")
//    public ResponseEntity<BookDto> buyBookOfUser(Authentication authentication,
//           @PathVariable(value = "id") Long id, @RequestBody BookDto bookDto) {
//
//        Book bk = bookRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        long userId = userDetails.getId();
//        User user = userRepository.findById(userId).get();
//
//        Book book1 = new Book(bk.getTitle(), bk.getAuthor(), bk.getReleaseDate(),
//                bk.getLength(), bookDto.getSold());
//        book1.setId(bk.getId());
//        user.addBook(book1);
//        userRepository.save(user);
//
//        bookDto.setId(bk.getId());
//        bookDto.setTitle(bk.getTitle());
//        bookDto.setAuthor(bk.getAuthor());
//        bookDto.setUserId(userId);
//        bookDto.setUsername(user.getUsername());
//
//        return new ResponseEntity<>(bookDto, HttpStatus.OK);
//    }



    // http://localhost:8082/api/book/sold/{id}
    @PutMapping("/book/sold/{id}")
    public ResponseEntity<Book> buyBook( @PathVariable(value = "id") Long id, @RequestBody Book book) {
        Book bk = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

        bk.setSold(bk.getSold() + book.getSold());
        bookRepository.save(bk);

        return new ResponseEntity<>(bk, HttpStatus.OK);
    }



    @DeleteMapping("/book/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable(value = "id") Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

        bookRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // http://localhost:8082/api/book/{id}/bookImg
    @PostMapping("/book/{id}/bookImg")
    public ResponseEntity<?> postImgBook(@PathVariable(value = "id") Long id, @RequestParam String img) {
        bookRepository.uploadImg(id, img);
        return ResponseEntity.ok("Change picture success");
    }
}
