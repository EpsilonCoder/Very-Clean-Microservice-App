package sn.ssi.sigmap.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygTypeSourceFinancementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygTypeSourceFinancementDTO.class);
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO1 = new SygTypeSourceFinancementDTO();
        sygTypeSourceFinancementDTO1.setId(1L);
        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO2 = new SygTypeSourceFinancementDTO();
        assertThat(sygTypeSourceFinancementDTO1).isNotEqualTo(sygTypeSourceFinancementDTO2);
        sygTypeSourceFinancementDTO2.setId(sygTypeSourceFinancementDTO1.getId());
        assertThat(sygTypeSourceFinancementDTO1).isEqualTo(sygTypeSourceFinancementDTO2);
        sygTypeSourceFinancementDTO2.setId(2L);
        assertThat(sygTypeSourceFinancementDTO1).isNotEqualTo(sygTypeSourceFinancementDTO2);
        sygTypeSourceFinancementDTO1.setId(null);
        assertThat(sygTypeSourceFinancementDTO1).isNotEqualTo(sygTypeSourceFinancementDTO2);
    }
}
