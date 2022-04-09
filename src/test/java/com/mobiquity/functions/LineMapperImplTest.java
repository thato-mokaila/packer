package com.mobiquity.functions;

import com.mobiquity.models.LineImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LineMapperImplTest {

    public static final String LINE = "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52)\n" +
            "(6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
    @Test
    void should_map_string_to_line_impl() {
        LineMapperImpl mapper = new LineMapperImpl();
        LineImpl result = mapper.apply(LINE);

        assertNotNull(result);
        assertEquals(75.0, result.getMaxWeight());
        assertEquals(9, result.getPackages().size());
    }
}