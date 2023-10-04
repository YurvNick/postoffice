package com.example.postoffice.controller.rest.v1;

import com.example.postoffice.controller.rest.BaseController;
import com.example.postoffice.controller.rest.CommonController;
import com.example.postoffice.dto.historypoint.RequestHistoryPointDto;
import com.example.postoffice.dto.historypoint.ResponseHistoryPointDto;
import com.example.postoffice.entity.HistoryPoint;
import com.example.postoffice.mapper.historyPoint.HistoryPointMapper;
import com.example.postoffice.service.HistoryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/historyPoint")
public class HistoryPointControllerV1
        extends
        BaseController<HistoryPoint,
                RequestHistoryPointDto,
                ResponseHistoryPointDto,
                HistoryPointService,
                HistoryPointMapper>
        implements
        CommonController<HistoryPoint,
                RequestHistoryPointDto,
                ResponseHistoryPointDto> {

    @Autowired
    public HistoryPointControllerV1(HistoryPointService service,
                                    HistoryPointMapper mapper) {
        super(service, mapper);
    }
}
