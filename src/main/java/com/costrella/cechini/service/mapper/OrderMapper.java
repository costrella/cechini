package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.Order;
import com.costrella.cechini.service.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {WarehouseMapper.class, StatusMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "warehouse.name", target = "warehouseName")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    OrderDTO toDto(Order order);

    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "removeOrderItem", ignore = true)
    @Mapping(target = "report", ignore = true)
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "statusId", target = "status")
    Order toEntity(OrderDTO orderDTO);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }

    default OrderDTO toDtoCustom(Order order, OrderItemMapper orderItemMapper) {
        if (order == null) {
            return null;
        }
        OrderDTO orderDTO = new OrderDTO(); //todo
        orderDTO.setId(order.getId());
        orderDTO.setComment(order.getComment());
        orderDTO.setDeliveryDate(order.getDeliveryDate());
        orderDTO.setOrderDate(order.getOrderDate());
        if (order.getStatus() != null) {
            orderDTO.setStatusId(order.getStatus().getId());
            orderDTO.setStatusName(order.getStatus().getName());
        }
        if (order.getWarehouse() != null) {
            orderDTO.setWarehouseId(order.getWarehouse().getId());
            orderDTO.setWarehouseName(order.getWarehouse().getName());
        }
        orderDTO.setOrderItems(order.getOrderItems().stream().map(orderItemMapper::toDto).collect(Collectors.toList()));
        return orderDTO;
    }
}
