package iuh.fit.week7_lab_phanhoaian_20012781.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import iuh.fit.week7_lab_phanhoaian_20012781.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "Employee.findAll", query = "from Employee"),
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name",nullable = false,length = 150)
    private String fullName;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonSerialize(using = DateSerializer.class)
    private Date dob;

    @Column(nullable = false,length = 150,unique = true)
    private String email;

    @Column(nullable = false,length = 15)
    private String phone;

    @Column(nullable = false,length = 250)
    private String address;

    @Column(nullable = false,length = 11)
    private EmployeeStatus status;

    @OneToMany(mappedBy = "employee",fetch = FetchType.EAGER)
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", orders=" + orders +
                ", user=" + user +
                '}';
    }

    public Employee(long id) {
        this.id = id;
    }
}