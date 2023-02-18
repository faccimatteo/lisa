package it.unive.lisa.analysis.string.stringgraph;

import org.junit.Test;

import static org.junit.Assert.*;

public class CompactTest extends BaseStringGraphTest {

    @Test
    public void whenSimpleNode_thenChecksNonEmptyDenotationIsTrue() {

        StringGraph s = new StringGraph("fabolousword");
        s.compact();
        assertTrue(s.getFathers().isEmpty());
        assertNull(s.getBound());
        assertEquals(s.getSons().size(), 12);

    }
}
