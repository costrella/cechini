package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.NoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {StoreMapper.class, WorkerMapper.class, ManagerMapper.class, ReportMapper.class})
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "workerNote.id", target = "workerNoteId")
    @Mapping(source = "managerNote.id", target = "managerNoteId", ignore = true)
    @Mapping(source = "report.id", target = "reportId")
    @Mapping(source = "managerNote", target = "managerNote", ignore = true)
    NoteDTO toDto(Note note);

    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "workerNoteId", target = "workerNote")
    @Mapping(source = "managerNoteId", target = "managerNote", ignore = true)
    @Mapping(source = "reportId", target = "report")
    Note toEntity(NoteDTO noteDTO);

    default Note fromId(Long id) {
        if (id == null) {
            return null;
        }
        Note note = new Note();
        note.setId(id);
        return note;
    }
}
