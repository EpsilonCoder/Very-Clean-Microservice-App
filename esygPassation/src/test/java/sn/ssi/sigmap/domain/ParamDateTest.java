package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class ParamDateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamDate.class);
        ParamDate paramDate1 = new ParamDate();
        paramDate1.setId(1L);
        ParamDate paramDate2 = new ParamDate();
        paramDate2.setId(paramDate1.getId());
        assertThat(paramDate1).isEqualTo(paramDate2);
        paramDate2.setId(2L);
        assertThat(paramDate1).isNotEqualTo(paramDate2);
        paramDate1.setId(null);
        assertThat(paramDate1).isNotEqualTo(paramDate2);
    }
}
