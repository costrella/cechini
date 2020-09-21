package com.costrella.cechini.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.costrella.cechini.web.rest.TestUtil;

public class StoreGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreGroupDTO.class);
        StoreGroupDTO storeGroupDTO1 = new StoreGroupDTO();
        storeGroupDTO1.setId(1L);
        StoreGroupDTO storeGroupDTO2 = new StoreGroupDTO();
        assertThat(storeGroupDTO1).isNotEqualTo(storeGroupDTO2);
        storeGroupDTO2.setId(storeGroupDTO1.getId());
        assertThat(storeGroupDTO1).isEqualTo(storeGroupDTO2);
        storeGroupDTO2.setId(2L);
        assertThat(storeGroupDTO1).isNotEqualTo(storeGroupDTO2);
        storeGroupDTO1.setId(null);
        assertThat(storeGroupDTO1).isNotEqualTo(storeGroupDTO2);
    }
}
