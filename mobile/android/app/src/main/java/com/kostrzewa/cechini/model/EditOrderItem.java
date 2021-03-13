package com.kostrzewa.cechini.model;

public class EditOrderItem {

    private OrderItemDTO orderItemDTO;

    public EditOrderItem(OrderItemDTO o) {
        this.orderItemDTO = o;
    }

    public OrderItemDTO getOrderItemDTO() {
        return orderItemDTO;
    }

    public void setOrderItemDTO(OrderItemDTO orderItemDTO) {
        this.orderItemDTO = orderItemDTO;
    }
}
