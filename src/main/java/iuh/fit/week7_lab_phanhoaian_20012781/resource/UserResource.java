package iuh.fit.week7_lab_phanhoaian_20012781.resource;

import iuh.fit.week7_lab_phanhoaian_20012781.models.User;
import iuh.fit.week7_lab_phanhoaian_20012781.response.DataResponse;
import iuh.fit.week7_lab_phanhoaian_20012781.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserResource {
    private final UserServices userServices;

    @GetMapping("/users")
    public ResponseEntity<DataResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(userServices.getAllUsers());
    }

    //by id
    @GetMapping("/users/{id}")
    public ResponseEntity<DataResponse<User>> getUserById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userServices.getUserById(id));
    }
}
