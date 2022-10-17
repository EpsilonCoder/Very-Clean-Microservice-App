package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygService.class);
        SygService sygService1 = new SygService();
        sygService1.setId(1L);
        SygService sygService2 = new SygService();
        sygService2.setId(sygService1.getId());
        assertThat(sygService1).isEqualTo(sygService2);
        sygService2.setId(2L);
        assertThat(sygService1).isNotEqualTo(sygService2);
        sygService1.setId(null);
        assertThat(sygService1).isNotEqualTo(sygService2);
    }
}
