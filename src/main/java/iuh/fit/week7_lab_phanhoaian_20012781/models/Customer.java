package iuh.fit.week7_lab_phanhoaian_20012781.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cust_name", length = 150, nullable = false)
    private String custName;

    @Column(unique = true, length = 150)
    private String email;

    @Column(length = 15, nullable = false)
    private String phone;

    @Column(length = 250, nullable = false)
    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Order> orders;

    public Customer(long id) {
        this.id = id;
    }
}
