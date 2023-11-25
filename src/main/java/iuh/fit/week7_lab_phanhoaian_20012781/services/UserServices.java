package iuh.fit.week7_lab_phanhoaian_20012781.services;

import iuh.fit.week7_lab_phanhoaian_20012781.models.User;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.UserRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.response.DataResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServices {
    private final UserRepository userRepository;
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public DataResponse<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String baseUrl = attributes.getRequest().getScheme() + "://" +
                attributes.getRequest().getServerName() + ":" +
                attributes.getRequest().getServerPort() +
                "/image/avatar/";
        for (User user : users) {
            String avatarUrl = user.getAvatar();
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                user.setAvatar(baseUrl + avatarUrl);
            }
        }

        return DataResponse.<List<User>>builder()
                .data(users)
                .message("Get all users successfully")
                .status(200)
                .build();
    }

    public DataResponse<User> getUserById(Long id) {
        return DataResponse.<User>builder()
                .data(userRepository.findById(id).orElseThrow())
                .message("Get user by id successfully")
                .status(200)
                .build();
    }
}
