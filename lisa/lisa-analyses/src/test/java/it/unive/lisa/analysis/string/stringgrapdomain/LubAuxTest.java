package it.unive.lisa.analysis.string.stringgrapdomain;

import it.unive.lisa.analysis.string.stringgraph.StringGraph;
import it.unive.lisa.analysis.string.stringgraph.StringGraphDomain;
import org.junit.Test;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.OR;
import static org.junit.Assert.assertEquals;

public class LubAuxTest {

    @Test
    public void whenLubAuxOperatorIsApplied_thenCheckCorrectness() {
        StringGraph s1 = new StringGraph("hello");
        StringGraph s2 = new StringGraph("world");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        StringGraphDomain sgd = sgd1.lubAux(sgd2);

        assertEquals(OR, sgd.getStringGraph().getLabel());
        assertEquals(s1, sgd.getStringGraph().getSons().get(0));
        assertEquals(s2, sgd.getStringGraph().getSons().get(1));
        assertEquals(sgd.getStringGraph(), sgd1.getStringGraph().getFathers().get(0));
        assertEquals(sgd.getStringGraph(), sgd2.getStringGraph().getFathers().get(0));
    }


}
