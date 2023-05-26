package com.project.projectBook.services.Implement;

import com.project.projectBook.dto.CartDto;
import com.project.projectBook.model.Cart;
import com.project.projectBook.model.User;
import com.project.projectBook.repository.BookRepository;
import com.project.projectBook.repository.CartRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public List<CartDto> showCart(Long userId) {
        User user = userRepository.findById(userId).get();
        List<CartDto> cartDtos = user.getCarts().stream()
                .map(cart -> {
                    long total = 0;
                    total = cart.getUsedBuy() * cart.getBook().getPrice();
                    CartDto cartDto = modelMapper.map(cart, CartDto.class);
                    cartDto.setId(cart.getId());
                    cartDto.setIdUser(cart.getUser().getId());
                    cartDto.setUsername(cart.getUser().getUsername());
                    cartDto.setIdBook(cart.getBook().getId());
                    cartDto.setBookTitle(cart.getBook().getTitle());
                    cartDto.setImg(cart.getBook().getImgBook());
                    cartDto.setPrice(cart.getBook().getPrice());
                    cartDto.setTotalPrice(total);
                    return cartDto;
                }).collect(Collectors.toList());
        return cartDtos;
    }

    @Override
    public void createCart(Long userId, Long bookId, CartDto cartDto) {
        Cart cart = new Cart();
        cart.setBook(bookRepository.findById(bookId).get());
        cart.setUser(userRepository.findById(userId).get());
        cart.setAddress(cartDto.getAddress());
        cart.setSdt(cartDto.getSdt());
        cart.setUsedBuy(cartDto.getUsedBuy());
        cart.setDate(LocalDate.now());
        LocalTime tm = LocalTime.now();
        cart.setTime(LocalTime.parse(String.format("%02d:%02d:%02d", tm.getHour(), tm.getMinute(), tm.getSecond())));
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).get();
        cartRepository.delete(cart);
    }
}
