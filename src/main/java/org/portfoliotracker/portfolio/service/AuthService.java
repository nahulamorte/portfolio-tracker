package org.portfoliotracker.portfolio.service;

import jakarta.transaction.Transactional;
import org.portfoliotracker.portfolio.auth.JwtService;
import org.portfoliotracker.portfolio.dto.request.LoginRequestDTO;
import org.portfoliotracker.portfolio.dto.request.RegisterRequestDTO;
import org.portfoliotracker.portfolio.dto.response.LoginResponseDTO;
import org.portfoliotracker.portfolio.dto.response.RegisterResponseDTO;
import org.portfoliotracker.portfolio.entity.Portfolio;
import org.portfoliotracker.portfolio.entity.UserApp;
import org.portfoliotracker.portfolio.entity.UserAuth;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {
    private final UserAppRepository userAppRepository;
    private final UserAuthRepository userAuthRepository;
    private final PortfolioRepository portfolioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserAppRepository userAppRepository,
                       UserAuthRepository userAuthRepository,
                       PortfolioRepository portfolioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userAppRepository = userAppRepository;
        this.userAuthRepository = userAuthRepository;
        this.portfolioRepository = portfolioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        if (userAuthRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }
        if (userAuthRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        // Paso 1: UserApp primero, necesita existir para tener ID
        UserApp userApp = new UserApp(request.name(), request.dateBirth());
        userApp = userAppRepository.save(userApp);

        // Paso 2: UserAuth, ahora sí puede referenciar el UserApp ya persistido
        UserAuth userAuth = new UserAuth();
        userAuth.setUserApp(userApp);
        userAuth.setUsername(request.username());
        userAuth.setEmail(request.email());
        userAuth.setPasswordHash(passwordEncoder.encode(request.password()));
        userAuthRepository.save(userAuth);

        // Paso 3: Portfolio inicial, balance 0, mismo UserApp
        Portfolio portfolio = new Portfolio(userApp, BigDecimal.ZERO);
        portfolioRepository.save(portfolio);

        String token = jwtService.generateToken(userAuth.getUsername());
        return new RegisterResponseDTO(userAuth.getUsername(), token);
    }

    public LoginResponseDTO login(LoginRequestDTO request){
        UserAuth userAuth = userAuthRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña inválidos"));

        if (!passwordEncoder.matches(request.password(), userAuth.getPasswordHash())) {
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        String token = jwtService.generateToken(userAuth.getUsername());
        return new LoginResponseDTO(userAuth.getUsername(), token);
    }
}
