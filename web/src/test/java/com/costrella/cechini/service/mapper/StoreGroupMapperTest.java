package com.costrella.cechini.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StoreGroupMapperTest {

    private StoreGroupMapper storeGroupMapper;

    @BeforeEach
    public void setUp() {
        storeGroupMapper = new StoreGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(storeGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(storeGroupMapper.fromId(null)).isNull();
    }
}
