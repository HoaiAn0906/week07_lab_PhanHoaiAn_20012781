package iuh.fit.week7_lab_phanhoaian_20012781.services;

import iuh.fit.week7_lab_phanhoaian_20012781.models.Token;
import iuh.fit.week7_lab_phanhoaian_20012781.models.TokenType;
import iuh.fit.week7_lab_phanhoaian_20012781.models.User;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.TokenRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.UserRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.request.LoginRequest;
import iuh.fit.week7_lab_phanhoaian_20012781.request.RegisterRequest;
import iuh.fit.week7_lab_phanhoaian_20012781.response.AuthenticationResponse;
import iuh.fit.week7_lab_phanhoaian_20012781.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ImageService imageService;

    public AuthenticationResponse register(RegisterRequest request, MultipartFile avatarFile) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRegisteredAt(java.time.Instant.now());

        // Handle the avatar file as needed, for example, save it to the server
        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String uploadDirectory = "src/main/resources/static/avatar";
                String fileName = imageService.saveImageToStorage(uploadDirectory, avatarFile);
                System.out.println(fileName);

                user.setAvatar(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .tokenType("Bearer")
                .expiresAt(java.time.Instant.now().plusMillis(jwtService.getJwtExpirationInMillis()))
                .build();
    }

    public DataResponse<AuthenticationResponse> login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .tokenType("Bearer")
                .expiresAt(java.time.Instant.now().plusMillis(jwtService.getJwtExpirationInMillis()))
                .user(user)
                .build();

        return DataResponse.<AuthenticationResponse>builder()
                .data(authenticationResponse)
                .message("Login successfully")
                .status(200)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
