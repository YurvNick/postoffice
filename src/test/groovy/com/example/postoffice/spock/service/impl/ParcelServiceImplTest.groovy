package com.example.postoffice.spock.service.impl

import com.example.postoffice.entity.Department
import com.example.postoffice.entity.HistoryPoint
import com.example.postoffice.entity.Parcel
import com.example.postoffice.entity.enums.ParcelType
import com.example.postoffice.entity.enums.PointType
import com.example.postoffice.repository.ParcelRepository
import com.example.postoffice.service.DepartmentService
import com.example.postoffice.service.impl.ParcelServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

import static org.junit.jupiter.api.Assertions.assertThrows
import static org.mockito.Mockito.*
//В тестах нельзя использовать final поля
class ParcelServiceImplTest extends Specification {
    private ParcelRepository parcelRepository
    private DepartmentService departmentServiceImpl
    private ParcelServiceImpl parcelService
    private Parcel parcel

    def setup() {
        parcelRepository = Stub(ParcelRepository)
        departmentServiceImpl = Mock(DepartmentService)
        parcelService = new ParcelServiceImpl(parcelRepository, departmentServiceImpl)
        parcel = Parcel.builder()
                .parcelType(ParcelType.LETTER)
                .recipientIndex(123456)
                .recipientAddress('test')
                .firstName('ivan')
                .lastName('ivanov')
                .historyPoints(new ArrayList<>())
                .build()
    }


    def "parcel service create"() {
        given:
        parcelRepository.save(parcel) >> parcel

        when:
        Parcel result = parcelService.create(parcel)

        then:
        result != null
        parcel == result
    }


    def "parcel service find all"() {
        given: "подготавливаем объект страницы с наполнением"
        Pageable pageable = PageRequest.of(0, 10)
        List<Parcel> parcelList = List.of(parcel, parcel)
        Page<Parcel> parcels = new PageImpl<>(parcelList)
        parcelRepository.findAll(pageable) >> parcels

        when: "выполняем поиск всех элементов"
        Page<Parcel> page = parcelService.findAll(pageable)

        then: "проверяем содержимое страницы"
        page != null
        page.size == 2
    }


    def "parcel service find by id"() {
        given:
        Long identifierParcel = 1L
        parcelRepository.findById(identifierParcel) >> Optional.of((parcel))

        when:
        Parcel result = parcelService.findEntity(identifierParcel)

        then:
        result != null
        parcel == result
    }

    def "arrive at department"() {
        given:
        Long identifierParcel = 1L
        Integer departmentIndex = 123456

        departmentServiceImpl.findByIndex(departmentIndex) >> new Department()
        parcelRepository.findById(identifierParcel) >> Optional.ofNullable(parcel)
        parcelRepository.save(parcel) >> parcel

        when:
        Parcel parcelResponse = parcelService.arriveAtDepartment(identifierParcel, departmentIndex)

        then:
        parcelResponse != null
        parcel == parcelResponse
    }


    def "entity not found test"() {
        given:
        Long id = 1L
        parcelRepository.findById(id) >> Optional.empty()

        when:
        parcelService.update(parcel, id)
        then:
        final EntityNotFoundException exception = thrown()
    }
}