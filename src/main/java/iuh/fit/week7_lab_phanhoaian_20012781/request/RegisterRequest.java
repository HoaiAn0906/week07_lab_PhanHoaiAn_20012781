package iuh.fit.week7_lab_phanhoaian_20012781.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
}
