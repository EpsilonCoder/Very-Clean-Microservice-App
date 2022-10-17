package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygTypeMarcheTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygTypeMarche.class);
        SygTypeMarche sygTypeMarche1 = new SygTypeMarche();
        sygTypeMarche1.setId(1L);
        SygTypeMarche sygTypeMarche2 = new SygTypeMarche();
        sygTypeMarche2.setId(sygTypeMarche1.getId());
        assertThat(sygTypeMarche1).isEqualTo(sygTypeMarche2);
        sygTypeMarche2.setId(2L);
        assertThat(sygTypeMarche1).isNotEqualTo(sygTypeMarche2);
        sygTypeMarche1.setId(null);
        assertThat(sygTypeMarche1).isNotEqualTo(sygTypeMarche2);
    }
}
