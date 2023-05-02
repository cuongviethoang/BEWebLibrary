package com.project.projectBook.controller;

import com.project.projectBook.model.ERole;
import com.project.projectBook.model.Role;
import com.project.projectBook.model.User;
import com.project.projectBook.payload.request.LoginRequest;
import com.project.projectBook.payload.request.SignupRequest;
import com.project.projectBook.payload.response.JwtResponse;
import com.project.projectBook.payload.response.MessageResponse;
import com.project.projectBook.repository.RoleRepository;
import com.project.projectBook.repository.UserRepository;
import com.project.projectBook.security.jwt.JwtUtils;
import com.project.projectBook.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // authenticationManager dùng để xác thực thông tin đăng nhập của người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication); // tạo chuỗi JWT từ thông tin người dùng

        // thông tin chi tiết của người dùng đã đc xác thực từ đối tượng Authentication
        // Khi người dùng đã được xác thực, đối tượng Authentication sẽ được lưu trữ trong SecurityContextHolder, bao gồm cả đối tượng Principal mà chứa thông tin về người dùng.
        // tuy nhiên đối tượng Principal lưu trữ dưới dạng là 1 Object nên phải ép kiểu
        // sang UserDetailsImp để spring security cos thể hiểu đc
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // sử dụng phương thức getAuthorities() để lấy danh sách các đối tượng GrantedAuthority,
        // sau đó sử dụng phương thức stream() của lớp List để chuyển đổi danh sách này thành một luồng dữ liệu (stream).
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()); // phương thức collect() để thu thập các chuỗi này thành một danh sách mới của lớp List<String>.

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    // http://localhost:8082/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
