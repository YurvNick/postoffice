package com.example.postoffice.mapper.parcel;

import com.example.postoffice.dto.parsel.RequestParcelDto;
import com.example.postoffice.dto.parsel.ResponseParcelDto;
import com.example.postoffice.entity.Parcel;
import com.example.postoffice.mapper.CommonMapper;
import com.example.postoffice.mapper.historyPoint.HistoryPointMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParcelMapper extends CommonMapper<Parcel, RequestParcelDto, ResponseParcelDto> {
}
