package com.project.projectBook.services;

import com.project.projectBook.dto.BillDto;


import java.util.List;

public interface BillService {

    void createBill(Long userId, Long bookId, BillDto billDto);

    List<BillDto> showBill(Long userId);

    List<BillDto> getAllBillOfBook(Long id);
}
