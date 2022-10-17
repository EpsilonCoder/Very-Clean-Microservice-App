package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class RealisationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Realisation.class);
        Realisation realisation1 = new Realisation();
        realisation1.setId(1L);
        Realisation realisation2 = new Realisation();
        realisation2.setId(realisation1.getId());
        assertThat(realisation1).isEqualTo(realisation2);
        realisation2.setId(2L);
        assertThat(realisation1).isNotEqualTo(realisation2);
        realisation1.setId(null);
        assertThat(realisation1).isNotEqualTo(realisation2);
    }
}
