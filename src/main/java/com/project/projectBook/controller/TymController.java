package com.project.projectBook.controller;

import com.project.projectBook.dto.TymDto;
import com.project.projectBook.model.Tym;
import com.project.projectBook.repository.TymRepository;
import com.project.projectBook.services.TymService;
import com.project.projectBook.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TymController {

    @Autowired
    TymRepository tymRepository;

    @Autowired
    TymService tymService;

    // http://localhost:8082/api/book/{id}/tyms
    @GetMapping("/book/{bookId}/tyms")
    public ResponseEntity<?> getAllTmOfBook(@PathVariable(value = "bookId") Long bookId) {
        List<TymDto> tymDtos = tymService.getAllTymOfBook(bookId);
        return ResponseEntity.ok(tymDtos);
    }

    // http://locaLhost:8082/api/book/{id}/tym
    @PostMapping("/book/{bookId}/tym")
    public ResponseEntity<?> createTym(Authentication authentication, @PathVariable(value = "bookId") Long bookId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        Tym tym = tymRepository.findTymByUserId(userId);
        if(tym != null) {
            tymRepository.delete(tym);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        tymService.createTym(userId, bookId);
        return ResponseEntity.ok(HttpStatus.OK);
     }
}
