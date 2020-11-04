package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.ReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrderMapper.class, WorkerMapper.class, StoreMapper.class})
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {

//    @Mapping(source = "order.id", target = "orderId")
//    @Mapping(source = "worker.id", target = "workerId")
//    @Mapping(source = "worker.surname", target = "workerSurname")
//    @Mapping(source = "store.id", target = "storeId")
//    @Mapping(source = "store.name", target = "storeName")
////    @Mapping(source = "photos.size", target = "photosCount")
//    ReportDTO toDto(Report report);

    @Mapping(source = "orderId", target = "order")
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "removePhotos", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "removeNote", ignore = true)
    @Mapping(source = "workerId", target = "worker")
    @Mapping(source = "storeId", target = "store")
    Report toEntity(ReportDTO reportDTO);

    default Report fromId(Long id) {
        if (id == null) {
            return null;
        }
        Report report = new Report();
        report.setId(id);
        return report;
    }

    //    @Mapping(source = "order.id", target = "orderId")
//    @Mapping(source = "worker.id", target = "reportDTO.workerId")
//    @Mapping(source = "worker.surname", target = "reportDTO.workerSurname")
//    @Mapping(source = "store.id", target = "storeId")
//    @Mapping(source = "store.name", target = "storeName")
    default ReportDTO toDto(Report report) {
        if (report == null) return null;
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setDesc(report.getDesc());
        reportDTO.setManagerNote(report.getManagerNote());
        reportDTO.setReportDate(report.getReportDate());
        if (report.getWorker() != null) {
            reportDTO.setWorkerId(report.getWorker().getId());
            reportDTO.setWorkerSurname(report.getWorker().getSurname());
        }
        if (report.getStore() != null) {
            reportDTO.setStoreId(report.getStore().getId());
            reportDTO.setStoreName(report.getStore().getName());
        }
        if (report.getOrder() != null) {
            reportDTO.setOrderId(report.getOrder().getId());
        }
        reportDTO.setPhotosCount(report.getPhotos().size());
        return reportDTO;
    }
}
