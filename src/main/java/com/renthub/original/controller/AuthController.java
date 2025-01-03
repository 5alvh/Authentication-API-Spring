package com.renthub.original.controller;

import com.renthub.original.dto.AuthenticationRequest;
import com.renthub.original.dto.AuthenticationResponse;
import com.renthub.original.dto.SignUpRequest;
import com.renthub.original.dto.UserDto;
import com.renthub.original.entity.User;
import com.renthub.original.repository.UserRepository;
import com.renthub.original.services.auth.AuthService;
import com.renthub.original.services.jwt.UserService;
import com.renthub.original.utilities.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JWTUtil jwtUtil;
    private UserRepository userRepository;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserService userService, JWTUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createCustomer(@RequestBody SignUpRequest signUpRequest) {
        if (authService.hasEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>("Email Already Exists",HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.createCustomer(signUpRequest);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Customer Not Created",HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Incorrect Email Or Password.");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());

        System.out.println(userDetails.getUsername()+userDetails.getPassword()+userDetails.getAuthorities());

        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }

        return authenticationResponse;
    }
}
