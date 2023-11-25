package iuh.fit.week7_lab_phanhoaian_20012781.response;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import iuh.fit.week7_lab_phanhoaian_20012781.enums.ProductStatus;
import iuh.fit.week7_lab_phanhoaian_20012781.models.ProductImage;
import iuh.fit.week7_lab_phanhoaian_20012781.models.ProductPrice;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ProductResponse {
    private long id;
    private String name;
    private String description;
    private String unit;
    private String manufacturer;
    private ProductStatus status;
    private List<ProductImage> productImages;
    private List<ProductPrice> productPrices;
    private double lastPrice;
}
