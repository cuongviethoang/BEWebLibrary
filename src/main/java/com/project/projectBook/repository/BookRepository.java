package com.project.projectBook.repository;

import com.project.projectBook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByGenresId(Long genreId);

    @Modifying
    @Transactional
    @Query("UPDATE Book bk SET bk.imgBook = :path WHERE bk.id = :id")
    void uploadImg(Long id, String path);

    @Query("select b from Book b")
    List<Book> findAllBook();

    @Modifying
    @Transactional
    @Query("UPDATE Book b set b.title = ?2, b.author = ?3, b.releaseDate = ?4, b.length = ?5, b.sold = ?6, b.totalBook = ?7, b.price = ?8 where b.id = ?1")
    void fixOneBook(Long id, String title, String author, String date, Integer length, Integer sold, Integer totalBook, Long price);
}
