package com.project.projectBook.services.Implement;


import com.project.projectBook.dto.BillDto;
import com.project.projectBook.model.Bill;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.User;
import com.project.projectBook.repository.BillRepository;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;




    @Override
    public void createBill(Long userId, Long bookId, BillDto billDto) {
        User user = userRepository.findById(userId).get();
        Book book = bookRepository.findById(bookId).get();
        Bill bill = new Bill();
        bill.setUser(user);
        bill.setBook(book);
        bill.setUsedBuy(billDto.getUsedBuy());
        bill.setDate(LocalDate.now());
        LocalTime tm = LocalTime.now();
        bill.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
        billRepository.save(bill);
    }

    @Override
    public List<BillDto> showBill(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getBills().stream().map((bill -> {
            BillDto billDto = modelMapper.map(bill, BillDto.class);
            billDto.setId(bill.getId());
            billDto.setIdBook(bill.getBook().getId());
            billDto.setBookTitle(bill.getBook().getTitle());
            billDto.setImg(bill.getBook().getImgBook());
            billDto.setIdUser(userId);
            billDto.setUsername(user.getUsername());
            return billDto;
        })).collect(Collectors.toList());
    }

    @Override
    public List<BillDto> getAllBillOfBook(Long id) {
        Book book = bookRepository.findById(id).get();
        List<BillDto> billDtos = book.getBill().stream()
                .map(bill -> {
                    BillDto billDto = modelMapper.map(bill, BillDto.class);
                    billDto.setId(bill.getId());
                    billDto.setIdBook(bill.getBook().getId());
                    billDto.setBookTitle(bill.getBook().getTitle());
                    billDto.setImg(bill.getBook().getImgBook());
                    billDto.setIdUser(bill.getUser().getId());
                    billDto.setUsername(bill.getUser().getUsername());
                    return billDto;
                }).collect(Collectors.toList());
        return billDtos;
    }
}
