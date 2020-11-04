package com.costrella.cechini.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StatusMapperTest {

    private StatusMapper statusMapper;

    @BeforeEach
    public void setUp() {
        statusMapper = new StatusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(statusMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(statusMapper.fromId(null)).isNull();
    }
}
