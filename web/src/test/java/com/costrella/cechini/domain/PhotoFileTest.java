package com.costrella.cechini.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.costrella.cechini.web.rest.TestUtil;

public class PhotoFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotoFile.class);
        PhotoFile photoFile1 = new PhotoFile();
        photoFile1.setId(1L);
        PhotoFile photoFile2 = new PhotoFile();
        photoFile2.setId(photoFile1.getId());
        assertThat(photoFile1).isEqualTo(photoFile2);
        photoFile2.setId(2L);
        assertThat(photoFile1).isNotEqualTo(photoFile2);
        photoFile1.setId(null);
        assertThat(photoFile1).isNotEqualTo(photoFile2);
    }
}
