package com.project.projectBook.controller;

import com.project.projectBook.dto.BookDto;
import com.project.projectBook.dto.BookReactDto;
import com.project.projectBook.dto.BuyBookDto;
import com.project.projectBook.exception.ResourceNotFoundException;
import com.project.projectBook.model.Bill;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.User;
import com.project.projectBook.payload.response.MessageResponse;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.BookService;
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
    BookService bookService;


    // Lấy ra chi tiết thông tin tất cả  quyển sách
    // http://localhost:8082/api/books
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBook();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Lấy ra thông tin 1 quyển sách theo id
    // http://localhost:8082/api/book/{id}
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable(value = "id") Long id ) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Lấy ra thông tin như trung bình đánh giá, lượt tym, lượt comment của 1 quyển sách theo id
    // http://localhost:8082/api/book/{id}/count
    @GetMapping("/book/{id}/count")
    public ResponseEntity<?> getCountOfBook(@PathVariable(value = "id") Long id) {
        BookReactDto bookReactDto = bookService.getBookReact(id);
        return ResponseEntity.ok(bookReactDto);
    }

    // Lấy ra thể loại của tất cả sách của 1 thể loại
    // http://localhost:8082/api/books/genre/{id}
    @GetMapping("/books/genre/{id}")
    public  ResponseEntity<?> getAllBookOfGenre(@PathVariable(value = "id") Long id) {
        List<Book> books = bookRepository.findBooksByGenresId(id);
        return ResponseEntity.ok(books);
    }

    // Thống kê số sách đã bán số sách còn, tổng tiền đã bán cảu tất cả quyển sách
    // http://localhost:8082/api/books/stats
    @GetMapping("/books/stats")
    public ResponseEntity<?> getStatAllBook() {
        List<BuyBookDto> buyBookDtos = bookService.buyBookDtos();
        return ResponseEntity.ok(buyBookDtos);
    }

    // Tìm tên của 1 quyển sách
    // http://localhost:8082/api/books/findBook?title=
    @GetMapping("/books/findBook")
    public  ResponseEntity<?> getAllBookToInput(@RequestParam String title) {
        List<Book> books = bookRepository.findAllBookToInput(title);
        return ResponseEntity.ok(books);
    }

    // Thêm 1 quyển sách
    // http://localhost:8082/api/book
    @PostMapping("/book")
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        if(bookRepository.existsByTitle(book.getTitle())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Tên sách đã tồn tại trong csdl"));
        }
        bookService.createBook(book);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    // Sửa thông tin của 1 quyển sách
    // http://localhost:8082/api/book/{id}
    @PutMapping("/book/{id}")
    public ResponseEntity<?> fixBook(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        bookService.fixOneBook(id, book);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // Sửa lại ố lượng sách còn lại trong kho và cập nhật số sách đã bán
    // http://localhost:8082/api/book/{id}/sold
    @PutMapping("/book/{id}/sold")
    public ResponseEntity<Book> buyBook( @PathVariable(value = "id") Long id, @RequestBody Book book) {
        Book bk = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

        bk.setSold(bk.getSold() + book.getSold());
        bk.setTotalBook(bk.getTotalBook() - book.getSold());
        bookRepository.save(bk);

        return new ResponseEntity<>(bk, HttpStatus.OK);
    }

    // Gọi api này khi xóa bill sẽ cập nhật lại số ách đã mua vào lại kho
    // http://localhost:8082/api/book/{id}/delbill
    @PutMapping("/book/{id}/delbill")
    public ResponseEntity<Book> delBill(@PathVariable(value = "id") Long id, @RequestBody Bill bill) {
        Book bk = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

        bk.setSold(bk.getSold() - bill.getUsedBuy());
        bk.setTotalBook(bk.getTotalBook() + bill.getUsedBuy());
        bookRepository.save(bk);

        return new ResponseEntity<>(bk, HttpStatus.OK);
    }


    // Xóa 1 quyển sách theo id;
    // http://localhost:8082/api/book/{id}
    @DeleteMapping("/book/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable(value = "id") Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

        bookRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Thay ảnh của 1 cuốn sách
    // http://localhost:8082/api/book/{id}/bookImg
    @CrossOrigin
    @PostMapping("/book/{id}/bookImg")
    public ResponseEntity<?> postImgBook(@PathVariable(value = "id") Long id, @RequestParam String img) {
        bookRepository.uploadImg(id, img);
        return ResponseEntity.ok("Change picture success");
    }
}
