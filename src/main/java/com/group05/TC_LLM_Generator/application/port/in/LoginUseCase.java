package com.group05.TC_LLM_Generator.application.port.in;

import com.group05.TC_LLM_Generator.application.dto.AuthResponse;

public interface LoginUseCase {
    AuthResponse loginWithGoogle(String idToken);
}
