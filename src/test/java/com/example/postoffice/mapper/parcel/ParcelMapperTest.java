package com.example.postoffice.mapper.parcel;

import com.example.postoffice.dto.parsel.RequestParcelDto;
import com.example.postoffice.dto.parsel.ResponseParcelDto;
import com.example.postoffice.entity.Parcel;
import com.example.postoffice.entity.enums.ParcelType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class ParcelMapperTest {

    @Autowired
    private ParcelMapper parcelMapper;
    private Parcel parcel;
    private RequestParcelDto requestParcelDto;

    @BeforeEach
    public void setup() {
        parcel = Parcel.builder()
                .parcelType(ParcelType.LETTER)
                .recipientIndex(123456)
                .recipientAddress("test")
                .firstName("ivan")
                .lastName("ivanov")
                .historyPoints(new ArrayList<>())
                .build();

        requestParcelDto = RequestParcelDto.builder()
                .identifier(1L)
                .parcelType(ParcelType.LETTER)
                .recipientIndex(123456)
                .recipientAddress("test")
                .firstName("ivan")
                .lastName("ivanov")
                .build();
    }


    @Test
    public void departmentMapperToEntity() {
        Parcel entity = parcelMapper.toEntity(requestParcelDto);

        Assertions.assertEquals(entity.getParcelType(), requestParcelDto.getParcelType());
    }

    @Test
    public void departmentMapperToResponse() {
        ResponseParcelDto response = parcelMapper.toResponse(parcel);

        Assertions.assertEquals(response.getParcelType(), parcel.getParcelType());
    }

    @Test
    public void departmentMapperUpdate() {
        String value = "test";
        requestParcelDto.setLastName(value);

        Parcel parcelResponse = parcelMapper.partialUpdate(requestParcelDto, parcel);

        Assertions.assertEquals(parcelResponse.getLastName(), value);
    }
}