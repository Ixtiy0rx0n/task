package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionDTO {
    private Integer id;
    @NotBlank(message = "position name is required")
    private String positionName;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "employment type is required")
    private String employmentType;
    @NotBlank(message = "requirements is required")
    private String requirements;


}
