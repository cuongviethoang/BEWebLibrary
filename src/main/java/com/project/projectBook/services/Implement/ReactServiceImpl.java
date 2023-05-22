package com.project.projectBook.services.Implement;

import com.project.projectBook.dto.ReactDto;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.React;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.ReactRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.ReactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReactServiceImpl implements ReactService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReactRepository reactRepository;
    @Override
    public List<ReactDto> getAllReactOfBook(Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        List<ReactDto> reactDtos = book.getReacts().stream()
                .map(react -> {
                    ReactDto reactDto = modelMapper.map(react, ReactDto.class);
                    reactDto.setBookId(react.getBook().getId());
                    reactDto.setUserId(react.getUser().getId());
                    reactDto.setUsername(react.getUser().getUsername());
                    return reactDto;
                }).collect(Collectors.toList());
        Collections.sort(reactDtos, Comparator.comparing(ReactDto::getTime).reversed());
        Collections.sort(reactDtos, Comparator.comparing(ReactDto::getDate).reversed());
        return reactDtos;
    }

    @Override
    public void createReactOfBook(Long userId, Long bookId, Integer voted) {
        React react = new React();
        react.setVoted(voted);
        switch (voted){
            case 1:
                react.setMessage("Tệ");
                break;
            case 2:
                react.setMessage("Chán");
                break;
            case 3:
                react.setMessage("Tạm được");
                break;
            case 4:
                react.setMessage("Hay");
                break;
            default:
                react.setMessage("Tuyệt vời");
        }
        react.setDate(LocalDate.now());
        LocalTime tm = LocalTime.now();
        react.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
        react.setUser(userRepository.findById(userId).get());
        react.setBook(bookRepository.findById(bookId).get());
        reactRepository.save(react);
    }
}
