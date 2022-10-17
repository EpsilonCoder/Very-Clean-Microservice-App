package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanPassationMapperTest {

    private PlanPassationMapper planPassationMapper;

    @BeforeEach
    public void setUp() {
        planPassationMapper = new PlanPassationMapperImpl();
    }
}
