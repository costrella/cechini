package com.kostrzewa.cechini.data.events;

import com.kostrzewa.cechini.model.WorkerDTO;

public class LoginSuccess {

    private WorkerDTO workerDTO;

    public LoginSuccess(WorkerDTO workerDTO) {
        this.workerDTO = workerDTO;
    }

    public WorkerDTO getWorkerDTO() {
        return workerDTO;
    }

    public void setWorkerDTO(WorkerDTO workerDTO) {
        this.workerDTO = workerDTO;
    }
}
