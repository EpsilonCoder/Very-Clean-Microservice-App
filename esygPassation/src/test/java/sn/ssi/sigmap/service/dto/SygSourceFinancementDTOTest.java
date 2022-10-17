package sn.ssi.sigmap.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygSourceFinancementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygSourceFinancementDTO.class);
        SygSourceFinancementDTO sygSourceFinancementDTO1 = new SygSourceFinancementDTO();
        sygSourceFinancementDTO1.setId(1L);
        SygSourceFinancementDTO sygSourceFinancementDTO2 = new SygSourceFinancementDTO();
        assertThat(sygSourceFinancementDTO1).isNotEqualTo(sygSourceFinancementDTO2);
        sygSourceFinancementDTO2.setId(sygSourceFinancementDTO1.getId());
        assertThat(sygSourceFinancementDTO1).isEqualTo(sygSourceFinancementDTO2);
        sygSourceFinancementDTO2.setId(2L);
        assertThat(sygSourceFinancementDTO1).isNotEqualTo(sygSourceFinancementDTO2);
        sygSourceFinancementDTO1.setId(null);
        assertThat(sygSourceFinancementDTO1).isNotEqualTo(sygSourceFinancementDTO2);
    }
}
