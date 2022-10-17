package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SygSourceFinancementMapperTest {

    private SygSourceFinancementMapper sygSourceFinancementMapper;

    @BeforeEach
    public void setUp() {
        sygSourceFinancementMapper = new SygSourceFinancementMapperImpl();
    }
}
