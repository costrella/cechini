package com.kostrzewa.cechini.data;

import com.kostrzewa.cechini.model.WorkerDTO;

public interface WorkerDataManager {

    void loginAsync(WorkerDTO workerDTO);

    WorkerDTO getWorker();

    void setWorker(WorkerDTO workerDTO);

}