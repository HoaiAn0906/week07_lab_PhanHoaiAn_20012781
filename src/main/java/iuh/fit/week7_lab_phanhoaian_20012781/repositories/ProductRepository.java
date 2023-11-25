package iuh.fit.week7_lab_phanhoaian_20012781.repositories;

import iuh.fit.week7_lab_phanhoaian_20012781.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String productName, Pageable pageable);
}
