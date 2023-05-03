package com.project.projectBook.repository;

import com.project.projectBook.model.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactRepository extends JpaRepository<React, Long> {

    List<React> findReactByBookId(Long bookId);
}
