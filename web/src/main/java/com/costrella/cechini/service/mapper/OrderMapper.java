package com.costrella.cechini.service.mapper;


import com.costrella.cechini.domain.*;
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
    @Mapping(source = "report.worker.id", target = "workerId")
    @Mapping(source = "report.worker.surname", target = "workerSurname")
    @Mapping(source = "report.store.id", target = "storeId")
    @Mapping(source = "report.store.name", target = "storeName")
    OrderDTO toDto(Order order);

//    @Mapping(target = "orderItems", ignore = false)
//    @Mapping(target = "removeOrderItem", ignore = true)
//    @Mapping(target = "report", ignore = true)
//    @Mapping(source = "warehouseId", target = "warehouse")
//    @Mapping(source = "statusId", target = "status")
//    Order toEntity(OrderDTO orderDTO);

    default Order toEntity(OrderDTO orderDTO) {
        return OrderMapperCustom.toEntity(orderDTO);
    }

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
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTenant(order.getTenant());
        orderDTO.setId(order.getId());
        orderDTO.setNumber(order.getNumber());
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
        if (order.getReport() != null) {
            orderDTO.setReportId(order.getReport().getId());
            if (order.getReport().getStore() != null) {
                orderDTO.setStoreId(order.getReport().getStore().getId());
                orderDTO.setStoreName(order.getReport().getStore().getName());
            }
            if (order.getReport().getWorker() != null) {
                orderDTO.setWorkerId(order.getReport().getWorker().getId());
                orderDTO.setWorkerSurname(order.getReport().getWorker().getSurname());
            }

        }
        orderDTO.setOrderItems(order.getOrderItems().stream().map(orderItemMapper::toDto).collect(Collectors.toList()));
        return orderDTO;
    }
}
