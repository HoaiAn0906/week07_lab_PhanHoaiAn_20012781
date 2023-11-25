package iuh.fit.week7_lab_phanhoaian_20012781.resource;

import iuh.fit.week7_lab_phanhoaian_20012781.models.Order;
import iuh.fit.week7_lab_phanhoaian_20012781.response.DataResponse;
import iuh.fit.week7_lab_phanhoaian_20012781.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DashboardResource {
    private final OrderService orderService;

    // thống kế order theo tháng của năm được chọn
    @GetMapping("/dashboard/orders_by_month/{year}")
    public ResponseEntity<DataResponse<?>> getOrdersByMonth(
            @PathVariable int year
    ){
        Map<String, Integer> data = orderService.getOrdersByMonth(year);

        return ResponseEntity.status(200).body(DataResponse.<Map<String, Integer>>builder()
                .data(data)
                .status(200)
                .message("Orders by month")
                .build());
    }
}
