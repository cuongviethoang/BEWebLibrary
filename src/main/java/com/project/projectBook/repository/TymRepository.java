package com.project.projectBook.repository;

import com.project.projectBook.model.Tym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TymRepository extends JpaRepository<Tym, Long> {

    Tym findTymByUserId(Long userId);
}
