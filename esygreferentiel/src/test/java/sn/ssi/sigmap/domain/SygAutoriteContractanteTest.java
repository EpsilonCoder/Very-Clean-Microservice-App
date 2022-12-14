package sn.ssi.sigmap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class SygAutoriteContractanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SygAutoriteContractante.class);
        SygAutoriteContractante sygAutoriteContractante1 = new SygAutoriteContractante();
        sygAutoriteContractante1.setId(1L);
        SygAutoriteContractante sygAutoriteContractante2 = new SygAutoriteContractante();
        sygAutoriteContractante2.setId(sygAutoriteContractante1.getId());
        assertThat(sygAutoriteContractante1).isEqualTo(sygAutoriteContractante2);
        sygAutoriteContractante2.setId(2L);
        assertThat(sygAutoriteContractante1).isNotEqualTo(sygAutoriteContractante2);
        sygAutoriteContractante1.setId(null);
        assertThat(sygAutoriteContractante1).isNotEqualTo(sygAutoriteContractante2);
    }
}
