package iuh.fit.week7_lab_phanhoaian_20012781.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaymentResponse implements Serializable {
    private String status;
    private String message;
    private String URL;
}
