package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RealisationMapperTest {

    private RealisationMapper realisationMapper;

    @BeforeEach
    public void setUp() {
        realisationMapper = new RealisationMapperImpl();
    }
}
