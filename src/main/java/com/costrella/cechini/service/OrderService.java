package com.costrella.cechini.service;

import com.costrella.cechini.domain.Order;
import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.OrderRepository;
import com.costrella.cechini.service.dto.OrderDTO;
import com.costrella.cechini.service.mapper.OrderItemMapper;
import com.costrella.cechini.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable)
            .map(orderMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        log.debug("Request to get all Orders List");
        return orderRepository.findAll().stream()
            .map(orderMapper::toDto).collect(Collectors.toList());
    }

    /**
     *  Get all the orders where Report is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> findAllWhereReportIsNull() {
        log.debug("Request to get all orders where Report is null");
        return StreamSupport
            .stream(orderRepository.findAll().spliterator(), false)
            .filter(order -> order.getReport() == null)
            .map(orderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id)
            .map(v -> orderMapper.toDtoCustom(v, orderItemMapper));
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> findAllByWorkerId(Pageable pageable, Long id) {
        Order order = new Order();
        order.setReport(new Report().worker(new Worker().id(id)));
        return orderRepository.findAll(Example.of(order), pageable)
            .map(v -> orderMapper.toDtoCustom(v, orderItemMapper));
    }
}
