package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class HierarchieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hierarchie.class);
        Hierarchie hierarchie1 = new Hierarchie();
        hierarchie1.setId(1L);
        Hierarchie hierarchie2 = new Hierarchie();
        hierarchie2.setId(hierarchie1.getId());
        assertThat(hierarchie1).isEqualTo(hierarchie2);
        hierarchie2.setId(2L);
        assertThat(hierarchie1).isNotEqualTo(hierarchie2);
        hierarchie1.setId(null);
        assertThat(hierarchie1).isNotEqualTo(hierarchie2);
    }
}
