package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "position")
public class PositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "position_name", unique = true)
    private String positionName;
    @Column(name = "description")
    private String description;
    @Column(name = "employment_type")
    private String employmentType;
    @Column(name = "requirements")
    private String requirements;

    public PositionEntity() {

    }

}
