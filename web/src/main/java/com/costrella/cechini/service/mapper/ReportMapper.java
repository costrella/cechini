package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.*;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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
        report.setTenant(reportDTO.getTenant());
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
        reportDTO.setReportDate(report.getReportDate());
        reportDTO.setTenant(report.getTenant());
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
        reportDTO.setReadByManager(report.getReadByManager());
        reportDTO.setReadByWorker(report.getReadByWorker());

        reportDTO.setNotes(report.getNotes().stream().map(n -> {
            NoteDTO dto = new NoteDTO();
            dto.setDate(n.getDate());
            dto.setValue(n.getValue());
            dto.setNoteType(n.getNoteType());
            return dto;
        }).sorted(Comparator.comparing(NoteDTO::getDate)).collect(Collectors.toList()));
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
            photoFiles.add(photo.getFile()); //todo mozna zmienic
        });
        List<PhotoFileDTO> photoFileDTOS = new ArrayList<>();
        photoFiles.forEach(photoFile -> {
            photoFileDTOS.add(photoFileMapper.toDto(photoFile));
        });
        reportDTO.setPhotos(photoFileDTOS);
        return reportDTO;
    }

    default ReportDTOSimple toDtoMobile(Report report) {
        if (report == null) return null;
        ReportDTOSimple reportDTO = new ReportDTOSimple();
        reportDTO.setId(report.getId());
        reportDTO.setDesc(report.getDesc());
        reportDTO.setReportDate(report.getReportDate());
        reportDTO.setReadByManager(report.getReadByManager());
        reportDTO.setReadByWorker(report.getReadByWorker());
        reportDTO.setNotesExist(!report.getNotes().isEmpty());
        if (report.getStore() != null) {
            reportDTO.setStoreName(report.getStore().getName());
        }
        if (report.getOrder() != null) {
            reportDTO.setOrderId(report.getOrder().getId());
        }
        return reportDTO;
    }

}
