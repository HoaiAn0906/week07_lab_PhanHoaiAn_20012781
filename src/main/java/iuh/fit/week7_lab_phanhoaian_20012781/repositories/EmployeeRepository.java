package iuh.fit.week7_lab_phanhoaian_20012781.repositories;

import iuh.fit.week7_lab_phanhoaian_20012781.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUserId(Long userId);
}
