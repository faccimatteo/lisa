package it.unive.lisa.analysis.string.stringgraph;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NormalizeTest extends BaseStringGraphTest {

    @Test
    public void whenSimpleStringGraphIsCreated_thenCheckIsNormalized() {
        StringGraph s = new StringGraph("2");
        assertTrue(s.isNormalized());
    }

    @Test
    public void whenEmptyStringGraphIsCreated_thenCheckIsNormalized() {
        StringGraph s = new StringGraph("");
        assertTrue(s.isNormalized());
    }

    @Test
    public void whenMaxStringGraphIsCreated_thenCheckIsNormalized() {
        StringGraph s = new StringGraph("|");
        assertTrue(s.isNormalized());
    }

}
