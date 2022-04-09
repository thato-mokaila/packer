package com.mobiquity.functions;

import com.mobiquity.models.LineImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineWriterImplTest {

    @Test
    void should_write_expected_output() {
        assertEquals("4", LineImpl.builder().result("4").build().getResult());
    }
}