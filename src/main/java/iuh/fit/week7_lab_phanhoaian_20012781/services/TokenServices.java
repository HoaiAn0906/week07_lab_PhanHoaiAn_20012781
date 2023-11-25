package iuh.fit.week7_lab_phanhoaian_20012781.services;

import iuh.fit.week7_lab_phanhoaian_20012781.repositories.TokenRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class TokenServices {
    private TokenRepository tokenRepository;
}
