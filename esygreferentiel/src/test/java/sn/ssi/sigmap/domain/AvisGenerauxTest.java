package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class AvisGenerauxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisGeneraux.class);
        AvisGeneraux avisGeneraux1 = new AvisGeneraux();
        avisGeneraux1.setId(1L);
        AvisGeneraux avisGeneraux2 = new AvisGeneraux();
        avisGeneraux2.setId(avisGeneraux1.getId());
        assertThat(avisGeneraux1).isEqualTo(avisGeneraux2);
        avisGeneraux2.setId(2L);
        assertThat(avisGeneraux1).isNotEqualTo(avisGeneraux2);
        avisGeneraux1.setId(null);
        assertThat(avisGeneraux1).isNotEqualTo(avisGeneraux2);
    }
}
