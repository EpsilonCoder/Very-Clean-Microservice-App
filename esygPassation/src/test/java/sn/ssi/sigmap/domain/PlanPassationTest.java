package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class PlanPassationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanPassation.class);
        PlanPassation planPassation1 = new PlanPassation();
        planPassation1.setId(1L);
        PlanPassation planPassation2 = new PlanPassation();
        planPassation2.setId(planPassation1.getId());
        assertThat(planPassation1).isEqualTo(planPassation2);
        planPassation2.setId(2L);
        assertThat(planPassation1).isNotEqualTo(planPassation2);
        planPassation1.setId(null);
        assertThat(planPassation1).isNotEqualTo(planPassation2);
    }
}
