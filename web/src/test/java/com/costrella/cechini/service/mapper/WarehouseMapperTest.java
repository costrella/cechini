package com.costrella.cechini.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseMapperTest {

    private WarehouseMapper warehouseMapper;

    @BeforeEach
    public void setUp() {
        warehouseMapper = new WarehouseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(warehouseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(warehouseMapper.fromId(null)).isNull();
    }
}
