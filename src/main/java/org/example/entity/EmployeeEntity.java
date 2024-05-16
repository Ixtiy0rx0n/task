package org.example.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "age")
    private Integer age;
    @Column(name = "address")
    private String address;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "salary")
    private String salary;

    @ManyToOne
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    private PositionEntity position;
    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;


    public EmployeeEntity() {

    }
}
