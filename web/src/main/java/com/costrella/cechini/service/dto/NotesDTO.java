package com.costrella.cechini.service.dto;

import com.costrella.cechini.domain.enumeration.NoteType;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Note} entity.
 */
public class NotesDTO implements Serializable {

    private List<NoteDTO> noteDTOS;

    public List<NoteDTO> getNoteDTOS() {
        return noteDTOS;
    }

    public void setNoteDTOS(List<NoteDTO> noteDTOS) {
        this.noteDTOS = noteDTOS;
    }
}
