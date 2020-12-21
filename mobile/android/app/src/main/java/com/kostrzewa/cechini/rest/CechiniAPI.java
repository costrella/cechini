package com.kostrzewa.cechini.rest;

import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.model.ProductDTO;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;
import com.kostrzewa.cechini.model.ReportsDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.model.StoreGroupDTO;
import com.kostrzewa.cechini.model.WarehouseDTO;
import com.kostrzewa.cechini.model.WorkerDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CechiniAPI {

    String ENDPOINT = "http://10.0.2.2:8080/api/";

    @GET("stores/{storeId}")
    Call<StoreDTO> getStore(@Path("storeId") String storeId);

    @GET("stores/worker/{id}/mystores")
    Call<List<StoreDTO>> getMyStores(@Path("id") Long id);

    @POST("stores")
    Call<StoreDTO> addStore(@Body StoreDTO storeDTO);

    @GET("store-groups/all")
    Call<List<StoreGroupDTO>> getAllStoreGroups();

    @GET("products/all")
    Call<List<ProductDTO>> getAllProducts();

    @GET("warehouses/all")
    Call<List<WarehouseDTO>> getAllWarehouses();

    @GET("reports/worker/{id}/all")
    Call<List<ReportDTO>> getMyReports(@Path("id") Long id);

    @POST("orders")
    Call<OrderDTO> sendOrder(@Body OrderDTO orderDTO);

    @POST("reports")
    Call<ReportDTO> sendReport(@Body ReportDTOWithPhotos reportDTO);

    @POST("reports/many")
    Call<Void> sendManyReports(@Body ReportsDTO reportsDTO);

    @POST("workers/login")
    Call<WorkerDTO> login(@Body WorkerDTO workerDTO);

    @GET("orders/worker/{id}/all")
    Call<List<OrderDTO>> getMyOrders(@Path("id") Long workerId);
}