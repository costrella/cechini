package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.*;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrderMapper.class, WorkerMapper.class, StoreMapper.class})
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {

    default Report toEntity(ReportDTO reportDTO) {
        Report report = new Report();
        report.setId(reportDTO.getId());
        report.setReportDate(Instant.now());
        report.setWorker(new Worker().id(reportDTO.getWorkerId()));
        report.setStore(new Store().id(reportDTO.getStoreId()));
        report.setDesc(reportDTO.getDesc());
        report.setManagerNote(reportDTO.getManagerNote());
        if (reportDTO.getOrderDTO() != null) {
            report.setOrder(OrderMapperCustom.toEntity(reportDTO.getOrderDTO()));
        }
        return report;
    }

    default Report toEntityWithPhotos(ReportDTOWithPhotos reportDTO) {
        Report report = toEntity(reportDTO);
        Set<Photo> photos = new HashSet<>();
        if (!reportDTO.getPhotosList().isEmpty()) {
            for (PhotoDTO photoDTO : reportDTO.getPhotosList()) {
                if (photoDTO.getPhotoFileDTO() != null && photoDTO.getPhotoFileDTO().getValue() != null) {
                    Photo photo = new Photo();
                    PhotoFile photoFile = new PhotoFile();
                    photoFile.setValue(photoDTO.getPhotoFileDTO().getValue());
                    photoFile.setValueContentType("image/png");
                    photo.setFile(photoFile);
                    photo.setReport(report);
                    photos.add(photo);
                }
            }
        }
        if (!photos.isEmpty()) {
            report.setPhotos(photos);
        }
        return report;
    }

    default Report fromId(Long id) {
        if (id == null) {
            return null;
        }
        Report report = new Report();
        report.setId(id);
        return report;
    }

    default ReportDTO toDto(Report report) {
        if (report == null) return null;
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setDesc(report.getDesc());
        reportDTO.setManagerNote(report.getManagerNote());
        reportDTO.setReportDate(report.getReportDate()); //todo
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

    default ReportDTO toDtoWithOrders(Report report, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        ReportDTO reportDTO = toDto(report);
        if (report.getOrder() != null) {
            reportDTO.setOrderDTO(orderMapper.toDtoCustom(report.getOrder(), orderItemMapper));
        }
        return reportDTO;
    }

    default ReportDTO toDtoWithPhotos(Report report, OrderMapper orderMapper, OrderItemMapper orderItemMapper, PhotoFileMapper photoFileMapper) {
        ReportDTO reportDTO = toDtoWithOrders(report, orderMapper, orderItemMapper);
        List<PhotoFile> photoFiles = new ArrayList<>();
        report.getPhotos().forEach(photo -> {
            photoFiles.add(photo.getFile());
        });
        List<PhotoFileDTO> photoFileDTOS = new ArrayList<>();
        photoFiles.forEach(photoFile -> {
            photoFileDTOS.add(photoFileMapper.toDto(photoFile));
        });
        reportDTO.setPhotos(photoFileDTOS);
        return reportDTO;
    }

    default ReportDTOSimple toDtoSimple(Report report) {
        if (report == null) return null;
        ReportDTOSimple reportDTO = new ReportDTOSimple();
        reportDTO.setId(report.getId());
        reportDTO.setDesc(report.getDesc());
        reportDTO.setManagerNote(report.getManagerNote());
//        reportDTO.setReportDate(report.getReportDate());
        if (report.getStore() != null) {
            reportDTO.setStoreName(report.getStore().getName());
        }
//        if (report.getOrder() != null) {
//            reportDTO.setOrderId(report.getOrder().getId());
//        }
        reportDTO.setPhotosCount(report.getPhotos().size());
        return reportDTO;
    }
}
