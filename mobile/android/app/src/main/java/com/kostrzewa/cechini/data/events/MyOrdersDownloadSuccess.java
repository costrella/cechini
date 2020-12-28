package com.kostrzewa.cechini.data.events;

import com.kostrzewa.cechini.model.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersDownloadSuccess {

    public MyOrdersDownloadSuccess(List<OrderDTO> list) {
        this.list = list;
    }

    private List<OrderDTO> list = new ArrayList<>();

    public List<OrderDTO> getList() {
        return list;
    }

    public void setList(List<OrderDTO> list) {
        this.list = list;
    }
}
