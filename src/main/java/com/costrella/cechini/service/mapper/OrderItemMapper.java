package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
import com.costrella.cechini.service.dto.OrderItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, OrderMapper.class})
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.capacity", target = "productCapacity")
    @Mapping(source = "product.eanPack", target = "productEanPack")
    @Mapping(source = "order.id", target = "orderId")
    OrderItemDTO toDto(OrderItem orderItem);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "orderId", target = "order")
    OrderItem toEntity(OrderItemDTO orderItemDTO);

    default OrderItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        return orderItem;
    }
}
