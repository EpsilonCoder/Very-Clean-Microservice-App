package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygTypeServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygTypeService.class);
        SygTypeService sygTypeService1 = new SygTypeService();
        sygTypeService1.setId(1L);
        SygTypeService sygTypeService2 = new SygTypeService();
        sygTypeService2.setId(sygTypeService1.getId());
        assertThat(sygTypeService1).isEqualTo(sygTypeService2);
        sygTypeService2.setId(2L);
        assertThat(sygTypeService1).isNotEqualTo(sygTypeService2);
        sygTypeService1.setId(null);
        assertThat(sygTypeService1).isNotEqualTo(sygTypeService2);
    }
}
