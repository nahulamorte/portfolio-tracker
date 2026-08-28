package org.portfoliotracker.portfolio.auth;

import org.portfoliotracker.portfolio.dto.request.LoginRequestDTO;
import org.portfoliotracker.portfolio.dto.request.RegisterRequestDTO;
import org.portfoliotracker.portfolio.dto.response.LoginResponseDTO;
import org.portfoliotracker.portfolio.dto.response.RegisterResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
public class UserAuthController {

    private final AuthService authService;

    public UserAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request){
        RegisterResponseDTO response = authService.register(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
