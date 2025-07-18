package com.costrella.cechini.service;

import com.costrella.cechini.domain.*;
import com.costrella.cechini.domain.enumeration.OrderFileType;
import com.costrella.cechini.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static com.costrella.cechini.domain.enumeration.NoteType.BY_MANGER;
import static com.costrella.cechini.domain.enumeration.NoteType.BY_WORKER;

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

    private final NoteRepository noteRepository;

    private final PasswordEncoder passwordEncoder;

    public ExampleDataService(UserRepository userRepository, TenantRepository tenantRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository, StoreRepository storeRepository, WorkerRepository workerRepository, ReportRepository reportRepository, NoteRepository noteRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.storeRepository = storeRepository;
        this.workerRepository = workerRepository;
        this.reportRepository = reportRepository;
        this.noteRepository = noteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Product createProductExampleOne(Tenant tenant) {
        Product product = new Product();
        product.setName("Product Example One");
        product.setCapacity(10d);
        product.setPackCountPalette(10);
        product.setArtCountPalette(10);
        product.setTenant(tenant);
        return productRepository.save(product);
    }

    private Product createProductExampleTwo(Tenant tenant) {
        Product product = new Product();
        product.setName("Product Example Two");
        product.setCapacity(15d);
        product.setPackCountPalette(15);
        product.setArtCountPalette(15);
        product.setTenant(tenant);
        return productRepository.save(product);
    }


    private Warehouse createWarehouseOne(Tenant tenant, String email) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("Warehouse Example");
        warehouse.setMail(email);
        warehouse.setOrderFileType(OrderFileType.CSV);
        warehouse.setTenant(tenant);
        return warehouseRepository.save(warehouse);
    }

    private Store createStoreOne(Tenant tenant, Worker worker) {
        Store store = new Store();
        store.setName("Shop Example");
        store.setAddress("Krakow, ul. Dietla 1");
        store.setTenant(tenant);
        store.setWorker(worker);
        store.setDesc("Description of shop one");
        return storeRepository.save(store);
    }

    public void createReportWithWorkerAndOrder(Tenant tenant, String email) {
        Report report = new Report();
        report.setDesc("Report Example One");
        report.setTenant(tenant);
        Worker worker = createWorker(tenant);
        report.setWorker(worker);
        report.setReportDate(Instant.now());
        report.setStore(createStoreOne(tenant, worker));
        report.setOrder(createOrder(tenant, createWarehouseOne(tenant, email)));
        report.setNotes(createNotes(tenant, report, worker));
        report.setReadByManager(false);
        report.setReadByWorker(true);

        report.setPhotos(createPhotos(tenant, report));
        reportRepository.save(report);
    }

    private Set<Photo> createPhotos(Tenant tenant, Report report){
       Photo photo = new Photo();
       photo.setTenant(tenant);
       photo.setReport(report);

       PhotoFile photoFile = new PhotoFile();
       photoFile.setPhoto(photo);
       photoFile.setTenant(tenant);
        try {
            photoFile.setValue(getImageAsByteArray("test.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        photoFile.setValueContentType("image/jpeg");
        photo.setFile(photoFile);

        Set<Photo> photos = new HashSet<>();
        photos.add(photo);
        return photos;
    }


    public byte[] getImageAsByteArray(String imageName) throws IOException {
        ClassPathResource imgFile = new ClassPathResource("images/" + imageName);
        return StreamUtils.copyToByteArray(imgFile.getInputStream());
    }


    private Set<Note> createNotes(Tenant tenant, Report report, Worker worker){
        Note note = new Note();
        note.setTenant(tenant);
        note.setReport(report);
        note.setDate(Instant.now());
        note.setNoteType(BY_MANGER);
        note.setValue("Example comment from Manager");
        note = noteRepository.save(note);

        Note note2 = new Note();
        note2.setTenant(tenant);
        note2.setReport(report);
        note2.setDate(Instant.now());
        note2.setNoteType(BY_WORKER);
        note2.setValue("Example comment from Worker");
        note2 = noteRepository.save(note2);

        Set<Note> notes = new HashSet<>();
        notes.add(note);
        notes.add(note2);
        return notes;
    }

    private Order createOrder(Tenant tenant, Warehouse warehouse) {
        Order order = new Order();
        order.setNumber("123456789");
        order.setTenant(tenant);
        order.setOrderDate(Instant.now());
        order.setDeliveryDate(Instant.now());
        order.setWarehouse(warehouse);
        OrderItem orderItem = new OrderItem();
        orderItem.setTenant(tenant);
        orderItem.setProduct(createProductExampleOne(tenant));
        orderItem.setOrder(order);
        orderItem.setPackCount(15);
        orderItem.setArtCount(10);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setTenant(tenant);
        orderItem2.setProduct(createProductExampleTwo(tenant));
        orderItem2.setPackCount(10);
        orderItem2.setArtCount(15);
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
        worker.setSurname("Kowalski");
        worker.setPhone("123456789");
        worker.setActive(true);
        worker.setHiredDate(Instant.now());
        worker.setDesc("Example Worker");
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
