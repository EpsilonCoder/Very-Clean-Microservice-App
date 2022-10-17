package sn.ssi.sigmap.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class HistoriqueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriqueDTO.class);
        HistoriqueDTO historiqueDTO1 = new HistoriqueDTO();
        historiqueDTO1.setId(1L);
        HistoriqueDTO historiqueDTO2 = new HistoriqueDTO();
        assertThat(historiqueDTO1).isNotEqualTo(historiqueDTO2);
        historiqueDTO2.setId(historiqueDTO1.getId());
        assertThat(historiqueDTO1).isEqualTo(historiqueDTO2);
        historiqueDTO2.setId(2L);
        assertThat(historiqueDTO1).isNotEqualTo(historiqueDTO2);
        historiqueDTO1.setId(null);
        assertThat(historiqueDTO1).isNotEqualTo(historiqueDTO2);
    }
}
