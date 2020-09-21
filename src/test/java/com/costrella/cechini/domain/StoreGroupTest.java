package com.costrella.cechini.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.costrella.cechini.web.rest.TestUtil;

public class StoreGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreGroup.class);
        StoreGroup storeGroup1 = new StoreGroup();
        storeGroup1.setId(1L);
        StoreGroup storeGroup2 = new StoreGroup();
        storeGroup2.setId(storeGroup1.getId());
        assertThat(storeGroup1).isEqualTo(storeGroup2);
        storeGroup2.setId(2L);
        assertThat(storeGroup1).isNotEqualTo(storeGroup2);
        storeGroup1.setId(null);
        assertThat(storeGroup1).isNotEqualTo(storeGroup2);
    }
}
