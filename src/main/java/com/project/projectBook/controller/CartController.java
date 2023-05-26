package com.project.projectBook.controller;

import com.project.projectBook.dto.CartDto;
import com.project.projectBook.repository.CartRepository;
import com.project.projectBook.services.CartService;
import com.project.projectBook.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartService cartService;

    // http://localhost:8082/api/cart/show
    @GetMapping("/show")
    public ResponseEntity<?> showCart(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<CartDto> cartDtos = cartService.showCart(userDetails.getId());
        Collections.sort(cartDtos, Comparator.comparing(CartDto::getTime).reversed());
        Collections.sort(cartDtos, Comparator.comparing(CartDto::getTime).reversed());
        return ResponseEntity.ok(cartDtos);
    }

    // http://localhost:8082/api/cart/book/{id}
    @PostMapping("/book/{bookId}")
    public ResponseEntity<?> createCart(Authentication authentication, @PathVariable(value = "bookId") Long bookId,@RequestBody CartDto cartDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        cartService.createCart(userDetails.getId(), bookId,cartDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // http://localhost:8082/api/cart/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable(value = "id") Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
