package com.example.postoffice.dto.historypoint;

import com.example.postoffice.dto.AbstractRequestDto;
import com.example.postoffice.entity.enums.PointType;

import lombok.*;

import javax.validation.constraints.*;

/**
 * DTO for {@link com.example.postoffice.entity.HistoryPoint}
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestHistoryPointDto extends AbstractRequestDto {
    @NotNull
    private PointType pointType;
    @Min(100000)
    @Max(999999)
    private Integer indexDepartment;
}