package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateEmployeeDTO {
    private Integer id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "age is required")
    private Integer age;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message = "Salary is required")
    private String salary;
    @NotNull(message = "position is required")
    private Integer positionId;
}
