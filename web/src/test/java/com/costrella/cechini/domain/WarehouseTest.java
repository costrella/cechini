package com.costrella.cechini.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.costrella.cechini.web.rest.TestUtil;

public class WarehouseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Warehouse.class);
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setId(1L);
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setId(warehouse1.getId());
        assertThat(warehouse1).isEqualTo(warehouse2);
        warehouse2.setId(2L);
        assertThat(warehouse1).isNotEqualTo(warehouse2);
        warehouse1.setId(null);
        assertThat(warehouse1).isNotEqualTo(warehouse2);
    }
}
