package com.project.projectBook.services;

import com.project.projectBook.dto.CommentDto;
import com.project.projectBook.model.Comment;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAllCommentOfBook(Long bookId);

    void createCommentOfBook(Long userId, Long bookId, Comment comment);
}
