package com.project.projectBook.services.Implement;

import com.project.projectBook.dto.TymDto;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.Tym;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.TymRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.TymService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TymServiceImpl implements TymService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TymRepository tymRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<TymDto> getAllTymOfBook(Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        List<TymDto> tymDtos = book.getTyms().stream()
                .map(tym -> {
                    TymDto tymDto = modelMapper.map(tym, TymDto.class);
                    tymDto.setBookId(tym.getBook().getId());
                    tymDto.setUserId(tym.getUser().getId());
                    return tymDto;
                }).collect(Collectors.toList());
        return tymDtos;
    }

    @Override
    public void createTym(Long userId, Long bookId) {
        Tym tym = new Tym();
        tym.setUser(userRepository.findById(userId).get());
        tym.setBook(bookRepository.findById(bookId).get());
        tymRepository.save(tym);
    }
}
