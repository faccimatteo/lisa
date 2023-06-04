package it.unive.lisa.analysis.string.stringgraph;

import org.junit.Test;

import java.util.List;
import java.util.Stack;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.map;
import static org.junit.Assert.*;

public class CompactTest extends BaseStringGraphTest {

    @Test
    public void whenConcatStringGraph_thenChecksNonEmptyDenotationIsTrue() {

        StringGraph s = new StringGraph("fabolousword");
        s.compact();
        assertTrue(s.getFathers().isEmpty());
        assertNull(s.getBound());
        assertEquals(s.getSons().size(), 12);

    }

    @Test
    public void whenORNodeWithEmptyNodes_thenChecksEmptyNodesAreRemoved() {
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
    public void whenORNodeWithSonAsItself_thenChecksSonIsRemoved() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        root.addSon(new StringGraph("a"));
        root.addSon(new StringGraph("b"));
        root.addSon(new StringGraph("c"));
        root.addSon(root);
        root.compact();
        root.getSons().forEach(son -> assertNotEquals(son, root));
    }

    @Test
    public void whenORWithNoNodes_thenChecksEMPTYStringGraph() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        root.compact();
        assertEquals(root.getLabel(), StringGraph.NodeType.EMPTY);
    }

    @Test
    public void whenMAXSonIsFound_thenChecksStringGraphBecomesMAXStringGraph() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        root.addSon(new StringGraph("a"));
        root.addSon(new StringGraph(StringGraph.NodeType.MAX));
        root.addSon(new StringGraph("b"));
        root.addSon(new StringGraph("c"));
        root.compact();
        assertEquals(root.getLabel(), StringGraph.NodeType.MAX);
        assertTrue(root.getSons().isEmpty());
    }

    @Test
    public void whenORHasORSons_thenChecksORSonsAreRemoved() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        StringGraph orSon = new StringGraph(StringGraph.NodeType.OR);
        StringGraph orNode = new StringGraph(StringGraph.NodeType.OR);
        StringGraph aNode = new StringGraph("a");
        StringGraph bNode = new StringGraph("b");
        orNode.addSon(aNode);
        orNode.addSon(bNode);

        StringGraph word = new StringGraph("hello");

        orSon.addSon(orNode);
        orSon.addSon(word);

        root.addSon(orSon);
        root.compact();

        root.getSons().forEach(son ->
                assertTrue(
                        son.equals(word) ||
                                son.equals(aNode) ||
                                son.equals(bNode)
                )
        );
    }

    @Test
    public void whenORWithSingleSon_thenChecksSonsAreTransferred() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        StringGraph sonToBeSubstitute = new StringGraph("hello");

        root.addSon(sonToBeSubstitute);
        root.compact();

        assertEquals(StringGraph.NodeType.CONCAT, root.getLabel());
        assertEquals(root.getSons().get(0).getCharacter(), map('h'));
        assertEquals(root.getSons().get(1).getCharacter(), map('e'));
        assertEquals(root.getSons().get(2).getCharacter(), map('l'));
        assertEquals(root.getSons().get(3).getCharacter(), map('l'));
        assertEquals(root.getSons().get(4).getCharacter(), map('o'));
    }

    @Test
    public void whenORSonWithMultipleSeparateFathers_thenChecksFathersAreConnectedTogether() {
        StringGraph firstOr = new StringGraph(StringGraph.NodeType.OR);
        StringGraph secondOr = new StringGraph(StringGraph.NodeType.OR);

        StringGraph son = new StringGraph(StringGraph.NodeType.OR);
        StringGraph firstGreeting = new StringGraph("ciao");
        StringGraph secondGreeting = new StringGraph("hello");

        son.addSon(firstGreeting);
        son.addSon(secondGreeting);

        firstOr.addSon(son);
        secondOr.addSon(son);
        son.getFathers().add(firstOr);
        son.getFathers().add(secondOr);

        // TODO: fix rule 7 behavior
        firstOr.compact();

        assertEquals(secondOr, firstOr.getSons().get(0));
        assertEquals(son, secondOr.getSons().get(0));
        assertTrue(son.getSons().containsAll(List.of(firstGreeting, secondGreeting)));
    }
}
