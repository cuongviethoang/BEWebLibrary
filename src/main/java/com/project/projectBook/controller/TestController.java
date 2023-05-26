package com.project.projectBook.controller;

import com.project.projectBook.dto.BookDto;
import com.project.projectBook.model.User;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    // http://localhost:8082/api/test/user
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> userAccess(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        return ResponseEntity.ok(
                userDetails
        );
    }

    // http://localhost:8082/api/test/user/imgUser?path=
    @PostMapping("/user/imgUser")
    public ResponseEntity<?> updateImgProfile(Authentication authentication, @RequestParam String path) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userRepository.updateImgUser(userDetails.getId(), path);
        return ResponseEntity.ok("Change picture success");
    }
}
