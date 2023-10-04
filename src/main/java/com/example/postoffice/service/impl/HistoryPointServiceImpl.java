package com.example.postoffice.service.impl;

import com.example.postoffice.entity.HistoryPoint;
import com.example.postoffice.repository.HistoryPointRepository;
import com.example.postoffice.service.BaseService;
import com.example.postoffice.service.HistoryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("HistoryPointServiceImpl")
public class HistoryPointServiceImpl
        extends BaseService<HistoryPoint, HistoryPointRepository>
        implements HistoryPointService {

    @Autowired
    public HistoryPointServiceImpl(HistoryPointRepository repository) {
        super(repository);
    }
}
