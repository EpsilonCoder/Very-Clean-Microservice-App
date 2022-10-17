package sn.ssi.sigmap.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistoriqueMapperTest {

    private HistoriqueMapper historiqueMapper;

    @BeforeEach
    public void setUp() {
        historiqueMapper = new HistoriqueMapperImpl();
    }
}
