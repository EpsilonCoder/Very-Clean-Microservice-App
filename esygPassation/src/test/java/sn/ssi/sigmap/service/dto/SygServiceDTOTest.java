package sn.ssi.sigmap.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygServiceDTO.class);
        SygServiceDTO sygServiceDTO1 = new SygServiceDTO();
        sygServiceDTO1.setId(1L);
        SygServiceDTO sygServiceDTO2 = new SygServiceDTO();
        assertThat(sygServiceDTO1).isNotEqualTo(sygServiceDTO2);
        sygServiceDTO2.setId(sygServiceDTO1.getId());
        assertThat(sygServiceDTO1).isEqualTo(sygServiceDTO2);
        sygServiceDTO2.setId(2L);
        assertThat(sygServiceDTO1).isNotEqualTo(sygServiceDTO2);
        sygServiceDTO1.setId(null);
        assertThat(sygServiceDTO1).isNotEqualTo(sygServiceDTO2);
    }
}
