package iuh.fit.week7_lab_phanhoaian_20012781.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import iuh.fit.week7_lab_phanhoaian_20012781.embeddable.OrderDetailId;
import iuh.fit.week7_lab_phanhoaian_20012781.request.OrderRequest;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {
    @EmbeddedId
    private OrderDetailId orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId") // This maps the orderId attribute in OrderDetailId
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore // To avoid circular serialization issues
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId") // This maps the productId attribute in OrderDetailId
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    private double quantity;
    private double price;

    @Column(length = 250, nullable = true)
    private String note;
}
