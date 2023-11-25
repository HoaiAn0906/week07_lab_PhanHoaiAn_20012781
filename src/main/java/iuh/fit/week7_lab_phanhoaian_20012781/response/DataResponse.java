package iuh.fit.week7_lab_phanhoaian_20012781.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> {
    private T data;
    private String message;
    private int status;
}