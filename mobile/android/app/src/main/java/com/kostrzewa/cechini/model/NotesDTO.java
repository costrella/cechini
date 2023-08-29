package com.kostrzewa.cechini.model;

import java.io.Serializable;
import java.util.List;

public class NotesDTO implements Serializable {

    private List<NoteDTO> noteDTOS;

    public List<NoteDTO> getNoteDTOS() {
        return noteDTOS;
    }

    public void setNoteDTOS(List<NoteDTO> noteDTOS) {
        this.noteDTOS = noteDTOS;
    }
}
