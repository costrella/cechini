package com.costrella.cechini.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotoFileMapperTest {

    private PhotoFileMapper photoFileMapper;

    @BeforeEach
    public void setUp() {
        photoFileMapper = new PhotoFileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(photoFileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(photoFileMapper.fromId(null)).isNull();
    }
}
