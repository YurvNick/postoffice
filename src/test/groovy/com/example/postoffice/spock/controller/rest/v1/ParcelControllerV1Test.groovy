package com.example.postoffice.spock.controller.rest.v1

import com.example.postoffice.controller.rest.v1.ParcelControllerV1
import com.example.postoffice.dto.historypoint.ResponseHistoryPointDto
import com.example.postoffice.dto.parsel.RequestParcelDto
import com.example.postoffice.dto.parsel.ResponseParcelDto
import com.example.postoffice.entity.HistoryPoint
import com.example.postoffice.entity.Parcel
import com.example.postoffice.entity.enums.ParcelType
import com.example.postoffice.mapper.historyPoint.HistoryPointMapper
import com.example.postoffice.mapper.parcel.ParcelMapper
import com.example.postoffice.service.ParcelService
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.*
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = ParcelControllerV1.class)
@MockBean(JpaMetamodelMappingContext.class)
class ParcelControllerV1Test extends Specification {
    @Autowired
    private MockMvc mockMvc
    @Autowired
    private ObjectMapper objectMapper
    @MockBean(name= 'ParcelServiceImpl')
    private ParcelService parcelService
    @MockBean
    private HistoryPointMapper historyPointMapper
    @MockBean
    private ParcelMapper mapper
    private Parcel parcel
    private RequestParcelDto requestParcelDto
    private ResponseParcelDto responseParcelDto


    def setup() {
        parcel = Parcel.builder()
                .id(1L)
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

        responseParcelDto = ResponseParcelDto.builder()
                .id(1L)
                .parcelType(ParcelType.LETTER)
                .recipientIndex(123456)
                .recipientAddress('test')
                .firstName('ivan')
                .lastName('ivanov')
                .historyPoints(new ArrayList<>())
                .build()
    }


    def "parcel controller create"() throws Exception {

        given:
        when(mapper.toEntity(requestParcelDto)).thenReturn(parcel)
        when(parcelService.create(parcel)).thenReturn(parcel)
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto)

        ResultActions response = mockMvc.perform(post('/api/v1/parcel/create')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestParcelDto))
                .characterEncoding('utf-8'))

        expect:
        response.andExpect(MockMvcResultMatchers.status().isOk())
    }


    def "parcel controller find all"() throws Exception {
        given:
        int offset = 0
        int limit = 10
        Sort.Order order = Sort.Order.by('id').with(Sort.Direction.ASC)

        List<Parcel> parcels = List.of(parcel, parcel)
        Page<Parcel> page = new PageImpl<>(parcels)
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(order))

        when(parcelService.findAll(pageable)).thenReturn(page)

        ResultActions response = mockMvc.perform(get('/api/v1/parcel/all')
                .param('offset', String.valueOf(offset))
                .param('limit', String.valueOf(limit))
                .param('param', String.valueOf(Sort.Direction.ASC))
                .characterEncoding('utf-8'))

        expect:
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("content.length()").value(2))
    }


    def "parcel controller find entity"() throws Exception {
        given:
        long id = 1L

        when(mapper.toEntity(requestParcelDto)).thenReturn(parcel)
        when(parcelService.findEntity(id)).thenReturn(parcel)
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto)

        ResultActions response = mockMvc.perform(get('/api/v1/parcel/get')
                .contentType(MediaType.APPLICATION_JSON)
                .param('id', String.valueOf(id))
                .characterEncoding('utf-8'))

        expect:
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("firstName", CoreMatchers.is(responseParcelDto.firstName)))
                .andExpect(jsonPath("lastName", CoreMatchers.is(responseParcelDto.lastName)))
    }


    def "registration parcel"() throws Exception {
        given:
        int index = 1234556

        when((mapper.toEntity(requestParcelDto))).thenReturn(parcel)
        when(parcelService.registrationParcel(parcel, index)).thenReturn(parcel)
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto)

        ResultActions response = mockMvc.perform(post('/api/v1/parcel/registration')
                .contentType(MediaType.APPLICATION_JSON)
                .param('index', String.valueOf(index))
                .content(objectMapper.writeValueAsString(requestParcelDto))
                .characterEncoding('utf-8'))

        expect:
        response.andExpect(MockMvcResultMatchers.status().isOk())
    }


    def "arrive at department"() throws Exception {
        given:
        int index = 1234556
        long idParcel = 1L

        when(parcelService.arriveAtDepartment(idParcel, index)).thenReturn(parcel)
        when(parcelService.findEntity(idParcel)).thenReturn(parcel)
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto)

        ResultActions response = mockMvc.perform(get('/api/v1/parcel/arrive')
                .param('identifierParcel', '1')
                .param('index', String.valueOf(index))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding('utf-8'))

        expect:
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("firstName", CoreMatchers.is(responseParcelDto.firstName)))
                .andExpect(jsonPath("lastName", CoreMatchers.is(responseParcelDto.lastName)))
    }


    def "test leave at department"() throws Exception {
        given:
        long idParcel = 1L

        when(parcelService.leaveDepartment(idParcel)).thenReturn(parcel)
        when(parcelService.findEntity(idParcel)).thenReturn((parcel))
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto)


        ResultActions response = mockMvc.perform(get('/api/v1/parcel/leave')
                .param('identifierParcel', String.valueOf(idParcel))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding('utf-8'))

        expect:
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("firstName", CoreMatchers.is(responseParcelDto.firstName)))
                .andExpect(jsonPath("lastName", CoreMatchers.is(responseParcelDto.lastName)))
    }


    def "delivery to recipient"() throws Exception {
        given:
        long idParcel = 1L

        when(parcelService.deliveryToRecipient(idParcel)).thenReturn(parcel)
        when(mapper.toResponse(parcel)).thenReturn(responseParcelDto)
        ResultActions response = mockMvc.perform(get('/api/v1/parcel/delivery')

                .param('identifierParcel', String.valueOf(idParcel))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding('utf-8'))

        expect:
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("firstName", CoreMatchers.is(responseParcelDto.firstName)))
                .andExpect(jsonPath("lastName", CoreMatchers.is(responseParcelDto.lastName)))
    }


    def "get history parcel"() throws Exception {
        given:
        long idParcel = 1L
        HistoryPoint historyPoint1 = new HistoryPoint()
        HistoryPoint historyPoint2 = new HistoryPoint()
        List<HistoryPoint> list = List.of(historyPoint1, historyPoint2)

        when(parcelService.getHistoryParcel(idParcel)).thenReturn(list)
        when(historyPointMapper.toResponse(historyPoint1)).thenReturn(new ResponseHistoryPointDto())
        when(historyPointMapper.toResponse(historyPoint2)).thenReturn(new ResponseHistoryPointDto())

        ResultActions response = mockMvc.perform(get('/api/v1/parcel/history')
                .contentType(MediaType.APPLICATION_JSON)
                .param('identifierParcel', String.valueOf(idParcel))
                .characterEncoding('utf-8'))

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("length()").value(2))

    }
}