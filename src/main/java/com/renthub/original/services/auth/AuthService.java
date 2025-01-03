package com.renthub.original.services.auth;

import com.renthub.original.dto.SignUpRequest;
import com.renthub.original.dto.UserDto;

public interface AuthService {
    UserDto createCustomer(SignUpRequest signUpRequest);
    boolean hasEmail(String email);
}
