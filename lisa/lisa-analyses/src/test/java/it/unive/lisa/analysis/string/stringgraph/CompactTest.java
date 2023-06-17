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

        assertStringGraph(
                s,
                s.getSons(),
                12,
                List.of(),
                0,
                StringGraph.NodeType.CONCAT,
                true,
                null
        );

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

        assertStringGraph(
                root,
                root.getSons(),
                3,
                List.of(),
                0,
                StringGraph.NodeType.OR,
                false,
                null
        );
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

        assertStringGraph(
                root,
                root.getSons(),
                3,
                List.of(),
                0,
                StringGraph.NodeType.OR,
                false,
                null

        );
    }

    @Test
    public void whenORWithNoNodes_thenChecksEMPTYStringGraph() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        root.compact();

        assertStringGraph(
                root,
                List.of(),
                0,
                List.of(),
                0,
                StringGraph.NodeType.EMPTY,
                true,
                null
        );
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

        assertStringGraph(
                root,
                List.of(),
                0,
                List.of(),
                0,
                StringGraph.NodeType.MAX,
                false,
                null
        );
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

        assertStringGraph(
                root,
                List.of(word, aNode, bNode),
                3,
                List.of(),
                0,
                StringGraph.NodeType.OR,
                false,
                null
        );
    }

    @Test
    public void whenORWithSingleSon_thenChecksSonsAreTransferred() {
        StringGraph root = new StringGraph(StringGraph.NodeType.OR);
        StringGraph sonToBeSubstitute = new StringGraph("hello");

        root.addSon(sonToBeSubstitute);
        sonToBeSubstitute.getFathers().add(root);
        root.compact();

        assertEquals(StringGraph.NodeType.CONCAT, root.getLabel());
        assertEquals(root.getSons().get(0).getCharacter(), map('h'));
        assertEquals(root.getSons().get(1).getCharacter(), map('e'));
        assertEquals(root.getSons().get(2).getCharacter(), map('l'));
        assertEquals(root.getSons().get(3).getCharacter(), map('l'));
        assertEquals(root.getSons().get(4).getCharacter(), map('o'));

        assertStringGraph(
                root,
                root.getSons(),
                5,
                List.of(),
                0,
                StringGraph.NodeType.CONCAT,
                false,
                null
        );
    }

    @Test
    public void whenORSonWithMultipleSeparateFathers_thenChecksFathersAreConnectedTogether() {
        StringGraph firstOr = new StringGraph(StringGraph.NodeType.OR);
        StringGraph secondOr = new StringGraph(StringGraph.NodeType.OR);
        StringGraph thirdOr = new StringGraph(StringGraph.NodeType.OR);

        StringGraph son = new StringGraph(StringGraph.NodeType.OR);
        StringGraph firstGreeting = new StringGraph("ciao");
        StringGraph secondGreeting = new StringGraph("hello");
        StringGraph thirdGreeting = new StringGraph("salut");

        son.addSon(firstGreeting);
        son.addSon(secondGreeting);

        firstOr.addSon(son);
        secondOr.addSon(son);
        secondOr.addSon(thirdGreeting);
        thirdOr.addSon(son);

        firstOr.compact();
        secondOr.compact();

        // FirstOr substitute son and thirdOr becomes root
        assertEquals(1, thirdOr.getSons().size());
        assertTrue(secondOr.getSons().containsAll(List.of(firstOr,thirdGreeting)));
        assertTrue(firstOr.getSons().containsAll(List.of(firstGreeting, secondGreeting)));

        assertStringGraph(
                firstOr,
                List.of(firstGreeting, secondGreeting),
                2,
                List.of(secondOr),
                1,
                StringGraph.NodeType.OR,
                false,
                null
        );

        assertStringGraph(
                secondOr,
                List.of(thirdGreeting, firstOr),
                2,
                List.of(thirdOr),
                1,
                StringGraph.NodeType.OR,
                false,
                null
        );

        assertStringGraph(
                thirdOr,
                List.of(secondOr),
                1,
                List.of(),
                0,
                StringGraph.NodeType.OR,
                false,
                null
        );
    }
}
