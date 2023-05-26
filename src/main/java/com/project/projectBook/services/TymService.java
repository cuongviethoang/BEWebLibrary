package com.project.projectBook.services;

import com.project.projectBook.dto.TymDto;

import java.util.List;

public interface TymService {

    List<TymDto> getAllTymOfBook(Long bookId);

    void createTym(Long userId, Long bookId);
}
