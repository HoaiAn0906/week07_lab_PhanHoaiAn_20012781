package iuh.fit.week7_lab_phanhoaian_20012781.services;

import iuh.fit.week7_lab_phanhoaian_20012781.enums.ProductStatus;
import iuh.fit.week7_lab_phanhoaian_20012781.models.Product;
import iuh.fit.week7_lab_phanhoaian_20012781.models.ProductImage;
import iuh.fit.week7_lab_phanhoaian_20012781.models.ProductPrice;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.ProductImageRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.ProductPriceRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.ProductRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.response.DataResponse;
import iuh.fit.week7_lab_phanhoaian_20012781.response.ProductResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final ProductImageRepository productImageRepository;
    private final ProductPriceRepository productPriceRepository;

    public DataResponse<Page<ProductResponse>> getAllProducts(String filterQ, int page, int limit, String sortBy, String sortDirection){
        // Tạo đối tượng Pageable để xác định trang và kích thước trang
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        // Gọi phương thức truy vấn cơ sở dữ liệu trong Repository để lấy dữ liệu sản phẩm với phân trang, sắp xếp và bộ lọc
        Page<Product> productsPage;
        if (filterQ != null && !filterQ.isEmpty()) {
            productsPage = productRepository.findByNameContainingIgnoreCase(filterQ, pageable);
        } else {
            productsPage = productRepository.findAll(pageable);
        }

        return DataResponse.<Page<ProductResponse>>builder()
                .data(productsPage.map(product -> {
                    ProductResponse productResponse = new ProductResponse();
                    productResponse.setId(product.getId());
                    productResponse.setName(product.getName());
                    productResponse.setDescription(product.getDescription());
                    productResponse.setUnit(product.getUnit());
                    productResponse.setManufacturer(product.getManufacturer());
                    productResponse.setStatus(product.getStatus());
                    productResponse.setProductImages(product.getProductImages());
                    productResponse.setProductPrices(product.getProductPrices());
                    productResponse.setLastPrice(product.getProductPrices().get(product.getProductPrices().size() - 1).getPrice());
                    return productResponse;
                }))
                .message("Get all products successfully")
                .status(200)
                .build();
    }

    public DataResponse<Product> createProduct(String name, String description, String unit, String manufacturerName, String status, MultipartFile image, double price){
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setUnit(unit);
        product.setManufacturer(manufacturerName);
        product.setStatus(ProductStatus.valueOf(status));

        ProductImage productImage = new ProductImage();
        if (image != null && !image.isEmpty()) {
            try {
                String uploadDirectory = "src/main/resources/static/products";
                String fileName = imageService.saveImageToStorage(uploadDirectory, image);
                System.out.println();

                productImage.setPath(fileName);
                productImage.setProduct(product);
                product.setProductImages(List.of(productImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ProductPrice productPrice = new ProductPrice();
        if (price > 0) {
            Date now = new Date();
            productPrice.setPriceDateTime(now);
            productPrice.setPrice(price);
            productPrice.setProduct(product);
            productPrice.setNote("initial");
            product.setProductPrices(List.of(productPrice));
        }

        return DataResponse.<Product>builder()
                .data(productRepository.save(product))
                .message("Create product successfully")
                .status(200)
                .build();
    }

    //Delete
    public DataResponse<Product> deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return DataResponse.<Product>builder()
                .data(product)
                .message("Delete product successfully")
                .status(200)
                .build();
    }
}
