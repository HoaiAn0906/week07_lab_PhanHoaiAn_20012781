package iuh.fit.week7_lab_phanhoaian_20012781.resource;

import iuh.fit.week7_lab_phanhoaian_20012781.models.Order;
import iuh.fit.week7_lab_phanhoaian_20012781.request.OrderRequest;
import iuh.fit.week7_lab_phanhoaian_20012781.response.DataResponse;
import iuh.fit.week7_lab_phanhoaian_20012781.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderResource {
    private final OrderService orderService;
    @PostMapping("/orders")
    public ResponseEntity<DataResponse<?>> createOrder(
            @RequestBody OrderRequest orderRequest
            ){
        System.out.println("Creating order...");
        System.out.println(orderRequest);

        DataResponse<Order> data = orderService.createOrder(orderRequest);

        return ResponseEntity.status(200).body(data);
    }
}
