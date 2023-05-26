package com.project.projectBook.services;

import com.project.projectBook.dto.BillDto;
import com.project.projectBook.dto.BuyBookDto;
import com.project.projectBook.model.Book;

import java.util.List;

public interface BookService {
    List<BuyBookDto> buyBookDtos();

    List<Book> getAllBook();



    void createBook( Book book);

    Book fixOneBook(Long id, Book book);
}
