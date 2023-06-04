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

    @Test
    public void whenORNodeWithEmptyNodes_thenCheckEmptyNodesAreRemoved() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        root.addSon(new StringGraph("a"));
        root.addSon(new StringGraph(StringGraph.NodeType.EMPTY));
        root.addSon(new StringGraph("b"));
        root.addSon(new StringGraph(StringGraph.NodeType.EMPTY));
        root.addSon(new StringGraph("c"));
        root.addSon(new StringGraph(StringGraph.NodeType.EMPTY));
        root.compact();
        root.getSons().forEach(son -> assertNotEquals(son.getLabel(), StringGraph.NodeType.EMPTY));
    }

    @Test
    public void whenORNodeWithSonAsItself_thenCheckSonIsRemoved() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        root.addSon(new StringGraph("a"));
        root.addSon(new StringGraph("b"));
        root.addSon(new StringGraph("c"));
        root.addSon(root);
        root.compact();
        root.getSons().forEach(son -> assertNotEquals(son, root));
    }

    @Test
    public void whenMAXSonIsFound_thenCheckStringGraphBecomesMAXStringGraph() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        root.addSon(new StringGraph("a"));
        root.addSon(new StringGraph(StringGraph.NodeType.MAX));
        root.addSon(new StringGraph("b"));
        root.addSon(new StringGraph("c"));
        root.addSon(root);
        root.compact();
        root.getSons().forEach(son -> assertNotEquals(son, root));
    }
}
