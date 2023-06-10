package it.unive.lisa.analysis.string.stringgraph;

import org.junit.Test;

import java.util.List;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.*;
import static org.junit.Assert.*;

public class NormalizeTest extends BaseStringGraphTest {

    @Test
    public void whenSimpleStringGraphIsCreated_thenCheckIsNormalized() {
        assertTrue(this.getSimpleSon().isNormalized());
        StringGraph s = new StringGraph("2");
        assertTrue(s.isNormalized());
    }

    @Test
    public void whenEmptyStringGraphIsCreated_thenCheckIsNormalized() {
        assertTrue(this.getEmptySon().isNormalized());
        StringGraph s = new StringGraph("");
        assertTrue(s.isNormalized());
    }

    @Test
    public void whenMaxStringGraphIsCreated_thenCheckIsNormalized() {
        assertTrue(this.getMaxSon().isNormalized());
        StringGraph s = new StringGraph("|");
        assertTrue(s.isNormalized());
    }

    @Test
    public void whenConcatWithSimpleSon_thenCheckIsNormalized() {
        StringGraph son = new StringGraph(SIMPLE, List.of(), StringGraph.CHARACTER.a);
        StringGraph root = new StringGraph(CONCAT, List.of(son), null);
        assertTrue(root.isNormalized());
    }

    // RULE 1
    @Test
    public void whenConcatWithSingleSon_thenCheckConcatSubstituteSon() {
        StringGraph son = new StringGraph("checking");
        StringGraph firstOr = new StringGraph(OR, List.of(son), null);
        StringGraph root = new StringGraph(CONCAT, List.of(son), null);

        root.normalize();

        assertTrue(root.isNormalized());
        assertEquals(CONCAT, root.getLabel());
        assertTrue(root.getFathers().contains(firstOr));
    }

    // RULE 2
    @Test
    public void whenAllSonsAreMAX_thenTurnIntoMAXStringGraph() {
        StringGraph root = new StringGraph(CONCAT, List.of(new StringGraph(MAX), new StringGraph(MAX), new StringGraph(MAX)), null);
        root.normalize();

        assertTrue(root.isNormalized());
        assertEquals(MAX, root.getLabel());
        assertTrue(root.getSons().isEmpty());
    }

    // RULE 2
    @Test
    public void whenNotAllSonsAreMAX_thenRemainTheSame() {
        StringGraph root = new StringGraph(CONCAT, List.of(new StringGraph(MAX), new StringGraph(MAX), new StringGraph("a")), null);
        root.normalize();

        assertTrue(root.isNormalized());
        assertEquals(MAX, root.getLabel());
        assertTrue(root.getSons().isEmpty());
    }


}
