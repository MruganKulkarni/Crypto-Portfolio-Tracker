package com.crypto.authservice.security;

import com.crypto.authservice.entity.Role;
import com.crypto.authservice.entity.User;
import com.crypto.authservice.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User newUser = User.builder()
                            .email(email)
                            .fullName(name)
                            .password("GOOGLE_USER")
                            .role(Role.USER)
                            .enabled(true)
                            .build();

                    return userRepository.save(newUser);
                });

        String accessToken = jwtService.generateToken(user);

        System.out.println("GOOGLE LOGIN SUCCESS");

        response.sendRedirect(
                "http://localhost:8081/swagger-ui/index.html"
        );
    }
}