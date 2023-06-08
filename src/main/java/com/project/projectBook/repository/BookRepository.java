package com.project.projectBook.repository;

import com.project.projectBook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByGenresId(Long genreId);

    Boolean existsByTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book bk SET bk.imgBook = :path WHERE bk.id = :id")
    void uploadImg(Long id, String path);

    @Query("select b from Book b")
    List<Book> findAllBook();

    @Query("select b from Book b where b.title like %:title% ")
    List<Book> findAllBookToInput(@Param("title") String title);


}
