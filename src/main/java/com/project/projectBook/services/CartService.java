package com.project.projectBook.services;

import com.project.projectBook.dto.CartDto;

import java.util.List;

public interface CartService {

    List<CartDto> showCart(Long userId);

    void createCart(Long userId, Long bookId, CartDto cartDto);

    void deleteCart(Long cartId);
}
