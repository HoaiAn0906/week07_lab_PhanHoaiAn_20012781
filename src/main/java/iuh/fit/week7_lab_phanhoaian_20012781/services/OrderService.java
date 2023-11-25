package iuh.fit.week7_lab_phanhoaian_20012781.services;

import iuh.fit.week7_lab_phanhoaian_20012781.embeddable.OrderDetailId;
import iuh.fit.week7_lab_phanhoaian_20012781.models.*;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.EmployeeRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.OrderRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.ProductRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.repositories.UserRepository;
import iuh.fit.week7_lab_phanhoaian_20012781.request.OrderRequest;
import iuh.fit.week7_lab_phanhoaian_20012781.response.DataResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    public DataResponse<Order> createOrder(OrderRequest orderRequest) {
        Optional<User> user = userRepository.findByEmail(orderRequest.getEmployee().getEmail());
        Optional<Employee> employee = employeeRepository.findByUserId(user.get().getId());

        Order order = new Order();
        order.setCustomer(new Customer(orderRequest.getCustomer().getId()));
        order.setEmployee(employee.get());
        order.setDob(LocalDate.now());

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderRequest.getProducts().forEach(productRequest -> {
            OrderDetail orderDetail = new OrderDetail();
            OrderDetailId orderDetailId = new OrderDetailId();
            orderDetailId.setOrderId(order.getId()); // Assuming order.getId() returns the generated ID
            orderDetailId.setProductId(productRequest.getId()); // Assuming you have a product object
            orderDetail.setOrderDetailId(orderDetailId);
            orderDetail.setOrder(order);
            orderDetail.setQuantity(productRequest.getQuantity());
            orderDetail.setPrice(productRequest.getLastPrice());
            Product product = productRepository.findById(productRequest.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Product not found with ID: " + productRequest.getId())
            );
            orderDetail.setProduct(product);

            orderDetails.add(orderDetail);
        });

        order.setOrderDetails(orderDetails);

        orderRepository.save(order);

        return DataResponse.<Order>builder()
                .data(order)
                .status(200)
                .message("Order created successfully")
                .build();
    }

    public Map<String, Integer> getOrdersByMonth(int year) {
        Map<String, Integer> ordersByMonth = new LinkedHashMap<>();
        for (Month month : Month.values()) {
            ordersByMonth.put(month.toString(), 0);
        }

        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            if (order.getDob().getYear() == year) {
                String month = order.getDob().getMonth().toString();
                ordersByMonth.put(month, ordersByMonth.get(month) + 1);
            }
        }

        return ordersByMonth;
    }
}
