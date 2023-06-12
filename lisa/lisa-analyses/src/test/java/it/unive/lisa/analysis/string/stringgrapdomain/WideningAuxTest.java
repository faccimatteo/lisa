package it.unive.lisa.analysis.string.stringgrapdomain;

import it.unive.lisa.analysis.string.stringgraph.StringGraph;
import it.unive.lisa.analysis.string.stringgraph.StringGraphDomain;
import org.junit.Test;

import java.util.List;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.CONCAT;
import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.OR;
import static org.junit.Assert.assertEquals;

public class WideningAuxTest {

//    @Test
//    public void whenPartialOrderIsFound_thenReturnIncludedStringGraph() {
//        // go
//        StringGraph b = new StringGraph("b");
//        StringGraph a = new StringGraph("a");
//        StringGraph aRecursive = new StringGraph(CONCAT, List.of(a), null);
//        StringGraph root = new StringGraph(OR, List.of(b, aRecursive), null);
//        aRecursive.addSon(root);
//        StringGraphDomain firstStringGraphDomain = new StringGraphDomain(root);
//
//
//        // gn
//        StringGraph bb = new StringGraph("b");
//        StringGraph aa = new StringGraph("a");
//        StringGraph aaRecursive = new StringGraph(CONCAT, List.of(aa), null);
//        StringGraph orRecursive = new StringGraph(OR, List.of(bb, aaRecursive), null);
//        aaRecursive.addSon(orRecursive);
//        StringGraph hello = new StringGraph("hello");
//        StringGraph world = new StringGraph("world");
//        StringGraph or = new StringGraph(OR, List.of(hello, world), null);
//        StringGraph root1 = new StringGraph(CONCAT, List.of(or, orRecursive), null);
//        StringGraphDomain secondStringGraphDomain = new StringGraphDomain(root1);
//
//        assertEquals(firstStringGraphDomain, firstStringGraphDomain.wideningAux(secondStringGraphDomain));
//
//    }
}
