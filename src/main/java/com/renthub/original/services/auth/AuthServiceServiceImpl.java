package com.renthub.original.services.auth;

import com.renthub.original.dto.SignUpRequest;
import com.renthub.original.dto.UserDto;
import com.renthub.original.entity.User;
import com.renthub.original.enums.UserRole;
import com.renthub.original.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceServiceImpl implements AuthService {
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthServiceServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createCustomer(SignUpRequest signUpRequest) {
        if (hasEmail(signUpRequest.getEmail())) return null;
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getUserRole());
    }

    @Override
    public boolean hasEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
