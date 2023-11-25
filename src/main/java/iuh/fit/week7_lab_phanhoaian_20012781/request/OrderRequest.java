package iuh.fit.week7_lab_phanhoaian_20012781.request;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequest {
    private CustomerRequest customer;
    private EmployeeRequest employee;
    private List<ProductRequest> products;
    private long total;
}
