package com.example.postoffice.controller.rest.v1;

import com.example.postoffice.controller.rest.BaseController;
import com.example.postoffice.dto.historypoint.ResponseHistoryPointDto;
import com.example.postoffice.dto.parsel.RequestParcelDto;
import com.example.postoffice.dto.parsel.ResponseParcelDto;
import com.example.postoffice.entity.Parcel;
import com.example.postoffice.mapper.historyPoint.HistoryPointMapper;
import com.example.postoffice.mapper.parcel.ParcelMapper;
import com.example.postoffice.service.ParcelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;


@Validated
@RestController
@RequestMapping("/api/v1/parcel")
public class ParcelControllerV1
        extends
        BaseController<Parcel,
                RequestParcelDto,
                ResponseParcelDto,
                ParcelService,
                ParcelMapper> {

    private final HistoryPointMapper historyPointMapper;

    @Autowired
    public ParcelControllerV1(ParcelService service,
                              ParcelMapper mapper,
                              HistoryPointMapper historyPointMapper) {
        super(service, mapper);
        this.historyPointMapper = historyPointMapper;
    }

    @PostMapping("/registration")
    public ResponseParcelDto registrationParcel(@RequestBody @Valid RequestParcelDto requestParcelDto,
                                                @RequestParam(value = "index") @PositiveOrZero Integer index) {
        Parcel parcel = mapper.toEntity(requestParcelDto);
        return mapper.toResponse(service.registrationParcel(parcel, index));
    }

    @GetMapping("/arrive")
    public ResponseParcelDto arriveAtDepartment(@RequestParam(value = "identifierParcel") @PositiveOrZero Long identifierParcel,
                                                @RequestParam(value = "index") Integer indexDepartment) {

        return mapper.toResponse(service.arriveAtDepartment(identifierParcel, indexDepartment));
    }

    @GetMapping("/leave")
    public ResponseParcelDto leaveAtDepartment(@RequestParam(value = "identifierParcel") @PositiveOrZero Long identifierParcel) {
        return mapper.toResponse(service.leaveDepartment(identifierParcel));
    }

    @GetMapping("/delivery")
    public ResponseParcelDto deliveryToRecipient(@RequestParam(value = "identifierParcel") @PositiveOrZero Long identifierParcel) {
        return mapper.toResponse(service.deliveryToRecipient(identifierParcel));
    }

    @GetMapping("/history")
    public List<ResponseHistoryPointDto> getHistoryParcel(@RequestParam(value = "identifierParcel") @PositiveOrZero Long identifierParcel) {

        return service.getHistoryParcel(identifierParcel).stream()
                .map(historyPointMapper::toResponse).collect(Collectors.toList());
    }
}
