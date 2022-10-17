package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygSourceFinancementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygSourceFinancement.class);
        SygSourceFinancement sygSourceFinancement1 = new SygSourceFinancement();
        sygSourceFinancement1.setId(1L);
        SygSourceFinancement sygSourceFinancement2 = new SygSourceFinancement();
        sygSourceFinancement2.setId(sygSourceFinancement1.getId());
        assertThat(sygSourceFinancement1).isEqualTo(sygSourceFinancement2);
        sygSourceFinancement2.setId(2L);
        assertThat(sygSourceFinancement1).isNotEqualTo(sygSourceFinancement2);
        sygSourceFinancement1.setId(null);
        assertThat(sygSourceFinancement1).isNotEqualTo(sygSourceFinancement2);
    }
}
