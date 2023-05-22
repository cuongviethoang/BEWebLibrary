package com.project.projectBook.services;

import com.project.projectBook.dto.ReactDto;

import java.util.List;

public interface ReactService {

    List<ReactDto> getAllReactOfBook(Long bookId);

    void createReactOfBook(Long userId, Long bookId, Integer voted);
}
