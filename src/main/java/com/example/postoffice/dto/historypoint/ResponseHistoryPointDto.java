package com.example.postoffice.dto.historypoint;

import com.example.postoffice.dto.AbstractResponseDto;
import com.example.postoffice.entity.enums.PointType;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.postoffice.entity.HistoryPoint}
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseHistoryPointDto extends AbstractResponseDto {
    @NotNull
    private PointType pointType;
    @PastOrPresent
    private LocalDateTime appointmentDate;
    @Min(100000)
    @Max(999999)
    private Integer indexDepartment;
}