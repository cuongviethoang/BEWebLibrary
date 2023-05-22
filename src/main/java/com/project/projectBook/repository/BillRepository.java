package com.project.projectBook.repository;

import com.project.projectBook.model.Bill;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findBillByBookId(Long id);
}
