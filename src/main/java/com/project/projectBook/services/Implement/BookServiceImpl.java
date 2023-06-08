package com.project.projectBook.services.Implement;

import com.project.projectBook.dto.BillDto;
import com.project.projectBook.dto.BookReactDto;
import com.project.projectBook.dto.BuyBookDto;
import com.project.projectBook.model.Bill;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.Genre;
import com.project.projectBook.model.React;
import com.project.projectBook.repository.*;
import com.project.projectBook.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReactRepository reactRepository;

    @Autowired
    TymRepository tymRepository;

    @Override
    public List<BuyBookDto> buyBookDtos() {

        List<BuyBookDto> buyBookDtos = new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            BuyBookDto buyBookDto = new BuyBookDto();
            List<Bill> bills = billRepository.findBillByBookId(book.getId());
            Long totalPrice;
            long soldBook = 0;
            long restBook = 0;
            for(Bill bill: bills) {
                soldBook += bill.getUsedBuy();
            }
            buyBookDto.setId(book.getId());
            buyBookDto.setTitle(book.getTitle());
            buyBookDto.setSold(soldBook);
            buyBookDto.setRestBook(book.getTotalBook());
            buyBookDto.setPrice(book.getPrice());
            buyBookDto.setTotalPrice(book.getPrice() * soldBook);
            buyBookDtos.add(buyBookDto);
        });
        return buyBookDtos;
    }

    @Override
    public List<Book> getAllBook() {
        List<Book> books = bookRepository.findAllBook();
        return books;
    }

    @Override
    public BookReactDto getBookReact(Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        BookReactDto bookReactDto = new BookReactDto();
        bookReactDto.setId(book.getId());
        bookReactDto.setTitle(book.getTitle());
        bookReactDto.setTyms(book.getTyms().stream().count());
        bookReactDto.setComments(book.getComments().stream().count());
        long average = 0;
        long count = book.getReacts().stream().count();
        List<React> reacts = reactRepository.findReactByBookId(bookId);
        for(React react:reacts) {
            average += react.getVoted();
        }
        double result = (double) average/count;
        result = (double) Math.round(result * 100) / 100;
        bookReactDto.setAverageRate(result);
        return bookReactDto;
    }

    @Override
    public void createBook(Book book) {
        Book bk = new Book();
        bk.setTitle(book.getTitle());
        bk.setTotalBook(book.getTotalBook());
        bk.setSold(0);
        bk.setImgBook(book.getImgBook());
        bk.setPrice(book.getPrice());
        bk.setOverview(book.getOverview());
        bk.setLength(book.getLength());
        bk.setReleaseDate(book.getReleaseDate());
        bk.setAuthor(book.getAuthor());
        Set<Genre> genreSet = book.getGenres();
        bk.setGenres(genreSet);
        bookRepository.save(bk);
    }

    @Override
    public void fixOneBook(Long id, Book book) {
        Book bk = bookRepository.findById(id).get();
        bk.setTitle(book.getTitle());
        bk.setTotalBook(book.getTotalBook());
        bk.setSold(book.getSold());
        bk.setImgBook(book.getImgBook());
        bk.setPrice(book.getPrice());
        bk.setOverview(book.getOverview());
        bk.setLength(book.getLength());
        bk.setReleaseDate(book.getReleaseDate());
        bk.setAuthor(book.getAuthor());
        Set<Genre> genreSet = book.getGenres();
        bk.setGenres(genreSet);
        bookRepository.save(bk);
    }
}
