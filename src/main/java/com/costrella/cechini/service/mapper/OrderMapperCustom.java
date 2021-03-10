package com.costrella.cechini.service.mapper;

import com.costrella.cechini.domain.Order;
import com.costrella.cechini.domain.OrderItem;
import com.costrella.cechini.domain.Product;
import com.costrella.cechini.domain.Warehouse;
import com.costrella.cechini.service.dto.OrderDTO;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class OrderMapperCustom {

    public static Order toEntity(OrderDTO orderDTO) {
        orderDTO.setOrderDate(Instant.now()); //todo
        orderDTO.setDeliveryDate(Instant.now()); //todo

        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setNumber(getOrderNumber());
//        order.setStatus(new Status().id(orderDTO.getStatusId()));
        order.setWarehouse(new Warehouse().id(orderDTO.getWarehouseId()).name(orderDTO.getWarehouseName()));
        orderDTO.getOrderItems().stream().forEach(orderItemDTO -> {
            OrderItem orderItem = new OrderItem().artCount(orderItemDTO.getArtCount()).packCount(orderItemDTO.getPackCount());
            orderItem.setProduct(new Product().id(orderItemDTO.getProductId())
                .name(orderItemDTO.getProductName())
                .capacity(orderItemDTO.getProductCapacity())
                .eanPack(orderItemDTO.getProductEanPack()));
            order.addOrderItem(orderItem);
        });
//        throw new RuntimeException("test crash");
        return order;

    }

    private static String getOrderNumber() {
        Random rnd = new Random();
        int n = 1000 + rnd.nextInt(9000);
        return new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + n;
    }
}
