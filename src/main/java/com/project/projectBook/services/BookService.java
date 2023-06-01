package com.project.projectBook.services;

import com.project.projectBook.dto.BillDto;
import com.project.projectBook.dto.BookReactDto;
import com.project.projectBook.dto.BuyBookDto;
import com.project.projectBook.model.Book;

import java.util.List;

public interface BookService {
    List<BuyBookDto> buyBookDtos(); // Lấy ra số sách đã bán, tổng tiền thu được và số sách còn lại

    List<Book> getAllBook(); // Lấy ra tất cả sách

    BookReactDto getBookReact(Long bookId); // Lấy ra trung bình đánh giá, lượt tym, lượt comment

    void createBook( Book book); // tạo 1 quyển sách

    Book fixOneBook(Long id, Book book); // Sửa thông tin 1 quyển sách
}
