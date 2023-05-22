package com.project.projectBook.services.Implement;

import com.project.projectBook.dto.BillDto;
import com.project.projectBook.dto.BuyBookDto;
import com.project.projectBook.model.Bill;
import com.project.projectBook.model.Book;
import com.project.projectBook.repository.BillRepository;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BillRepository billRepository;

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
            buyBookDto.setRestBook(book.getTotalBook() - soldBook);
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
    public void createBook(Book book) {
        Book bk = new Book();
        bk.setTitle(book.getTitle());
        bk.setTotalBook(book.getTotalBook());
        bk.setSold(0);
        bk.setPrice(book.getPrice());
        bk.setLength(book.getLength());
        bk.setReleaseDate(book.getReleaseDate());
        bk.setAuthor(book.getAuthor());
        bookRepository.save(bk);
    }

    @Override
    public Book fixOneBook(Long id, Book book) {
        bookRepository.fixOneBook(id, book.getTitle(), book.getAuthor(), book.getReleaseDate(),
                book.getLength(), book.getSold(), book.getTotalBook(), book.getPrice());
        Book bk = bookRepository.findById(id).get();
        return  bk;
    }


}
