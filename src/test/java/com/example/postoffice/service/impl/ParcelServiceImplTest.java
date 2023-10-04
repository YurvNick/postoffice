package com.example.postoffice.service.impl;

import com.example.postoffice.entity.Department;
import com.example.postoffice.entity.HistoryPoint;
import com.example.postoffice.entity.Parcel;
import com.example.postoffice.entity.enums.ParcelType;
import com.example.postoffice.entity.enums.PointType;
import com.example.postoffice.repository.ParcelRepository;
import com.example.postoffice.service.DepartmentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParcelServiceImplTest {
    @Mock
    private ParcelRepository parcelRepository;
    @Mock(name = "DepartmentServiceImpl")
    private DepartmentService departmentServiceImpl;
    @InjectMocks
    private ParcelServiceImpl parcelService;
    private Parcel parcel;


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
    }

    @Test
    public void ParcelServiceImplCreate() {
        when(parcelRepository.save(parcel)).thenReturn(parcel);

        Parcel parcelResponse = parcelService.create(parcel);

        Assertions.assertThat(parcelResponse).isNotNull();
        Assertions.assertThat(parcel).isEqualTo(parcelResponse);

        verify(parcelRepository).save(parcel);
    }

    @Test
    public void ParcelServiceImplFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Parcel> parcelList = List.of(parcel, parcel);
        Page<Parcel> parcels = new PageImpl<>(parcelList);

        when(parcelRepository.findAll(pageable)).thenReturn(parcels);

        Page<Parcel> all = parcelService.findAll(pageable);

        Assertions.assertThat(all).isNotNull();
        Assertions.assertThat(all.getSize()).isEqualTo(2);
    }

    @Test
    public void ParcelServiceFindById() {
        Long identifierParcel = 1L;
        when(parcelRepository.findById(identifierParcel)).thenReturn(Optional.of((parcel)));

        Parcel parcelResponse = parcelService.findEntity(identifierParcel);

        Assertions.assertThat(parcelResponse).isNotNull();
        Assertions.assertThat(parcel).isEqualTo(parcelResponse);

        verify(parcelRepository).findById(identifierParcel);
    }

    @Test
    public void ParcelServiceUpdate() {
        Long identifierParcel = 1L;

        when(parcelRepository.findById(identifierParcel)).thenReturn(Optional.of(parcel));
        when(parcelRepository.save(parcel)).thenReturn(parcel);

        Parcel parcelUpdate = parcelService.update(parcel, identifierParcel);

        Assertions.assertThat(parcelUpdate).isNotNull();

        verify(parcelRepository).findById(identifierParcel);
    }

    @Test
    public void ParcelServiceDelete() {
        Long identifierParcel = 1L;

        when(parcelRepository.findById(identifierParcel)).thenReturn(Optional.of(parcel));

        parcelService.delete(identifierParcel);

        verify(parcelRepository).findById(identifierParcel);
        verify(parcelRepository).delete(parcel);
    }


    @Test
    void registrationParcel() {
        Integer startIndexDepartment = 123456;

        when(parcelRepository.save(parcel)).thenReturn(parcel);

        Parcel parcelResponse = parcelService.registrationParcel(parcel, startIndexDepartment);

        Assertions.assertThat(parcelResponse).isNotNull();
        Assertions.assertThat(parcel).isEqualTo(parcelResponse);

        verify(departmentServiceImpl).findByIndex(startIndexDepartment);
        verify(parcelRepository).save(parcel);
    }

    @Test
    void arriveAtDepartment() {
        Long identifierParcel = 1L;
        Integer departmentIndex = 123456;

        when(departmentServiceImpl.findByIndex(departmentIndex)).thenReturn(new Department());
        when(parcelRepository.findById(identifierParcel)).thenReturn(Optional.ofNullable(parcel));
        when(parcelRepository.save(parcel)).thenReturn(parcel);

        Parcel parcelResponse = parcelService.arriveAtDepartment(identifierParcel, departmentIndex);

        Assertions.assertThat(parcelResponse).isNotNull();
        Assertions.assertThat(parcel).isEqualTo(parcelResponse);

        verify(departmentServiceImpl).findByIndex(departmentIndex);
        verify(parcelRepository, times(2)).findById(identifierParcel);
        verify(parcelRepository).save(parcel);

    }

    @Test
    void leaveDepartment() {
        Long identifierParcel = 1L;

        parcel.getHistoryPoints().add(new HistoryPoint());
        parcel.getHistoryPoints().add(new HistoryPoint());

        when(parcelRepository.findById(identifierParcel)).thenReturn(Optional.ofNullable(parcel));
        when(parcelRepository.save(parcel)).thenReturn(parcel);

        Parcel parcelResponse = parcelService.leaveDepartment(identifierParcel);

        Assertions.assertThat(parcelResponse).isNotNull();
        Assertions.assertThat(parcel).isEqualTo(parcelResponse);

        verify(parcelRepository, times(2)).findById(identifierParcel);
        verify(parcelRepository).save(parcel);
    }

    @Test
    void deliveryToRecipient() {
        Long identifierParcel = 1L;
        HistoryPoint historyPointFirst = HistoryPoint.builder().pointType(PointType.REGISTRATION).build();
        HistoryPoint historyPointLast = HistoryPoint.builder().pointType(PointType.DELIVERED).build();
        parcel.getHistoryPoints().add(historyPointFirst);
        parcel.getHistoryPoints().add(historyPointLast);

        when(parcelRepository.findById(identifierParcel)).thenReturn(Optional.ofNullable(parcel));
        when(parcelRepository.save(parcel)).thenReturn(parcel);

        Parcel parcelResponse = parcelService.deliveryToRecipient(identifierParcel);

        Assertions.assertThat(parcelResponse).isNotNull();
        Assertions.assertThat(parcel).isEqualTo(parcelResponse);

        verify(parcelRepository, times(1)).findById(identifierParcel);
        verify(parcelRepository).save(parcel);
    }

    @Test
    void getHistoryParcel() {
        Long identifierParcel = 1L;
        HistoryPoint historyPointFirst = HistoryPoint.builder().pointType(PointType.REGISTRATION).build();
        HistoryPoint historyPointLast = HistoryPoint.builder().pointType(PointType.DELIVERED).build();
        parcel.getHistoryPoints().add(historyPointFirst);
        parcel.getHistoryPoints().add(historyPointLast);

        when(parcelRepository.findById(identifierParcel)).thenReturn(Optional.ofNullable(parcel));

        List<HistoryPoint> historyParcel = parcelService.getHistoryParcel(identifierParcel);

        Assertions.assertThat(historyParcel).isNotNull();
        Assertions.assertThat(historyParcel.size()).isEqualTo(2);

        verify(parcelRepository).findById(identifierParcel);
    }

    @Test
    public void entityNotFoundTest() {
        Long id = 1L;
        Mockito.when(parcelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> parcelService.update(parcel, id));
    }
}