package iuh.fit.week7_lab_phanhoaian_20012781.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private long id;
    private String name;
    private String description;
    private String unit;
    private String manufacturer_name;
    private String image;
    private long lastPrice;
    private long quantity;
}
