package iuh.fit.week7_lab_phanhoaian_20012781.resource;

import iuh.fit.week7_lab_phanhoaian_20012781.enums.ProductStatus;
import iuh.fit.week7_lab_phanhoaian_20012781.models.Product;
import iuh.fit.week7_lab_phanhoaian_20012781.response.DataResponse;
import iuh.fit.week7_lab_phanhoaian_20012781.response.ProductResponse;
import iuh.fit.week7_lab_phanhoaian_20012781.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService productServices;

    //    filter[q]=&filter[department_id]=&filter[type]=&sort=-created_at&page=1&limit=20
    @GetMapping("/products")
    public ResponseEntity<DataResponse<Page<ProductResponse>>> getAllProducts(
            @RequestParam(value = "filter[q]", defaultValue = "") String filterQ,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.ok(productServices.getAllProducts(filterQ, page, limit, sortBy, sortDirection));
    }

    @PostMapping("/products")
    public ResponseEntity<DataResponse<Product>> createProduct(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "manufacturer_name", defaultValue = "") String manufacturerName,
            @RequestParam(value = "unit", defaultValue = "") String unit,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "price") double price,
            @RequestParam(value = "status", defaultValue = "") String status,
            @RequestParam(value = "image") MultipartFile image
            ) {

        return ResponseEntity.ok(productServices.createProduct(name, description, unit, manufacturerName, status, image, price));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<DataResponse<Product>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productServices.deleteProduct(id));
    }
}
