package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SygServiceMapperTest {

    private SygServiceMapper sygServiceMapper;

    @BeforeEach
    public void setUp() {
        sygServiceMapper = new SygServiceMapperImpl();
    }
}
