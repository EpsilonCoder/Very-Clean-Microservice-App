package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParamDateMapperTest {

    private ParamDateMapper paramDateMapper;

    @BeforeEach
    public void setUp() {
        paramDateMapper = new ParamDateMapperImpl();
    }
}
