package iuh.fit.week7_lab_phanhoaian_20012781.repositories;

import iuh.fit.week7_lab_phanhoaian_20012781.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
