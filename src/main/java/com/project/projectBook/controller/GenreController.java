package com.project.projectBook.controller;

import com.project.projectBook.exception.ResourceNotFoundException;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.Genre;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.GenreReporitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class GenreController {

    @Autowired
    GenreReporitory genreReporitory;

    @Autowired
    BookRepository bookRepository;

    // http://localhost:8082/api/genres
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = new ArrayList<Genre>();

        genreReporitory.findAll().forEach(genres::add);

        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<Genre> getOneGenre(@PathVariable(value = "id") Long id) {
        Genre genre = genreReporitory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Genre with id = " + id));

        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @GetMapping("/genre/{genreId}/books")
    public ResponseEntity<List<Book>> getAllBookFromGenreId(@PathVariable(value = "genreId") Long genreId) {
        if(!genreReporitory.existsById(genreId)) {
            throw new ResourceNotFoundException("Not found Genre with id = " + genreId);
        }

        List<Book> books = bookRepository.findBooksByGenresId(genreId);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // http://localhost:8082/api/book/{id}/genre
    @PostMapping("/book/{bookId}/genre")
    public ResponseEntity<Genre> addGenre(@PathVariable(value = "bookId") Long bookId, @RequestBody Genre genreRequest) {
        Genre genre = bookRepository.findById(bookId).map(book -> {
            if(genreRequest.getId() == null) {
                Genre genre1 = new Genre(genreRequest.getName());
                book.addgenre(genre1);
                return genreReporitory.save(genre1);
            }
            else {
            Long genreId = genreRequest.getId();
                Genre _genre = genreReporitory.findById(genreId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Gener with id of gener to post for movie= " + genreId));
                book.addgenre(_genre);
                bookRepository.save(book);
                return _genre;
            }

        }).orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));
        return new ResponseEntity<>(genre, HttpStatus.CREATED);
    }

    @PutMapping("/genre/{id}")
    public ResponseEntity<Genre> fixGenre(@PathVariable(value = "id") Long id, @RequestBody Genre genre) {

        Genre genre1 = genreReporitory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Genre with id = " + id));

        genre1.setName(genre.getName());

        return new ResponseEntity<>(genreReporitory.save(genre1), HttpStatus.OK);
    }

    // http://localhost:8082/api/book/{id}/genre/{id}
    @DeleteMapping("/book/{bookId}/genre/{genreId}")
    public ResponseEntity<HttpStatus> deleteGenreOfBook(@PathVariable(value = "bookId") Long bookId, @PathVariable(value = "genreId") Long genreId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

        Genre genre = genreReporitory.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Genre with id = " + genreId));

        book.removeGenre(genreId);
        bookRepository.save(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/genre/{id}")
    public ResponseEntity<HttpStatus> deleteGenre(@PathVariable(value = "id") Long id) {

        Genre genre = genreReporitory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Genre with id = " + id));

        List<Book> books = bookRepository.findBooksByGenresId(id);

        for(Book book:books) {
            book.removeGenre(id);
        }
        genreReporitory.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
