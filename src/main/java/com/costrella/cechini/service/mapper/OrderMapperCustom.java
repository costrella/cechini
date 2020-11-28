package com.costrella.cechini.service.mapper;

import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.OrderDTO;

import java.time.Instant;

public class OrderMapperCustom {

    public static Order toEntity(OrderDTO orderDTO) {
        //todo
        orderDTO.setStatusId(1L); //todo
        orderDTO.setWorkerId(1L); //todo
        orderDTO.setOrderDate(Instant.now()); //todo
        orderDTO.setDeliveryDate(Instant.now()); //todo
        //todo ////////////////////////


        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(new Status().id(orderDTO.getStatusId()));
        order.setWarehouse(new Warehouse().id(orderDTO.getWarehouseId()));
        orderDTO.getOrderItems().stream().forEach(orderItemDTO -> {
            OrderItem orderItem = new OrderItem().artCount(orderItemDTO.getArtCount()).packCount(orderItemDTO.getPackCount());
            orderItem.setProduct(new Product().id(orderItemDTO.getProductId()));
            order.addOrderItem(orderItem);
        });
//        throw new RuntimeException("test crash");
        return order;

    }
}
