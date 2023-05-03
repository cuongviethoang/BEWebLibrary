package com.project.projectBook.repository;

import com.project.projectBook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByGenresId(Long genreId);

    @Modifying
    @Transactional
    @Query("UPDATE Book bk SET bk.imgBook = :path WHERE bk.id = :id")
    void uploadImg(Long id, String path);
}
