package com.example.postoffice.spock.mapper.parcel

import com.example.postoffice.dto.parsel.RequestParcelDto
import com.example.postoffice.dto.parsel.ResponseParcelDto
import com.example.postoffice.entity.Parcel
import com.example.postoffice.entity.enums.ParcelType
import com.example.postoffice.mapper.parcel.ParcelMapper
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification


@SpringBootTest
class ParcelMapperTest extends Specification {

    @Autowired
    private ParcelMapper parcelMapper
    private Parcel parcel
    private RequestParcelDto requestParcelDto


    def setup() {
        parcel = Parcel.builder()
                .parcelType(ParcelType.LETTER)
                .recipientIndex(123456)
                .recipientAddress('test')
                .firstName('ivan')
                .lastName('ivanov')
                .historyPoints(new ArrayList<>())
                .build()

        requestParcelDto = RequestParcelDto.builder()
                .identifier(1L)
                .parcelType(ParcelType.LETTER)
                .recipientIndex(123456)
                .recipientAddress('test')
                .firstName('ivan')
                .lastName('ivanov')
                .build()
    }


    def "department mapper to entity"() {
        given:
        Parcel entity = parcelMapper.toEntity(requestParcelDto)

        expect:
        entity.parcelType == requestParcelDto.parcelType
    }


    def "department mapper to response"() {
        given:
        ResponseParcelDto response = parcelMapper.toResponse(parcel)

        expect:
        response.parcelType == parcel.parcelType
    }


    def "department mapper update"() {
        given:
        String value = 'test'
        requestParcelDto.lastName = value

        when:
        Parcel parcelResponse = parcelMapper.partialUpdate(requestParcelDto, parcel)

        then:
        parcelResponse.lastName ==  value
    }
}