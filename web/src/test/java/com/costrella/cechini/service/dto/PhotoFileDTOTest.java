package com.costrella.cechini.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.costrella.cechini.web.rest.TestUtil;

public class PhotoFileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotoFileDTO.class);
        PhotoFileDTO photoFileDTO1 = new PhotoFileDTO();
        photoFileDTO1.setId(1L);
        PhotoFileDTO photoFileDTO2 = new PhotoFileDTO();
        assertThat(photoFileDTO1).isNotEqualTo(photoFileDTO2);
        photoFileDTO2.setId(photoFileDTO1.getId());
        assertThat(photoFileDTO1).isEqualTo(photoFileDTO2);
        photoFileDTO2.setId(2L);
        assertThat(photoFileDTO1).isNotEqualTo(photoFileDTO2);
        photoFileDTO1.setId(null);
        assertThat(photoFileDTO1).isNotEqualTo(photoFileDTO2);
    }
}
