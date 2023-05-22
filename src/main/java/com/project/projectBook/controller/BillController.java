package com.project.projectBook.controller;


import com.project.projectBook.dto.BillDto;
import com.project.projectBook.model.Book;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.services.BillService;
import com.project.projectBook.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/bill")
public class BillController {

    @Autowired
    BillService billService;

    @Autowired
    BookRepository bookRepository;

    // http://localhost:8082/api/bill/book/{id}
    @PostMapping("/book/{bookId}")
    public ResponseEntity<?> createBill(Authentication authentication, @PathVariable(value = "bookId") Long bookId, @RequestBody BillDto billDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        billService.createBill(userDetails.getId(), bookId, billDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // http://localhost:8082/api/bill/show
    @GetMapping("/show")
    public ResponseEntity<?> showBill(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new ResponseEntity<>(billService.showBill(userDetails.getId()), HttpStatus.OK);
    }

    // http://localhost:8082/api/bill/book/{id}/stats
    @GetMapping("/book/{bookId}/stats")
    public ResponseEntity<?> getAllBillOfBook(@PathVariable(name = "bookId") Long bookId) {
        List<BillDto> billDtos = billService.getAllBillOfBook(bookId);
        return ResponseEntity.ok(billDtos);
    }
}
