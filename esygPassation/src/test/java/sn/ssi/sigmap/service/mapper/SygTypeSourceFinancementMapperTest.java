package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SygTypeSourceFinancementMapperTest {

    private SygTypeSourceFinancementMapper sygTypeSourceFinancementMapper;

    @BeforeEach
    public void setUp() {
        sygTypeSourceFinancementMapper = new SygTypeSourceFinancementMapperImpl();
    }
}
