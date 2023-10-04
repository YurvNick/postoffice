package com.example.postoffice.dto.parsel;

import com.example.postoffice.dto.AbstractRequestDto;
import com.example.postoffice.entity.enums.ParcelType;

import lombok.*;

import javax.validation.constraints.*;

/**
 * DTO for {@link com.example.postoffice.entity.Parcel}
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestParcelDto extends AbstractRequestDto {
    private Long identifier;
    @NotNull
    private ParcelType parcelType;
    @Min(100000)
    @Max(999999)
    private Integer recipientIndex;
    @NotBlank
    private String recipientAddress;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}