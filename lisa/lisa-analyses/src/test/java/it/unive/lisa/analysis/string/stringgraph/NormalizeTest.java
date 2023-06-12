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

        s.compact();
        assertTrue(s.isNormalized());
    }

    @Test
    public void whenEmptyStringGraphIsCreated_thenCheckIsNormalized() {
        assertTrue(this.getEmptySon().isNormalized());
        StringGraph s = new StringGraph("");

        s.compact();
        assertTrue(s.isNormalized());
    }

    @Test
    public void whenMaxStringGraphIsCreated_thenCheckIsNormalized() {
        assertTrue(this.getMaxSon().isNormalized());
        StringGraph s = new StringGraph("|");

        s.compact();
        assertTrue(s.isNormalized());
    }

    @Test
    public void whenConcatWithSimpleSon_thenCheckIsNormalized() {
        StringGraph son = new StringGraph(SIMPLE, List.of(), StringGraph.CHARACTER.a);
        StringGraph root = new StringGraph(CONCAT, List.of(son), null);

        root.compact();
        assertTrue(root.isNormalized());
    }

    // RULE 1
    @Test
    public void whenConcatWithSingleSon_thenCheckConcatSubstituteSon() {
        StringGraph son = new StringGraph("checking");
        StringGraph firstOr = new StringGraph(OR, List.of(son), null);
        StringGraph root = new StringGraph(CONCAT, List.of(son), null);

        root.compact();
        root.normalize();

        assertTrue(root.isNormalized());
        assertEquals(CONCAT, root.getLabel());
        assertTrue(root.getFathers().contains(firstOr));
    }

    // RULE 2
    @Test
    public void whenAllSonsAreMAX_thenTurnIntoMAXStringGraph() {
        StringGraph root = new StringGraph(CONCAT, List.of(new StringGraph(MAX), new StringGraph(MAX), new StringGraph(MAX)), null);

        root.compact();
        root.normalize();

        assertTrue(root.isNormalized());
        assertEquals(MAX, root.getLabel());
        assertTrue(root.getSons().isEmpty());
    }

    // RULE 3
    @Test
    public void whenMultipleConcatSonsWithOneIncomingArc_thenMergeConcatSons() {
        StringGraph firstSon = new StringGraph("hello");
        StringGraph secondSon = new StringGraph("world");
        StringGraph root = new StringGraph(CONCAT, List.of(firstSon, secondSon), null);

        root.compact();
        root.normalize();

        assertEquals(CONCAT, root.getLabel());
        StringBuilder rootValue = new StringBuilder();

        root.getSons().forEach(son -> rootValue.append(son.getCharacter().name()));

        assertTrue(root.isNormalized());
        assertEquals("helloworld", rootValue.toString());
        assertEquals(10, root.getSons().size());
        assertTrue(firstSon.getSons().isEmpty());
        assertTrue(firstSon.getFathers().isEmpty());
        assertTrue(secondSon.getSons().isEmpty());
        assertTrue(secondSon.getFathers().isEmpty());

    }

    // RULE 4
    @Test
    public void whenConcatRootHasOneConcatNodeWithJustOneFather_thenRemoveTransferConcatSonToFather() {
        StringGraph firstSon = new StringGraph("supercalifragilistichespiralidoso");
        StringGraph secondSon = new StringGraph(OR, List.of(new StringGraph("a"), new StringGraph("b"), new StringGraph("c")), null);
        StringGraph root = new StringGraph(CONCAT, List.of(firstSon, secondSon), null);

        root.compact();
        root.normalize();

        assertTrue(root.isNormalized());
        assertEquals(CONCAT, root.getLabel());
        assertEquals(34, root.getSons().size());
        assertTrue(firstSon.getSons().isEmpty());

    }
}
