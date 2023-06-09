package com.project.projectBook.controller;


import com.project.projectBook.dto.BillDto;
import com.project.projectBook.model.Bill;
import com.project.projectBook.model.Book;
import com.project.projectBook.repository.BillRepository;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/bill")
public class BillController {

    @Autowired
    BillService billService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BillRepository billRepository;


    // Tạo bill
    // http://localhost:8082/api/bill/book/{id}
    @PostMapping("/book/{bookId}")
    public ResponseEntity<?> createBill(Authentication authentication, @PathVariable(value = "bookId") Long bookId, @RequestBody BillDto billDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        billService.createBill(userDetails.getId(), bookId, billDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Lấy ra tất cả bill mà mỗi một người dùng đã mua
    // http://localhost:8082/api/bill/show
    @GetMapping("/show")
    public ResponseEntity<?> showBill(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<BillDto> billDtos = billService.showBill(userDetails.getId());
        Collections.sort(billDtos, Comparator.comparing(BillDto::getTime).reversed());
        Collections.sort(billDtos, Comparator.comparing(BillDto::getDate).reversed());
        return new ResponseEntity<>(billDtos, HttpStatus.OK);
    }

    // Thống kê tất cả bill đã bán của một cuốn sách
    // http://localhost:8082/api/bill/book/{id}/stats
    @CrossOrigin
    @GetMapping("/book/{bookId}/stats")
    public ResponseEntity<?> getAllBillOfBook(@PathVariable(name = "bookId") Long bookId) {
        List<BillDto> billDtos = billService.getAllBillOfBook(bookId);
        return ResponseEntity.ok(billDtos);
    }

    // http://localhost:8082/api/bill/{id}
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteBill(@PathVariable(name = "id") Long id) {
        Bill bill = billRepository.findById(id).get();
        billRepository.delete(bill);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
