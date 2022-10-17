package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygTypeSourceFinancementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygTypeSourceFinancement.class);
        SygTypeSourceFinancement sygTypeSourceFinancement1 = new SygTypeSourceFinancement();
        sygTypeSourceFinancement1.setId(1L);
        SygTypeSourceFinancement sygTypeSourceFinancement2 = new SygTypeSourceFinancement();
        sygTypeSourceFinancement2.setId(sygTypeSourceFinancement1.getId());
        assertThat(sygTypeSourceFinancement1).isEqualTo(sygTypeSourceFinancement2);
        sygTypeSourceFinancement2.setId(2L);
        assertThat(sygTypeSourceFinancement1).isNotEqualTo(sygTypeSourceFinancement2);
        sygTypeSourceFinancement1.setId(null);
        assertThat(sygTypeSourceFinancement1).isNotEqualTo(sygTypeSourceFinancement2);
    }
}
