package com.example.postoffice.controller.rest.v1;

import com.example.postoffice.dto.historypoint.ResponseHistoryPointDto;
import com.example.postoffice.dto.parsel.RequestParcelDto;
import com.example.postoffice.dto.parsel.ResponseParcelDto;
import com.example.postoffice.entity.HistoryPoint;
import com.example.postoffice.entity.Parcel;
import com.example.postoffice.entity.enums.ParcelType;

import com.example.postoffice.mapper.historyPoint.HistoryPointMapper;
import com.example.postoffice.mapper.parcel.ParcelMapper;
import com.example.postoffice.service.ParcelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ParcelControllerV1.class)
@MockBean(JpaMetamodelMappingContext.class)
class ParcelControllerV1Test {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean(name="ParcelServiceImpl")
    private ParcelService parcelService;
    @MockBean
    private HistoryPointMapper historyPointMapper;
    @MockBean
    private ParcelMapper mapper;
    private Parcel parcel;
    private RequestParcelDto requestParcelDto;
    private ResponseParcelDto responseParcelDto;


    @BeforeEach
    public void setup() {
        parcel = Parcel.builder()
                .id(1L)
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

        responseParcelDto = ResponseParcelDto.builder()
                .id(1L)
                .parcelType(ParcelType.LETTER)
                .recipientIndex(123456)
                .recipientAddress("test")
                .firstName("ivan")
                .lastName("ivanov")
                .historyPoints(new ArrayList<>())
                .build();
    }

    @Test
    public void ParcelControllerCreate() throws Exception {

        when(mapper.toEntity(requestParcelDto)).thenReturn(parcel);
        when(parcelService.create(parcel)).thenReturn(parcel);
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto);

        ResultActions response = mockMvc.perform(post("/api/v1/parcel/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestParcelDto))
                .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void ParcelControllerFindAll() throws Exception {
        int offset = 0;
        int limit = 10;
        Sort.Order order = Sort.Order.by("id").with(Sort.Direction.ASC);

        List<Parcel> parcels = List.of(parcel, parcel);
        Page<Parcel> page = new PageImpl<>(parcels);
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(order));

        when(parcelService.findAll(pageable)).thenReturn(page);

        ResultActions response = mockMvc.perform(get("/api/v1/parcel/all")
                .param("offset", String.valueOf(offset))
                .param("limit", String.valueOf(limit))
                .param("param", String.valueOf(Sort.Direction.ASC))
                .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    public void ParcelControllerFindEntity() throws Exception {
        long id = 1L;

        when(mapper.toEntity(requestParcelDto)).thenReturn(parcel);
        when(parcelService.findEntity(id)).thenReturn(parcel);
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto);

        ResultActions response = mockMvc.perform(get("/api/v1/parcel/get")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(id))
                .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(responseParcelDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(responseParcelDto.getLastName())));
    }


    @Test
    void registrationParcel() throws Exception {
        int index = 1234556;

        when((mapper.toEntity(requestParcelDto))).thenReturn(parcel);
        when(parcelService.registrationParcel(parcel, index)).thenReturn(parcel);
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto);

        ResultActions response = mockMvc.perform(post("/api/v1/parcel/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("index", String.valueOf(index))
                .content(objectMapper.writeValueAsString(requestParcelDto))
                .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void arriveAtDepartment() throws Exception {
        int index = 1234556;
        long idParcel = 1L;

        when(parcelService.arriveAtDepartment(idParcel, index)).thenReturn(parcel);
        when(parcelService.findEntity(idParcel)).thenReturn(parcel);
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto);

        ResultActions response = mockMvc.perform(get("/api/v1/parcel/arrive")
                .param("identifierParcel", "1")
                .param("index", String.valueOf(index))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(responseParcelDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(responseParcelDto.getLastName())));
    }

    @Test
    void testLeaveAtDepartment() throws Exception {
        long idParcel = 1L;

        when(parcelService.leaveDepartment(idParcel)).thenReturn(parcel);
        when(parcelService.findEntity(idParcel)).thenReturn((parcel));
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto);


        ResultActions response = mockMvc.perform(get("/api/v1/parcel/leave")
                .param("identifierParcel", String.valueOf(idParcel))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(responseParcelDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(responseParcelDto.getLastName())));
    }

    @Test
    void deliveryToRecipient() throws Exception {
        long idParcel = 1L;

        when(parcelService.deliveryToRecipient(idParcel)).thenReturn(parcel);
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto);
        ResultActions response = mockMvc.perform(get("/api/v1/parcel/delivery")

                .param("identifierParcel", String.valueOf(idParcel))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(responseParcelDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(responseParcelDto.getLastName())));
    }

    @Test
    void getHistoryParcel() throws Exception {
        long idParcel = 1L;
        HistoryPoint historyPoint1 = new HistoryPoint();
        HistoryPoint historyPoint2 = new HistoryPoint();
        List<HistoryPoint> list = List.of(historyPoint1, historyPoint2);

        when(parcelService.getHistoryParcel(idParcel)).thenReturn(list);
        when(historyPointMapper.toResponse(historyPoint1)).thenReturn(new ResponseHistoryPointDto());
        when(historyPointMapper.toResponse(historyPoint2)).thenReturn(new ResponseHistoryPointDto());

        ResultActions response = mockMvc.perform(get("/api/v1/parcel/history")
                .contentType(MediaType.APPLICATION_JSON)
                .param("identifierParcel", String.valueOf(idParcel))
                .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(parcelService).getHistoryParcel(idParcel);
    }
}