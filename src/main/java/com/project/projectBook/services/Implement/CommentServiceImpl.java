package com.project.projectBook.services.Implement;

import com.project.projectBook.dto.CommentDto;
import com.project.projectBook.model.Book;
import com.project.projectBook.model.Comment;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.CommentRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.CommentService;
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
public class CommentServiceImpl  implements CommentService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;
    @Override
    public List<CommentDto> getAllCommentOfBook(Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        List<CommentDto> commentDtos = book.getComments().stream()
                .map(comment -> {
                    CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
                    commentDto.setBookId(comment.getBook().getId());
                    commentDto.setUserId(comment.getUser().getId());
                    commentDto.setUsername(comment.getUser().getUsername());
                    commentDto.setImgUser(comment.getUser().getImgProfile());
                    return commentDto;
                }).collect(Collectors.toList());
        Collections.sort(commentDtos, Comparator.comparing(CommentDto::getTime).reversed());
        Collections.sort(commentDtos, Comparator.comparing(CommentDto::getDate).reversed());
        return commentDtos;
    }

    @Override
    public void createCommentOfBook(Long userId, Long bookId, Comment comment) {

        Comment comment1 = new Comment();
        comment1.setContent(comment.getContent());
        comment1.setDate(LocalDate.now());
        LocalTime tm = LocalTime.now();
        comment1.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
        comment1.setUser(userRepository.findById(userId).get());
        comment1.setBook(bookRepository.findById(bookId).get());
        commentRepository.save(comment1);
    }

}
