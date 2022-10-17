package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SygTypeMarcheMapperTest {

    private SygTypeMarcheMapper sygTypeMarcheMapper;

    @BeforeEach
    public void setUp() {
        sygTypeMarcheMapper = new SygTypeMarcheMapperImpl();
    }
}
