package com.costrella.cechini.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkerMapperTest {

    private WorkerMapper workerMapper;

    @BeforeEach
    public void setUp() {
        workerMapper = new WorkerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(workerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workerMapper.fromId(null)).isNull();
    }
}
