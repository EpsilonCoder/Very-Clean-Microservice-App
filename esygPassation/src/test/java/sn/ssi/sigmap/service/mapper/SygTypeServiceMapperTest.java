package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SygTypeServiceMapperTest {

    private SygTypeServiceMapper sygTypeServiceMapper;

    @BeforeEach
    public void setUp() {
        sygTypeServiceMapper = new SygTypeServiceMapperImpl();
    }
}
