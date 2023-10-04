package com.example.postoffice.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.postoffice.entity.HistoryPoint;
import com.example.postoffice.repository.HistoryPointRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HistoryPointServiceImplTest {

    @Mock(name = "HistoryPointRepository")
    private HistoryPointRepository historyPointRepository;
    @InjectMocks
    private HistoryPointServiceImpl historyPointService;
    private HistoryPoint historyPoint;


    @BeforeEach
    public void setup() {
        historyPoint = HistoryPoint.builder()
                .indexDepartment(123456)
                .appointmentDate(LocalDateTime.now())
                .build();
    }

    @Test
    public void historyPointServiceImplCreate() {
        when(historyPointRepository.save(historyPoint)).thenReturn(historyPoint);

        HistoryPoint historyPointResponse = historyPointService.create(historyPoint);

        Assertions.assertThat(historyPointResponse).isNotNull();

        verify(historyPointRepository).save(historyPoint);
    }

    @Test
    public void historyPointServiceImplUpdate() {
        Long id = 1L;

        when(historyPointRepository.findById(id)).thenReturn(Optional.ofNullable(historyPoint));
        when(historyPointRepository.save(historyPoint)).thenReturn(historyPoint);

        HistoryPoint historyPointUpdate = historyPointService.update(historyPoint, id);

        Assertions.assertThat(historyPointUpdate).isNotNull();

        verify(historyPointRepository).save(historyPoint);
    }

    @Test
    public void historyPointServiceImplDelete() {
        Long id = 1L;

        when(historyPointRepository.findById(id)).thenReturn(Optional.ofNullable(historyPoint));

        historyPointService.delete(id);

        verify(historyPointRepository).findById(id);
        verify(historyPointRepository).delete(historyPoint);
    }

    @Test
    public void historyPointServiceImplFindById() {
        Long id = 1L;

        when(historyPointRepository.findById(id)).thenReturn(Optional.of(historyPoint));

        HistoryPoint departmentResponse = historyPointService.findEntity(id);

        Assertions.assertThat(departmentResponse).isNotNull();

        verify(historyPointRepository).findById(id);
    }

    @Test
    public void entityNotFoundTest() {
        Long id = 1L;
        Mockito.when(historyPointRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> historyPointService.update(historyPoint, id));
    }

    @Test
    public void historyPointServiceImplFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<HistoryPoint> historyPointList = List.of(historyPoint, historyPoint);
        Page<HistoryPoint> historyPoints = new PageImpl<>(historyPointList);

        when(historyPointRepository.findAll(pageable)).thenReturn(historyPoints);

        Page<HistoryPoint> all = historyPointService.findAll(pageable);

        Assertions.assertThat(all).isNotNull();
    }
}