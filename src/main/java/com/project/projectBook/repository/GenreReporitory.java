package com.project.projectBook.repository;

import com.project.projectBook.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreReporitory extends JpaRepository<Genre, Long> {
    List<Genre> findGenresByBooksId(Long bookId);

    Genre findGenreByName(String name);

}
