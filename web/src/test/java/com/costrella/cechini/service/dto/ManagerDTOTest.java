package com.costrella.cechini.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.costrella.cechini.web.rest.TestUtil;

public class ManagerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManagerDTO.class);
        ManagerDTO managerDTO1 = new ManagerDTO();
        managerDTO1.setId(1L);
        ManagerDTO managerDTO2 = new ManagerDTO();
        assertThat(managerDTO1).isNotEqualTo(managerDTO2);
        managerDTO2.setId(managerDTO1.getId());
        assertThat(managerDTO1).isEqualTo(managerDTO2);
        managerDTO2.setId(2L);
        assertThat(managerDTO1).isNotEqualTo(managerDTO2);
        managerDTO1.setId(null);
        assertThat(managerDTO1).isNotEqualTo(managerDTO2);
    }
}
