package com.nicat.task.expresstask.service;


import com.nicat.task.expresstask.entity.Role;
import com.nicat.task.expresstask.entity.User;
import com.nicat.task.expresstask.exception.AppException;
import com.nicat.task.expresstask.payload.ApiResponse;
import com.nicat.task.expresstask.payload.SignUpRequest;
import com.nicat.task.expresstask.repository.RoleRepository;
import com.nicat.task.expresstask.repository.UserRepository;
import com.nicat.task.expresstask.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private EmailService emailService;


    public ResponseEntity registerUser(SignUpRequest signUpRequest ){

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setIsActive(0);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (signUpRequest.getRoleId() == null) {
            signUpRequest.setRoleId(1L);
        }

        Role userRole = roleRepository.findById(signUpRequest.getRoleId())
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        String confirmationToken=getConfirmationToken();

        user.setConfirmationToken(confirmationToken);
        User result = userRepository.save(user);

        emailService.sendMail(user.getEmail(),
                "Confirmation",
                "http://localhost:8899/api/auth/confirmation?confirmationToken="+confirmationToken);


        return ResponseEntity.ok((new ApiResponse(true, "User registered successfully")));

    }

    public ResponseEntity<?> confirmation(String confirmationToken){
        Optional<User> user=userRepository.findByConfirmationToken(confirmationToken);
        if(user.isPresent()){
            User user1=user.get();
            user1.setIsActive(1);
            userRepository.save(user1);

            return ResponseEntity.ok((new ApiResponse(true, "User confirmed successfully")));
        }else{
            return ResponseEntity.ok((new ApiResponse(false, "Confirmation token is invalid")));
        }


    }

    private String getConfirmationToken()  {
        UUID gfg = UUID.randomUUID();
        return gfg.toString();
    }
}
