package sn.ssi.sigmap.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.ssi.sigmap.web.rest.TestUtil;

class ParamDateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamDateDTO.class);
        ParamDateDTO paramDateDTO1 = new ParamDateDTO();
        paramDateDTO1.setId(1L);
        ParamDateDTO paramDateDTO2 = new ParamDateDTO();
        assertThat(paramDateDTO1).isNotEqualTo(paramDateDTO2);
        paramDateDTO2.setId(paramDateDTO1.getId());
        assertThat(paramDateDTO1).isEqualTo(paramDateDTO2);
        paramDateDTO2.setId(2L);
        assertThat(paramDateDTO1).isNotEqualTo(paramDateDTO2);
        paramDateDTO1.setId(null);
        assertThat(paramDateDTO1).isNotEqualTo(paramDateDTO2);
    }
}
