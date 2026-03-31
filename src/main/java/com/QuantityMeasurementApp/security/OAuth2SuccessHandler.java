package com.QuantityMeasurementApp.security;


import com.QuantityMeasurementApp.model.User;
import com.QuantityMeasurementApp.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler 
        extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder 
            passwordEncoder;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Get user info from Google
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Check if user already exists in our database
        // If not, create a new user automatically
        if (!userRepository.existsByUsername(email)) {
            User newUser = new User(
                    email,
                    passwordEncoder.encode("OAUTH2_USER_" + email),
                    "ROLE_USER"
            );
            userRepository.save(newUser);
        }

        // Generate JWT token for the user
        String token = jwtUtil.generateToken(email);

        // Redirect to a URL with the token
        // Frontend can extract token from URL
        String redirectUrl = "http://localhost:8080/auth/oauth2/success" 
                             + "?token=" + token 
                             + "&username=" + email;

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}

