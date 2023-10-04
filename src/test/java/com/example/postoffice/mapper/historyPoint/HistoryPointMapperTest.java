package com.example.postoffice.mapper.historyPoint;

import com.example.postoffice.dto.historypoint.RequestHistoryPointDto;
import com.example.postoffice.dto.historypoint.ResponseHistoryPointDto;
import com.example.postoffice.entity.HistoryPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class HistoryPointMapperTest {

    @Autowired
    private HistoryPointMapper historyPointMapper;
    private HistoryPoint historyPoint;
    private RequestHistoryPointDto requestHistoryPointDto;


    @BeforeEach
    public void setup() {
        historyPoint = HistoryPoint.builder()
                .indexDepartment(123456)
                .appointmentDate(LocalDateTime.now())
                .build();

        requestHistoryPointDto = RequestHistoryPointDto.builder()
                .indexDepartment(123456)
                .build();
    }

    @Test
    public void departmentMapperToEntity() {
        HistoryPoint entity = historyPointMapper.toEntity(requestHistoryPointDto);

        Assertions.assertEquals(entity.getIndexDepartment(), requestHistoryPointDto.getIndexDepartment());
    }

    @Test
    public void departmentMapperToResponse() {
        ResponseHistoryPointDto response = historyPointMapper.toResponse(historyPoint);

        Assertions.assertEquals(response.getIndexDepartment(),historyPoint.getIndexDepartment());
    }

    @Test
    public void departmentMapperUpdate() {
        int index = 888888;
        requestHistoryPointDto.setIndexDepartment(index);
        HistoryPoint update = historyPointMapper.partialUpdate(requestHistoryPointDto, historyPoint);

        Assertions.assertEquals(update.getIndexDepartment(),index);
    }
}