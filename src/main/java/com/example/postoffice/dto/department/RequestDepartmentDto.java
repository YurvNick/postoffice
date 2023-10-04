package com.example.postoffice.dto.department;

import com.example.postoffice.dto.AbstractRequestDto;


import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link com.example.postoffice.entity.Department}
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestDepartmentDto extends AbstractRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String departmentAddress;
    @Min(100000)
    @Max(999999)
    private Integer index;
}