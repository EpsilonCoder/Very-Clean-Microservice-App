package sn.ssi.sigmap.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygTypeMarcheDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygTypeMarcheDTO.class);
        SygTypeMarcheDTO sygTypeMarcheDTO1 = new SygTypeMarcheDTO();
        sygTypeMarcheDTO1.setId(1L);
        SygTypeMarcheDTO sygTypeMarcheDTO2 = new SygTypeMarcheDTO();
        assertThat(sygTypeMarcheDTO1).isNotEqualTo(sygTypeMarcheDTO2);
        sygTypeMarcheDTO2.setId(sygTypeMarcheDTO1.getId());
        assertThat(sygTypeMarcheDTO1).isEqualTo(sygTypeMarcheDTO2);
        sygTypeMarcheDTO2.setId(2L);
        assertThat(sygTypeMarcheDTO1).isNotEqualTo(sygTypeMarcheDTO2);
        sygTypeMarcheDTO1.setId(null);
        assertThat(sygTypeMarcheDTO1).isNotEqualTo(sygTypeMarcheDTO2);
    }
}
