package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.ReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {WorkerMapper.class, StoreMapper.class, OrderMapper.class})
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {

    @Mapping(source = "worker.id", target = "workerId")
    @Mapping(source = "worker.surname", target = "workerSurname")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "store.name", target = "storeName")
    @Mapping(source = "order.id", target = "orderId")
    ReportDTO toDto(Report report);

    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "removePhotos", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "removeNote", ignore = true)
    @Mapping(source = "workerId", target = "worker")
    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "orderId", target = "order")
    Report toEntity(ReportDTO reportDTO);

    default Report fromId(Long id) {
        if (id == null) {
            return null;
        }
        Report report = new Report();
        report.setId(id);
        return report;
    }
}
