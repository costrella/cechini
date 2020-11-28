package com.kostrzewa.cechini.data;

import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.WarehouseDTO;

import java.util.List;

public interface ReportDataManager {

//    List<WarehouseDTO> getAllWarehouses();

    void send(ReportDTO reportDTO);
}
