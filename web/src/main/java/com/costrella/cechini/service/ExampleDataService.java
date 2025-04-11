package com.costrella.cechini.service;

import com.costrella.cechini.domain.*;
import com.costrella.cechini.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class ExampleDataService {

    private final Logger log = LoggerFactory.getLogger(ExampleDataService.class);

    private final UserRepository userRepository;

    private final TenantRepository tenantRepository;

    private final ProductRepository productRepository;

    private final WarehouseRepository warehouseRepository;

    private final StoreRepository storeRepository;

    private final WorkerRepository workerRepository;

    private final ReportRepository reportRepository;

    private final PasswordEncoder passwordEncoder;

    public ExampleDataService(UserRepository userRepository, TenantRepository tenantRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository, StoreRepository storeRepository, WorkerRepository workerRepository, ReportRepository reportRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.storeRepository = storeRepository;
        this.workerRepository = workerRepository;
        this.reportRepository = reportRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Product createProductExampleOne(Tenant tenant) {
        Product product = new Product();
        product.setName("Product Example " + tenant.getId());
        product.setCapacity(10d);
        product.setTenant(tenant);
        return productRepository.save(product);
    }

    private Product createProductExampleTwo(Tenant tenant) {
        Product product = new Product();
        product.setName("Product Example " + tenant.getId());
        product.setCapacity(10d);
        product.setTenant(tenant);
        return productRepository.save(product);
    }


    private Warehouse createWarehouseOne(Tenant tenant) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("Warehouse Example " + tenant.getId());
        warehouse.setTenant(tenant);
        return warehouseRepository.save(warehouse);
    }

    private Store createStoreOne(Tenant tenant, Worker worker) {
        Store store = new Store();
        store.setName("Shop Example " + tenant.getId());
        store.setAddress("Cracov, ul. Dietla 1");
        store.setTenant(tenant);
        store.setWorker(worker);
        store.setDesc("Description of shop one");
        return storeRepository.save(store);
    }

    public void createReportWithWorkerAndOrder(Tenant tenant) {
        Report report = new Report();
        report.setDesc("Report Example One");
        report.setTenant(tenant);
        Worker worker = createWorker(tenant);
        report.setWorker(worker);
        report.setReportDate(Instant.now());
        report.setStore(createStoreOne(tenant, worker));
        report.setOrder(createOrder(tenant, createWarehouseOne(tenant)));
        reportRepository.save(report);
    }

    private Order createOrder(Tenant tenant, Warehouse warehouse) {
        Order order = new Order();
        order.setTenant(tenant);
        order.setOrderDate(Instant.now());
        order.setDeliveryDate(Instant.now());
        order.setWarehouse(warehouse);
        OrderItem orderItem = new OrderItem();
        orderItem.setTenant(tenant);
        orderItem.setProduct(createProductExampleOne(tenant));
        orderItem.setOrder(order);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setTenant(tenant);
        orderItem2.setProduct(createProductExampleOne(tenant));
        orderItem2.setOrder(order);
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);
        orderItems.add(orderItem2);
        order.setOrderItems(orderItems);
        return order;
    }

    private Worker createWorker(Tenant tenant) {
        Worker worker = new Worker();
        worker.setName("Adam");
        worker.setSurname("Surname Example");
        worker.setPhone("123456789");
        worker.setActive(true);
        worker.setLogin("adam" + tenant.getId());
        worker.setPassword("1234");
        worker.setTenant(tenant);
        User user = new User();
        user.setActivated(true);
        user.setEmail("adam" + tenant.getId() + "@test.com");
        user.setLogin(worker.getLogin());
        user.setPassword(passwordEncoder.encode(worker.getPassword()));
        user.setTenant(tenant);
        user = userRepository.save(user);
        worker.setUser(user);
        return workerRepository.save(worker);
    }


}
