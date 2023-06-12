package it.unive.lisa.analysis.string.stringgrapdomain;

import it.unive.lisa.analysis.string.stringgraph.StringGraph;
import it.unive.lisa.analysis.string.stringgraph.StringGraphDomain;
import it.unive.lisa.symbolic.value.operator.binary.*;
import org.junit.Test;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.CONCAT;
import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.MAX;
import static org.junit.Assert.assertEquals;

public class EvalBinaryExpressionTest {

    @Test
    public void whenStringConcatIsApplied_thenCheckStringAreConcatenated() {
        StringGraph s1 = new StringGraph("hello");
        StringGraph s2 = new StringGraph("world");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        StringGraphDomain sgd = sgd1.evalBinaryExpression(StringConcat.INSTANCE, sgd1, sgd2, null);

        assertEquals(CONCAT, sgd.getStringGraph().getLabel());
        assertEquals("CONCAT[ h e l l o w o r l d]", sgd.getStringGraph().toString());
        for (StringGraph son : sgd.getStringGraph().getSons()) {
            assertEquals(sgd.getStringGraph(), son.getFathers().get(0));
        }
    }

    @Test
    public void whenStringEqualsOperatorIsApplied_thenCheckMaxIsReturned() {
        StringGraph s1 = new StringGraph("hello");
        StringGraph s2 = new StringGraph("world");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        StringGraphDomain sgd = sgd1.evalBinaryExpression(StringEquals.INSTANCE, sgd1, sgd2, null);

        assertEquals(MAX, sgd.getStringGraph().getLabel());
        assertEquals("MAX", sgd.getStringGraph().toString());
        for (StringGraph son : sgd.getStringGraph().getSons()) {
            assertEquals(sgd.getStringGraph(), son.getFathers().get(0));
        }
    }

    @Test
    public void whenStringEndsWithOperatorIsApplied_thenCheckMaxIsReturned() {
        StringGraph s1 = new StringGraph("hello");
        StringGraph s2 = new StringGraph("world");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        StringGraphDomain sgd = sgd1.evalBinaryExpression(StringEndsWith.INSTANCE, sgd1, sgd2, null);

        assertEquals(MAX, sgd.getStringGraph().getLabel());
        assertEquals("MAX", sgd.getStringGraph().toString());
        for (StringGraph son : sgd.getStringGraph().getSons()) {
            assertEquals(sgd.getStringGraph(), son.getFathers().get(0));
        }
    }

    @Test
    public void whenStringIndexOfOperatorIsApplied_thenCheckMaxIsReturned() {
        StringGraph s1 = new StringGraph("hello");
        StringGraph s2 = new StringGraph("world");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        StringGraphDomain sgd = sgd1.evalBinaryExpression(StringIndexOf.INSTANCE, sgd1, sgd2, null);

        assertEquals(MAX, sgd.getStringGraph().getLabel());
        assertEquals("MAX", sgd.getStringGraph().toString());
        for (StringGraph son : sgd.getStringGraph().getSons()) {
            assertEquals(sgd.getStringGraph(), son.getFathers().get(0));
        }
    }

    @Test
    public void whenStringStartsWithOperatorIsApplied_thenCheckMaxIsReturned() {
        StringGraph s1 = new StringGraph("hello");
        StringGraph s2 = new StringGraph("world");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        StringGraphDomain sgd = sgd1.evalBinaryExpression(StringContains.INSTANCE, sgd1, sgd2, null);

        assertEquals(MAX, sgd.getStringGraph().getLabel());
        assertEquals("MAX", sgd.getStringGraph().toString());
        for (StringGraph son : sgd.getStringGraph().getSons()) {
            assertEquals(sgd.getStringGraph(), son.getFathers().get(0));
        }
    }


}
