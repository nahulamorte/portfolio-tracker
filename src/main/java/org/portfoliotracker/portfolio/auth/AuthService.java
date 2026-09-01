package org.portfoliotracker.portfolio.auth;

import jakarta.transaction.Transactional;
import lombok.Builder;
import org.portfoliotracker.portfolio.auth.exception.EmailAlreadyExistsException;
import org.portfoliotracker.portfolio.auth.exception.UsernameAlreadyExistsException;
import org.portfoliotracker.portfolio.auth.dto.request.LoginRequestDTO;
import org.portfoliotracker.portfolio.auth.dto.request.RegisterRequestDTO;
import org.portfoliotracker.portfolio.auth.dto.response.LoginResponseDTO;
import org.portfoliotracker.portfolio.auth.dto.response.RegisterResponseDTO;
import org.portfoliotracker.portfolio.entity.Portfolio;
import org.portfoliotracker.portfolio.entity.UserApp;
import org.portfoliotracker.portfolio.repository.PortfolioRepository;
import org.portfoliotracker.portfolio.repository.UserAppRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Builder
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

        UserApp userApp = UserApp.builder()
                .name(request.name())
                .dateBirth(request.dateBirth())
                .build();
        userApp = userAppRepository.save(userApp);

        UserAuth userAuth = UserAuth.builder()
                .userApp(userApp)
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode((request.password())))
                .build();
        userAuthRepository.save(userAuth);

        Portfolio portfolio = Portfolio.builder()
                .user(userApp)
                .balance(BigDecimal.ZERO)
                .build();
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
